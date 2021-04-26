package es.ucm.fdi.iw.gotour.control;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.yaml.snakeyaml.tokens.Token.ID;

import java.util.ArrayList;
import java.util.List;
import es.ucm.fdi.iw.gotour.LocalData;
import es.ucm.fdi.iw.gotour.model.Tour;
import es.ucm.fdi.iw.gotour.model.TourOfertado;
import es.ucm.fdi.iw.gotour.model.User;
import es.ucm.fdi.iw.gotour.model.Review;

/**
 * Admin-only controller
 * @author mfreire
 */
@Controller()
@RequestMapping("tour")
public class TourController {
	
	private static final Logger log = LogManager.getLogger(TourController.class);
	
	@Autowired 
	private EntityManager entityManager;
	
	@Autowired
	private LocalData localData;
	
	@Autowired
	private Environment env;

    RootController root;
	
    @GetMapping(value="/{id}")
    @Transactional
	public String tourOfertado(@PathVariable long id, Model model) {
        TourOfertado tour = entityManager.find(TourOfertado.class, id);
        List<Tour> instancias =  new ArrayList<>(tour.getInstancias());

        List<String> etiquetas = entityManager.createNamedQuery("TourOfertado.getEtiquetas")
                .setParameter("id", id)
                .getResultList();
        long id_guia = tour.getGuia().getId();
        List<String> idiomas = entityManager.createNamedQuery("User.haslanguajes")
                .setParameter("user_id", id_guia)
                .getResultList();
        model.addAttribute("tour",tour);
        model.addAttribute("etiquetas",etiquetas);
        model.addAttribute("idiomas",idiomas);
		return "tour";
	}

    @GetMapping(value="/{id}/pago")
	public String pago(@PathVariable long id, Model model) {
        Tour t = entityManager.find(Tour.class, id);
        List<String> etiquetas = entityManager.createNamedQuery("TourOfertado.getEtiquetas")
        .setParameter("id", id)
        .getResultList();
        long id_guia = t.getDatos().getGuia().getId();
        List<String> idiomas = entityManager.createNamedQuery("User.haslanguajes")
        .setParameter("user_id", id_guia)
        .getResultList();
        model.addAttribute("tour",t);
        model.addAttribute("idiomas",idiomas);
		return "pago";
	}
    @GetMapping(value="/{id}/review")
	public String review(@PathVariable long id, Model model) {
        Tour t = entityManager.find(Tour.class, id);
        List<String> etiquetas = entityManager.createNamedQuery("TourOfertado.getEtiquetas")
        .setParameter("id", id)
        .getResultList();
        long id_guia = t.getDatos().getGuia().getId();
        List<String> idiomas = entityManager.createNamedQuery("User.haslanguajes")
        .setParameter("user_id", id_guia)
        .getResultList();
        model.addAttribute("tour",t);
        model.addAttribute("idiomas",idiomas);
		return "review";
	}


	@PostMapping("/{id}/inscribirse")
    @Transactional
	public String inscribirse(@PathVariable("id") long id,Model model,@RequestParam int turistas,HttpSession session) {
        Tour t = entityManager.find(Tour.class, id); // mejor que PreparedQueries que sólo buscan por ID
        User u = entityManager.find(User.class,      // IMPORTANTE: tiene que ser el de la BD, no vale el de la sesión
            ((User)session.getAttribute("u")).getId());
        model.addAttribute("asistentes", turistas);
        t.addTurista(u, turistas);
        tourOfertado(id, model);
		return "pago";
	}

    @PostMapping("/{id}/valorar")
    @Transactional
    public String valorar(@PathVariable("id") long id, Model model, @RequestParam int valoracion, @RequestParam String textoReview, HttpSession session){
        TourOfertado t = entityManager.find(TourOfertado.class, id); // mejor que PreparedQueries que sólo buscan por ID
        User u = entityManager.find(User.class,      // IMPORTANTE: tiene que ser el de la BD, no vale el de la sesión
            ((User)session.getAttribute("u")).getId());
        Review r = new Review();
        log.info("SE PROCEDE A CREAR LA REVIEW");
        r.setCreador(u);
        r.setDestinatario(t.getGuia());
        r.setPuntuacion(valoracion);
        r.setTexto(textoReview);
        r.setTourValorado(t);
        log.info("La review actual es {}", r);
        entityManager.persist(r);
        entityManager.flush();
        tourOfertado(id, model);
        return "tour";
    }

