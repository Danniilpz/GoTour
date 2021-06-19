package es.ucm.fdi.iw.gotour.control;

import java.io.File;


import javax.servlet.http.HttpServletRequest;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;




import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PathVariable;

import es.ucm.fdi.iw.gotour.LocalData;
import es.ucm.fdi.iw.gotour.model.User;
import es.ucm.fdi.iw.gotour.model.Tour;
import es.ucm.fdi.iw.gotour.model.TourOfertado;
import es.ucm.fdi.iw.gotour.model.Reporte;
import es.ucm.fdi.iw.gotour.model.User.Role;



/**
 * Admin-only controller
 * @author mfreire
 */
@Controller()
@RequestMapping("admin")
public class AdminController {
	
	private static final Logger log = LogManager.getLogger(AdminController.class);
	
	@Autowired 
	private EntityManager entityManager;
	
	@Autowired
	private LocalData localData;
	
	@Autowired
	private Environment env;
	
	@GetMapping("/")
	public String index(Model model) {

        model.addAttribute("activeProfiles", env.getActiveProfiles());
		model.addAttribute("basePath", env.getProperty("es.ucm.fdi.base-path"));
		model.addAttribute("debug", env.getProperty("es.ucm.fdi.debug"));
		List<User> users = entityManager.createNamedQuery("AllUsers").getResultList();        
        // dumps them via log
		
        log.info("Dumping table {}", "user");
        for (Object o : users) {
            log.info("\t{}", o);
        }

		List<Tour> tours = entityManager.createNamedQuery("AllTours").getResultList();
		List<Tour> listaTours = entityManager.createNamedQuery("AllToursOrdered").getResultList();
		List<TourOfertado> toursOfertados = entityManager.createNamedQuery("AllToursOfer").getResultList();
		
		int tourNumber=0;
		if(tours!=null){
			tourNumber=tours.size();
			toursOfertados.size();

		}
		int tourOfertadoNumber=0;

		if(toursOfertados!=null){
			
			tourOfertadoNumber=toursOfertados.size();
			

		}


		int userNumber= users.size();
		List<Reporte> reportes = entityManager.createNamedQuery("AllReportes").getResultList();
        int reportesNumber=0;
		reportesNumber= reportes.size();
  
		List<Reporte> reportesTour= entityManager.createNamedQuery("TypeReportes").setParameter("tipoparam", "TOUR").getResultList();
		List<Reporte> reportesUser= entityManager.createNamedQuery("TypeReportes").setParameter("tipoparam", "USER").getResultList();
      
		

		int reportesTourNumber=0;
        reportesTourNumber=reportesTour.size();
		
		int reportesUserNumber=0;
        reportesUserNumber=reportesUser.size();
	
        // adds them to model
        model.addAttribute("userNumber", userNumber);
		model.addAttribute("users", users);
		model.addAttribute("tours", listaTours);
		model.addAttribute("tourNumber", tourNumber);
		model.addAttribute("reportes", reportes);
		model.addAttribute("reportesNumber", reportesNumber);
		model.addAttribute("reportesUser", reportesTour);
		model.addAttribute("reportesTour", reportesUser);
		model.addAttribute("reportesUserNumber", reportesTourNumber);
		model.addAttribute("reportesTourNumber", reportesUserNumber);
		model.addAttribute("classActiveAdmin","active");		
        return "admin/index";

	}





	
	@PostMapping("/toggleuser")
	@Transactional
	public String delUser(Model model, @RequestParam long id) {
		User target = entityManager.find(User.class, id);
		if (target.getEnabled() == 1) {
			// remove profile photo
			File f = localData.getFile("user", ""+id);
			if (f.exists()) {
				f.delete();
			}
			// disable user
			target.setEnabled(0); 
		} else {
			// enable user
			target.setEnabled(1);
		}
		List<User> users = entityManager.createNamedQuery("AllUsers").getResultList();        
        // dumps them via log
        log.info("Dumping table {}", "user");
        for (Object o : users) {
            log.info("\t{}", o);
        }        
        // adds them to model
        model.addAttribute("users", users);
		model.addAttribute("classActiveUsers","active");
		return "admin/users";
	}


