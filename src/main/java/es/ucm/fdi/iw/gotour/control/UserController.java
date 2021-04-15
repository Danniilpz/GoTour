package es.ucm.fdi.iw.gotour.control;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import es.ucm.fdi.iw.gotour.LocalData;
import es.ucm.fdi.iw.gotour.model.Message;
import es.ucm.fdi.iw.gotour.model.User;
import es.ucm.fdi.iw.gotour.model.User.Role;

/**
 * User-administration controller
 * 
 * @author mfreire
 */
@Controller()
@RequestMapping("user")
public class UserController {

	@Autowired
	private PasswordEncoder passwordEncoder;

		/**
	 * Tests a raw (non-encoded) password against the stored one.
	 * @param rawPassword to test against
	 * @return true if encoding rawPassword with correct salt (from old password)
	 * matches old password. That is, true iff the password is correct  
	 */
	public boolean passwordMatches(String rawPassword, String EncodedPassword) {
		return passwordEncoder.matches(rawPassword, EncodedPassword);
	}

	/**
	 * Encodes a password, so that it can be saved for future checking. Notice
	 * that encoding the same password multiple times will yield different
	 * encodings, since encodings contain a randomly-generated salt.
	 * @param rawPassword to encode
	 * @return the encoded password (typically a 60-character string)
	 * for example, a possible encoding of "test" is 
	 * {bcrypt}$2y$12$XCKz0zjXAP6hsFyVc8MucOzx6ER6IsC1qo5zQbclxhddR1t6SfrHm
	 */
	public String encodePassword(String rawPassword) {
		System.out.println("La contraseña en bruto es ");
		System.out.println(rawPassword);
		return passwordEncoder.encode(rawPassword);
	}	

	
	
	private static final Logger log = LogManager.getLogger(UserController.class);
	
	@Autowired 
	private EntityManager entityManager;
	
	@Autowired
	private LocalData localData;

	@Autowired
	private SimpMessagingTemplate messagingTemplate;

	@GetMapping("/{id}")
	public String getUser(@PathVariable long id, Model model, HttpSession session) 			
			throws JsonProcessingException {		
		User u = entityManager.find(User.class, id);
		model.addAttribute("user", u);

		// construye y envía mensaje JSON
		User requester = (User)session.getAttribute("u");
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode rootNode = mapper.createObjectNode();
		rootNode.put("text", requester.getUsername() + " is looking up " + u.getUsername());
		String json = mapper.writeValueAsString(rootNode);
		
		messagingTemplate.convertAndSend("/topic/admin", json);

		return "user";
	}	
	
	@ResponseStatus(
		value=HttpStatus.FORBIDDEN, 
		reason="No eres administrador, y éste no es tu perfil")  // 403
	public static class NoEsTuPerfilException extends RuntimeException {}	
	
	@GetMapping(value="/{id}/photo")
	public StreamingResponseBody getPhoto(@PathVariable long id, Model model) throws IOException {		
		File f = localData.getFile("user", ""+id);
		InputStream in;
		if (f.exists()) {
			in = new BufferedInputStream(new FileInputStream(f));
		} else {
			in = new BufferedInputStream(getClass().getClassLoader()
					.getResourceAsStream("static/img/unknown-user.jpg"));
		}
		return new StreamingResponseBody() {
			@Override
			public void writeTo(OutputStream os) throws IOException {
				FileCopyUtils.copy(in, os);
			}
		};
	}
	
	@PostMapping("/{id}/msg")
	@ResponseBody
	@Transactional
	public String postMsg(@PathVariable long id, 
			@RequestBody JsonNode o, Model model, HttpSession session) 
		throws JsonProcessingException {
		
		String text = o.get("message").asText();
		User u = entityManager.find(User.class, id);
		User sender = entityManager.find(
				User.class, ((User)session.getAttribute("u")).getId());
		model.addAttribute("user", u);
		
		// construye mensaje, lo guarda en BD
		Message m = new Message();
		m.setRecipient(u);
		m.setSender(sender);
		m.setDateSent(LocalDateTime.now());
		m.setText(text);
		entityManager.persist(m);
		entityManager.flush(); // to get Id before commit
		
		// construye json
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode rootNode = mapper.createObjectNode();
		rootNode.put("from", sender.getUsername());
		rootNode.put("to", u.getUsername());
		rootNode.put("text", text);
		rootNode.put("id", m.getId());
		String json = mapper.writeValueAsString(rootNode);
		
		log.info("Sending a message to {} with contents '{}'", id, json);

		messagingTemplate.convertAndSend("/user/"+u.getUsername()+"/queue/updates", json);
		return "{\"result\": \"message sent.\"}";
	}	
	