    @PostMapping("/{id}/pagar")
    @Transactional
	public String pagar(@PathVariable("id") long id,Model model,@RequestParam int numTarjeta, @RequestParam String caducidadTarjeta,
                        @RequestParam int numSecreto,HttpSession session) {
                            //Aunque le pido los datos de tarjeta en el formulario realmente no hago nada con ellos
        Tour t = entityManager.find(Tour.class, id);
        User u = entityManager.find(User.class,
            ((User)session.getAttribute("u")).getId());
        if(numTarjeta!= u.getNumTarjeta()){
            u.setNumTarjeta(numTarjeta);
            u.setCaducidadTarjeta(caducidadTarjeta);
            u.setNumSecreto(numSecreto);
        }
        List<Tour> tours = entityManager.createNamedQuery("AllTours").getResultList();        
        // dumps them via log
        log.info("Dumping table {}", "tour");
        for (Object o : tours) {
            log.info("\t{}", o);
        }        
        // adds them to model
        model.addAttribute("tours", tours);	
		return "index";
	}

    
    @GetMapping("/{id}/crearInstancia")
    @Transactional
    public String crearInstancia(@PathVariable("id") long id, Model model, HttpSession session)
    {
        TourOfertado tour = entityManager.find(TourOfertado.class, id);
        model.addAttribute("tour", tour);
        if (!model.containsAttribute("inicial")) model.addAttribute("inicial", false);

        return "crearInstancia";
    }

    @PostMapping("/crearTour")
	@Transactional
    public String nuevoTour(@RequestParam String pais,
                            @RequestParam String ciudad,
                            @RequestParam String lugar,
                            @RequestParam String titulo,
                            @RequestParam String descripcion,
                            @RequestParam int maxTuristas,
                            @RequestParam double precio,
                            Model model, HttpSession session) {

        TourOfertado tourO = new TourOfertado();

        tourO.setPais(pais);
        tourO.setCiudad(ciudad);
        tourO.setLugar(lugar);
        tourO.setTitulo(titulo);
        tourO.setDescripcion(descripcion);
        tourO.setMaxTuristas(maxTuristas);
        tourO.setPrecio(precio);
        tourO.setDisponible(true);

        User guia = entityManager.find(User.class, ((User)session.getAttribute("u")).getId());
        tourO.setGuia(guia);

        entityManager.persist(tourO);
        entityManager.flush();

        return portada(tourO.getId(), model, session);
    }

    @GetMapping("/{id}/portada")
    @Transactional
    public String portada(@PathVariable("id") long id, Model model, HttpSession session)
    {
        TourOfertado tour = entityManager.find(TourOfertado.class, id);
        model.addAttribute("tour", tour);
        model.addAttribute("inicial", true);

        return "portada";
    }


    @PostMapping("/{id}/actualizarPortada")
    @Transactional
    public String portada(@PathVariable("id") long id, 
                          @RequestParam("portada") MultipartFile portada,
                          @RequestParam("mapa") MultipartFile mapa,
                          Model model, HttpSession session) throws IOException{

        log.info("Updating portada for tour {}", id);
		File f = localData.getFile("src/main/resources/static/img/TourOfertado/Portda", String.valueOf(id)); 
		if (portada.isEmpty()) {
			log.info("fallo al subir la portada: archivo vacio?");
		} else {
			try (BufferedOutputStream stream =
					new BufferedOutputStream(new FileOutputStream(f))) {
				byte[] bytes = portada.getBytes();
				stream.write(bytes);
			} catch (Exception e) {
				log.warn("Error uploading " + id + " ", e);
			}
			log.info("Successfully uploaded portada for {} into {}!", id, f.getAbsolutePath());
		}

        model.addAttribute("inicial", true);
        return crearInstancia(id, model, session);
    }


    @PostMapping("{id}/instancia")
	@Transactional
    public String nuevoTour(@PathVariable("id") long idTourO,
                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaIni,
                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin,
                            Model model, HttpSession session){

        TourOfertado tourO = entityManager.find(TourOfertado.class, idTourO);
        User guia = entityManager.find(User.class, tourO.getGuia().getId());
        Tour tour = new Tour();

        tour.setFechaIni(fechaIni);
        tour.setFechaFin(fechaFin);
        tour.setActTuristas(0);
        tour.setDatos(tourO);
        
        guia.getTourOfertados().add(tour);
        tourO.getInstancias().add(tour);

        entityManager.persist(tour);
        entityManager.flush();

        return "redirect:/tour/" + idTourO;
    }

}