	@GetMapping("/users")
	public String users(Model model) {
		model.addAttribute("activeProfiles", env.getActiveProfiles());
		model.addAttribute("basePath", env.getProperty("es.ucm.fdi.base-path"));
		model.addAttribute("debug", env.getProperty("es.ucm.fdi.debug"));
		List<User> users = entityManager.createNamedQuery("AllUsers").getResultList();        
        // dumps them via log
        log.info("Dumping table {}", "user");
        for (Object o : users) {
            log.info("\t{}", o);
        }        
        // adds them to model
        model.addAttribute("users", users);
		model.addAttribute("classActiveUsers","active");
		return "admin/users";
	}


	@PostMapping("/user-busqueda")
	public String userBusqueda(Model model, List<User> users) {
		model.addAttribute("activeProfiles", env.getActiveProfiles());
		model.addAttribute("basePath", env.getProperty("es.ucm.fdi.base-path"));
		model.addAttribute("debug", env.getProperty("es.ucm.fdi.debug"));
		      
        // dumps them via log
        log.info("Dumping table {}", "user");
        for (Object o : users) {
            log.info("\t{}", o);
        }        
        // adds them to model
        model.addAttribute("users", users);
		model.addAttribute("classActiveUsers","active");
		return "admin/user-busqueda";
	}



	@GetMapping("/reportes-usuarios")
	public String reportes(Model model) {
		model.addAttribute("activeProfiles", env.getActiveProfiles());
		model.addAttribute("basePath", env.getProperty("es.ucm.fdi.base-path"));
		model.addAttribute("debug", env.getProperty("es.ucm.fdi.debug"));
		List<Reporte> reportes= entityManager.createNamedQuery("AllReportes").getResultList();
		   
        // adds them to model
        model.addAttribute("reportes", reportes);
	
		model.addAttribute("classActiveReportes","active");
		
		return "admin/reportes-usuarios";
	}

	@GetMapping("/reporte-usuario")
	public String reporteBusqueda(Model model, List<Reporte> reportes) {

		
		model.addAttribute("activeProfiles", env.getActiveProfiles());
		model.addAttribute("basePath", env.getProperty("es.ucm.fdi.base-path"));
		model.addAttribute("debug", env.getProperty("es.ucm.fdi.debug"));
		      
        // dumps them via log
        log.info("Dumping table {}", "reportes");
        for (Object o : reportes) {
            log.info("\t{}", o);
        }        
        // adds them to model
		
        model.addAttribute("reportes", reportes);
		model.addAttribute("classActiveReportes","active");
		return "admin/reporte-usuario";
	}


	@PostMapping("/reporteSearch")
    public String searchReporte(Model model,@RequestParam String username
                                        , @RequestParam String motivo, @RequestParam String texto
                                        ){
        List<Reporte> busqueda = new ArrayList<Reporte>();
		List<Reporte> auxReporte = new ArrayList<Reporte>();
        if(username.length()>0){
			List<User> userBusqueda = entityManager.createNamedQuery("UsersByAdminSearchUser")
				.setParameter("usernameParam", "%" + username + "%").getResultList();
		for (User user : userBusqueda) {
            List<Reporte> auxbusqueda = entityManager.createNamedQuery("AllReportes").getResultList();
            for(Reporte report: auxbusqueda){
				if(report.getCreador().getId()==user.getId()){
					busqueda.add(report);
				}
			}

		}


		
		}
        if(username.length()<1&&motivo.length()<1&&texto.length()<1){
			busqueda=entityManager.createNamedQuery("AllReportes").getResultList();
		}else{
			if(motivo.length()>0&&texto.length()>0){
				auxReporte= entityManager.createNamedQuery("ReportesByAdminSearchMotivoTexto").setParameter("motivoParam",  "%" + motivo + "%").setParameter("textoParam",  "%" + texto + "%").getResultList();
			}else{
	
				if(motivo.length()>0&&texto.length()<1){
	
					auxReporte= entityManager.createNamedQuery("ReportesByAdminSearchMotivo").setParameter("motivoParam",  "%" + motivo + "%").getResultList();
	
				}else{
	
					if(motivo.length()<1&&texto.length()>0){
	
						auxReporte= entityManager.createNamedQuery("ReportesByAdminSearchTexto").setParameter("textoParam",  "%" + texto + "%").getResultList();
		
					}
	
				}
	
			}
	
	
			
				
		}
		
		
		

		for (Reporte reporte : auxReporte) {
			busqueda.add(reporte);
		
		}
		
		
             	
        model.addAttribute("busqueda", busqueda);
		
        return reporteBusqueda(model,busqueda);
    }

	