	@PostMapping("/{id}/photo")
	public String postPhoto(
			HttpServletResponse response,
			@RequestParam("photo") MultipartFile photo,
			@PathVariable("id") String id, Model model, HttpSession session) throws IOException {
		User target = entityManager.find(User.class, Long.parseLong(id));
		model.addAttribute("user", target);
		
		// check permissions
		User requester = (User)session.getAttribute("u");
		if (requester.getId() != target.getId() &&
				! requester.hasRole(Role.ADMIN)) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, 
					"No eres administrador, y éste no es tu perfil");
			return "user";
		}
		
		log.info("Updating photo for user {}", id);
		File f = localData.getFile("user", id);
		if (photo.isEmpty()) {
			log.info("failed to upload photo: emtpy file?");
		} else {
			try (BufferedOutputStream stream =
					new BufferedOutputStream(new FileOutputStream(f))) {
				byte[] bytes = photo.getBytes();
				stream.write(bytes);
			} catch (Exception e) {
				log.warn("Error uploading " + id + " ", e);
			}
			log.info("Successfully uploaded photo for {} into {}!", id, f.getAbsolutePath());
		}
		return "user";
	}
	@GetMapping("/logout")
	public String salir(Model model, HttpServletRequest request){
		try{
			request.logout();
		}
		catch(Exception e){
			log.error("Fallo al hacer logout", e);
		}
		return "index";
	}

	@PostMapping("/registro2")
    @Transactional
    public String registrar(@RequestParam String nombre,  
                            @RequestParam String apellidos, 
                            @RequestParam String email,
                            @RequestParam String password,
                            @RequestParam String preguntaseguridad,
                            @RequestParam String respuestaseguridad,
                            @RequestParam String username,
                            @RequestParam long numtelefono,
                            @RequestParam long numtarjeta,
                            @RequestParam String caducidadtarjeta,
                            @RequestParam int numsecreto,
                            Model model, HttpServletRequest request, HttpSession session){
        User user = new User();
        user.setNombre(nombre);
        user.setApellidos(apellidos);
        user.setEmail(email);
		String encoded = encodePassword(password);
        user.setPassword(encoded);
        user.setPreguntaseguridad(preguntaseguridad);
        user.setRespuestaseguridad(respuestaseguridad);
        user.setUsername(username);
        user.setNumtelefono(numtelefono);
        user.setNumtarjeta(numtarjeta);
        user.setCaducidadtarjeta(caducidadtarjeta);
        user.setNumsecreto(numsecreto);
        user.setRoles("USER");
        user.setEnabled(1);
        entityManager.persist(user);
        entityManager.flush();
        try {
	        request.login(username, password);
			session.setAttribute("u", user);
	    } catch (Exception e) {
	    }
        return "index";

    }
	@PostMapping("/{id}/actualizar")
	@Transactional
    public String actualizar(@PathVariable("id") Long id,
							@RequestParam String nombre,  
                            @RequestParam String apellidos, 
                            @RequestParam String email,
                            @RequestParam String password,
                            @RequestParam String username,
                            @RequestParam long numtelefono,
                            @RequestParam long numtarjeta,
                            @RequestParam String caducidadtarjeta,
                            @RequestParam int numsecreto,
                            Model model, HttpSession session){
		User user = entityManager.find(User.class, id);
		log.info("SE HA OBTENIDO EL USUARIO {}", user);
        user.setNombre(nombre);
        user.setApellidos(apellidos);
        user.setEmail(email);
		String encoded = encodePassword(password);
        user.setPassword(encoded);
        user.setUsername(username);
        user.setNumtelefono(numtelefono);
        user.setNumtarjeta(numtarjeta);
        user.setCaducidadtarjeta(caducidadtarjeta);
        user.setNumsecreto(numsecreto);
        user.setRoles("USER");
        user.setEnabled(1);
		session.setAttribute("u", user);
        return "datosPrivados";

    }
	@GetMapping("/{id}/perfil")
    public String perfil(Model model, @PathVariable("id") Long id, HttpSession session)
    {
		//User u = entityManager.find(User.class, id);
		//model.addAttribute("u", u);
		User requester = (User)session.getAttribute("u");
        User user = (User)entityManager.createNamedQuery("User.byId")
            .setParameter("id", requester.getId()).getSingleResult();
        user.setTourofrecidos(entityManager.createNamedQuery("User.getToursOfrecidos")
            .setParameter("guia_id", requester.getId()).getResultList());
        user.setReviewsrecibidas(entityManager.createNamedQuery("User.getReviewsRecibidas")
            .setParameter("dest", requester.getId()).getResultList());
        /*for (int i=0; i<user.getTourofrecidos().size(); i++){
            int datos_id = (int)entityManager.createNamedQuery("Tour.byId")
                .setParameter("id", user.getTourofrecidos().get(i).getId()).getSingleResult();
            user.getTourofrecidos().get(i).setDatos((TourOfertado)entityManager.createNamedQuery("TourOfrecido.byId")
                .setParameter("id", datos_id).getSingleResult());
        }*/
        model.addAttribute("user", user); 
        model.addAttribute("propio", true);
        return "perfil";
    }

	@GetMapping("/{id}/datosPrivados")
    public String datosPrivados(Model model, HttpSession session, @PathVariable("id") Long id)
    {
        return "datosPrivados";
    }
	@GetMapping("/{id}/EditarDatos")
	public String editar(Model model, HttpSession session, @PathVariable("id") Long id) {
		return "EditarDatos";
	}



}