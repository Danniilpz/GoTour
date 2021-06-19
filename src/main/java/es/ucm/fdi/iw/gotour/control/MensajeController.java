package es.ucm.fdi.iw.gotour.control;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import es.ucm.fdi.iw.gotour.model.Mensaje;
import es.ucm.fdi.iw.gotour.model.Transferable;
import es.ucm.fdi.iw.gotour.model.User;
import es.ucm.fdi.iw.gotour.model.Tour;

/**
 * User-administration controller
 * 
 * @author mfreire
 */
@Controller()
@RequestMapping("mensaje")
public class MensajeController {
	
	private static final Logger log = LogManager.getLogger(MensajeController.class);
	
	@Autowired 
	private EntityManager entityManager;
	

	@GetMapping(path = "/tour/{id}/received", produces = "application/json")
	@Transactional // para no recibir resultados inconsistentes
	@ResponseBody  // para indicar que no devuelve vista, sino un objeto (jsonizado)
	public List<Mensaje.Transfer> retrieveMensajes(@PathVariable long id, @RequestParam String topic, HttpSession session) {
		Tour u = entityManager.find(Tour.class, id);
		if((u.getTopicId()).equals(topic)){
			return u.getMensajes().stream().map(Transferable::toTransfer).collect(Collectors.toList());
		}
		return null;
		
	}	
	
	/*@GetMapping(path = "/unread", produces = "application/json")
	@ResponseBody
	public String checkUnread(HttpSession session) {
		long userId = ((User)session.getAttribute("u")).getId();		
		long unread = entityManager.createNamedQuery("Mensaje.countUnread", Long.class)
			.setParameter("userId", userId)
			.getSingleResult();
		session.setAttribute("unread", unread);
		return "{\"unread\": " + unread + "}";
	}*/
}