	@GetMapping("reporte/{id}/gestion-reporte")
	public String contestaReporte(Model model, @PathVariable("id") long id) {
	Reporte r = entityManager.find(Reporte.class, id);
	switch(r.getTipo()){
		case "USER":
		model.addAttribute("user",r.getUserReportado());
		break;
		case "TOUR":
		model.addAttribute("user",r.getTourReportado().getDatos().getGuia());
		break;
		case "ADMIN":
		User userAdmin = entityManager.find(User.class, id);
		model.addAttribute("user",userAdmin);
	}
	
	model.addAttribute("reporte",r);
	model.addAttribute("classActiveSettings","active");
	return "admin/gestion-reporte";


	}

	@PostMapping("reporte/{id}/gestion-reporte-admin")
	@Transactional
	public String contestarAlReporte(Model model, HttpSession session,@RequestParam String motivo, @RequestParam String respuesta,  @PathVariable("id") long id) {
	Reporte r = entityManager.find(Reporte.class, id);

	r.setContestada(true);
	
	Reporte respuestaAdmin= new Reporte();
	User userContestado = r.getCreador();
	
	User userCreador = entityManager.find(User.class,((User)session.getAttribute("u")).getId());
	respuestaAdmin.setTipo("ADMIN");
	respuestaAdmin.setCreador(userCreador);
	respuestaAdmin.setMotivo(motivo);
	respuestaAdmin.setTexto(respuesta);
	switch(r.getTipo()){
		case "TOUR":
		respuestaAdmin.setTourReportado(r.getTourReportado());
		break;
		case "USER":
		respuestaAdmin.setUserReportado(r.getUserReportado());
		break;
	}
	respuestaAdmin.setUserContestado(userContestado);
	respuestaAdmin.setReporteContestado(r);
	
	userCreador.getReporteCreados().add(respuestaAdmin);
	userContestado.getReporteRecibidos().add(respuestaAdmin);
	entityManager.persist(respuestaAdmin);
	entityManager.flush(); 

   
	List<Reporte> reportes= entityManager.createNamedQuery("AllReportes").getResultList();
	
     
    model.addAttribute("reportes", reportes);
	
	return "admin/reporte-usuario";


	}
	
	


	@PostMapping("/userSearch")
    public String searchUsers(Model model,@RequestParam String username
                                        ,@RequestParam String email
                                        ){

		List<User> busqueda= new ArrayList<User>();
        if(username.length()>0&&email.length()>0){
			busqueda = entityManager.createNamedQuery("UsersByAdminSearchEmailUser")
            .setParameter("usernameParam", "%" + username + "%") 
            .setParameter("emailParam","%" + email + "%").getResultList();

		}else{
			if(username.length()>0&&email.length()<1){
				busqueda = entityManager.createNamedQuery("UsersByAdminSearchUser")
				.setParameter("usernameParam", "%" + username + "%").getResultList();
	
			}else{
				if(username.length()<1&&email.length()>0){
					busqueda = entityManager.createNamedQuery("UsersByAdminSearchEmail").setParameter("emailParam","%" + email + "%").getResultList();
		
				}
			}
		}
       
	

        model.addAttribute("busqueda", busqueda);
		
        return userBusqueda(model,busqueda);
    }


	


	


		
		







	



}

