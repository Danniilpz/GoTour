package es.ucm.fdi.iw.gotour.control;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Hibernate;
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
import es.ucm.fdi.iw.gotour.model.Mensaje;
import es.ucm.fdi.iw.gotour.model.Review;
import es.ucm.fdi.iw.gotour.model.User;
import es.ucm.fdi.iw.gotour.model.Tour;
import es.ucm.fdi.iw.gotour.model.Reporte;
import es.ucm.fdi.iw.gotour.model.TourOfertado;
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
		
		String text = o.get("mensaje").asText();
		User u = entityManager.find(User.class, id);
		User sender = entityManager.find(
				User.class, ((User)session.getAttribute("u")).getId());
		model.addAttribute("user", u);
		
		// construye mensaje, lo guarda en BD
		Mensaje m = new Mensaje();
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
		
		log.info("Sending a Mensaje to {} with contents '{}'", id, json);

		messagingTemplate.convertAndSend("/user/"+u.getUsername()+"/queue/updates", json);
		return "{\"result\": \"mensaje sent.\"}";
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
		{
		List<Tour> tours = entityManager.createNamedQuery("AllTours").getResultList();        
        // dumps them via log
        log.info("Dumping table {}", "tour");
        for (Object o : tours) {
            log.info("\t{}", o);
        }     
        List<User> users = entityManager.createNamedQuery("AllUsersByPuntuacion").setMaxResults(20).getResultList();        
        // dumps them via log
        log.info("Dumping table {}", "user");
        for (Object o : users) {
            log.info("\t{}", o);
        }       
        // adds them to model
        model.addAttribute("tours", tours);	
        model.addAttribute("users", users);		
		return "index";
		}
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
                            Model model, HttpServletRequest request, HttpSession session){
        User user = new User();
        user.setNombre(nombre);
        user.setApellidos(apellidos);
        user.setEmail(email);
		String encoded = encodePassword(password);
        user.setPassword(encoded);
        user.setPreguntaSeguridad(preguntaseguridad);
        user.setRespuestaSeguridad(respuestaseguridad);
        user.setUsername(username);
        user.setNumTelefono(numtelefono);
        user.setRoles("USER");
        user.setEnabled(1);
        entityManager.persist(user);
        entityManager.flush();
        try {
	        request.login(username, password);
			session.setAttribute("u", user);
	    } catch (Exception e) {
	    }
        return "redirect:/";

    }

	@PostMapping("/{id}/actualizar")
	@Transactional
    public String actualizar(@PathVariable("id") Long id,
							@RequestParam String nombre,  
                            @RequestParam String apellidos, 
                            @RequestParam String email,
                            @RequestParam String username,
                            @RequestParam long numtelefono,
							@RequestParam String pregunta,
							@RequestParam String respuesta,
                            Model model, HttpSession session){
		User user = entityManager.find(User.class, id);
		log.info("SE HA OBTENIDO EL USUARIO {}", user);
        user.setNombre(nombre);
        user.setApellidos(apellidos);
        user.setEmail(email);
        user.setUsername(username);
        user.setNumTelefono(numtelefono);
        user.setRoles("USER");
        user.setEnabled(1);
		user.setPreguntaSeguridad(pregunta);
		user.setRespuestaSeguridad(respuesta);
		session.setAttribute("u", user);
		model.addAttribute("u", user);
		List<String> idiomas = entityManager.createNamedQuery("User.haslanguajes")
		.setParameter("user_id", user.getId())
		.getResultList();
		model.addAttribute("idiomas", idiomas);
        return "editarDatos";

    }
	@PostMapping("/actualizarFoto")
	@Transactional
	public String actualizarFoto(@RequestParam("foto") MultipartFile foto,
						Model model, HttpSession session) throws IOException {
		User u = entityManager.find(User.class,((User)session.getAttribute("u")).getId());
		log.info("Updating profile photo for user {}",String.valueOf(u.getId()));
		File f = localData.getFile("users", String.valueOf(u.getId()));
		if (foto.isEmpty()) {
			log.info("fallo al subir la foto: archivo vacio?");
		} else {
			try (BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(f))) {
				byte[] bytes = foto.getBytes();
				stream.write(bytes);
			} catch (Exception e) {
				log.warn("Error uploading " + u.getId() + ", profile photo ", e);
			}
			log.info("Successfully uploaded profile photo for {} into {}!", u.getId(), f.getAbsolutePath());
		}

		//return perfil(model,session,id);*/
		return perfil(model,session,u.getId());
	}
	@PostMapping("/addLanguaje")
	@Transactional
	public String actualizarIdiomas(@RequestParam String idioma,
						Model model, HttpSession session) throws IOException {
        User u = entityManager.find(User.class,      // IMPORTANTE: tiene que ser el de la BD, no vale el de la sesión
            ((User)session.getAttribute("u")).getId());
		u.addLanguaje(idioma);
		session.setAttribute("u", u);
		return perfil(model,session,u.getId());
	}


	@GetMapping("/{id}/perfil")
	@Transactional
    public String perfil(Model model, HttpSession session, @PathVariable("id") long id)
    {
	User user = entityManager.find(User.class, id);
	log.info("EL USUARIO CONTIENE LO SIGUIENTE {}", user);

		List<Tour> ofertados =  new ArrayList<>(user.getTourOfertados());
		List<Review> recibidas =  new ArrayList<>(user.getReviewsRecibidas());
		for (Tour t : ofertados) {
			t.getDatos();
		}
        List<String> idiomas = entityManager.createNamedQuery("User.haslanguajes")
                .setParameter("user_id", user.getId())
                .getResultList();
		if(user.getTourOfertados().size()>0){
			Tour firstTour=entityManager.createNamedQuery("Tour.getFirstTour",Tour.class)
				.setParameter("guia_id", user.getId())
				.setMaxResults(1)
				.getSingleResult();				
			List<Tour> tours=entityManager.createNamedQuery("Tour.getToursByUser",Tour.class)
			.setParameter("guia_id", user.getId())
			.getResultList();
			model.addAttribute("tours", tours);
			model.addAttribute("firstTour", firstTour);
			
		}
		
        model.addAttribute("idiomas",idiomas);
        model.addAttribute("user", user);
        return "perfil";
    }

	@PostMapping("/{id}/userReportado")
	@Transactional
    public String reportarUser(Model model, HttpSession session,@RequestParam String motivo, @RequestParam String reporte, @PathVariable("id") long id)
    {
	    User user = entityManager.find(User.class, id);
		User userCreador = entityManager.find(User.class,((User)session.getAttribute("u")).getId());
        Reporte r = new Reporte();
        r.setTipo("USER");
		r.setCreador(userCreador);
		r.setMotivo(motivo);
        r.setTexto(reporte);
        r.setTourReportado(null);
        r.setUserReportado(user);
		r.setUserContestado(null);
		r.setContestada(false);
		userCreador.getReporteCreados().add(r);
		entityManager.persist(r);
        entityManager.flush();
		List<User> users = entityManager.createNamedQuery("RolSearchUsers").setParameter("rolparameter", "ADMIN").getResultList(); 

		for(int i=0; i<users.size();i++){
			User admin=users.get(i);
			boolean a=false;
			admin.getReporteRecibidos().add(r);
			
		}
		

        
		return "gracias-reporte-ha-sido-recibido";
    }

	@PostMapping("/{idUser}/respuestasAdmin/{id}/respuestaReporte")
	@Transactional
    public String reporteUser(Model model, HttpSession session, @PathVariable("id") long id, @PathVariable("idUser") long idUser)
    { 
		User user = entityManager.find(User.class, id);
	    
		List<Reporte> reportes = entityManager.createNamedQuery("ReportesCreadorTipo",Reporte.class)
		.setParameter("typeParam", "ADMIN").setParameter("userParam",user).getResultList();
	
		model.addAttribute("reportesNumber", reportes.size());
        model.addAttribute("reportesa", reportes);
        return "respuestasAdmin";
    }


	
	@GetMapping("/{id}/perfil-reportes")
	@Transactional
    public String perfilReportes(Model model, HttpSession session, @PathVariable("id") long id)
    {    
		User user = entityManager.find(User.class, ((User)session.getAttribute("u")).getId());
	    List<Reporte> reportes = entityManager.createNamedQuery("ReportesCreador",Reporte.class)
		.setParameter("userParam", user).getResultList();	
		
        model.addAttribute("reportes", reportes);
        return "perfil-reportes";
    }
	

	@GetMapping("/{id}/foto")
	public StreamingResponseBody getFoto(@PathVariable long id, Model model) throws IOException {		
		File f = localData.getFile("users", ""+id);
		InputStream in;
		if (f.exists()) {
			in = new BufferedInputStream(new FileInputStream(f));
		} else {
			in = new BufferedInputStream(getClass().getClassLoader()
					.getResourceAsStream("static/img/defaultuser.png"));
		}
		return new StreamingResponseBody() {
			@Override
			public void writeTo(OutputStream os) throws IOException {
				FileCopyUtils.copy(in, os);
			}
		};
	}
	@GetMapping("/editarDatos")
	public String editar(Model model, HttpSession session) {
		User user = entityManager.find(User.class, ((User)session.getAttribute("u")).getId());
		List<String> idiomas = entityManager.createNamedQuery("User.haslanguajes")
                .setParameter("user_id", user.getId())
                .getResultList();
		model.addAttribute("idiomas",idiomas);
        model.addAttribute("u", user);
		return "editarDatos";
	}




}
