package es.ucm.fdi.iw.gotour.control;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;
import java.util.ArrayList;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.time.LocalDateTime;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.beans.PropertyAccessor;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import es.ucm.fdi.iw.gotour.LocalData;
import es.ucm.fdi.iw.gotour.model.Tour;
import es.ucm.fdi.iw.gotour.model.TourOfertado;
import es.ucm.fdi.iw.gotour.model.User;

/**
 * Landing-page controller
 * 
 * @author mfreire
 */
@Controller
public class RootController {

	private static final Logger log = LogManager.getLogger(RootController.class);
    @Autowired 
	private EntityManager entityManager;
    private AuthenticationManager authman;

    @Autowired
	private LocalData localData;

    @PostMapping("/")
    public String searchTours(Model model, HttpSession session,@RequestParam String pais
                                        ,@RequestParam String ciudad
                                        ,@RequestParam String lugar
                                        ,@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaini
                                        ,@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechafin){
        List<Tour> busqueda = entityManager.createNamedQuery("ToursBySearch")
            .setParameter("paisParam", pais)
            .setParameter("ciudadParam", ciudad)
            .setParameter("lugarParam", lugar)
            .setParameter("fechaIniParam", fechaini)
            .setParameter("fechaFinParam", fechafin).getResultList();      	
        model.addAttribute("busqueda", busqueda);	
        return index(model, session);
    }

    @GetMapping("/")            // <-- en qué URL se expone, y por qué métodos (GET)        
    public String index(        // <-- da igual, sólo para desarrolladores
            Model model, HttpSession session)        // <-- hay muchos, muchos parámetros opcionales
    {
        actualizarTours();
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
        model.addAttribute("classActiveHome","active");		
        return "index";
    }

    @Transactional
    public void actualizarTours(){
        List<Tour> tours = entityManager.createNamedQuery("AllTours").getResultList(); 
        for(Tour t:tours){
            if(t.getFechaIni().isBefore(LocalDateTime.now())){
                t.getDatos().setDisponible(false);
            }
            else{
                t.getDatos().setDisponible(true);
            }
        }
    }

	@GetMapping("/chat")
	public String chat(Model model, HttpServletRequest request) {
		return "chat";
	}
	
	@GetMapping("/error")
	public String error(Model model) {
		return "error";
	}
	
	@GetMapping("/login")
	public String login(Model model) {
        model.addAttribute("classActiveLogin","active");
		return "login";
	}

    @GetMapping("/registro")
	public String registro(Model model) {
        model.addAttribute("classActiveRegistro","active");
		return "registro";
	}

	@GetMapping("/crearTour")
    public String crearTour(Model model, HttpSession session)
    {
        return "crearTour";
    }

    // Un getMapping por vista que queramos en el proyecto. Y un template por vista

    @GetMapping("/guia")
    public String guia(Model model)
    {
        model.addAttribute("user", "");   
        return "guia";
    }

    @GetMapping("/busqueda")
    public String busqueda(Model model)
    {  
        return "busqueda";
    }
    @GetMapping("/validar")
    @ResponseBody
	public Long validarUser(@RequestParam String username,
						Model model, HttpSession session) throws IOException {
		Long res = (Long)entityManager.createNamedQuery("User.hasUsername")
		.setParameter("username", username)
		.getSingleResult();
		log.info("El numero de usuarios con ese nombre es {}", res);
		String response;
		// if(res > 0){
		// 	response = "<span id='Ocupado'style='color: red;'>Not Available.</span>";
		// }
		// else{
		// 	response = "<span id='Libre' style='color: green;'>Available.</span>";
		// }
		return res;
	}


   

 
}