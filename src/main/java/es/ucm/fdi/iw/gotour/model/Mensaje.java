package es.ucm.fdi.iw.gotour.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.persistence.JoinColumn;
import lombok.Data;
import lombok.Getter;
import lombok.AllArgsConstructor;
import javax.validation.constraints.NotNull;
/**
 * A message that users can send each other.
 *
 * @author mfreire
 */
@Entity
@Data
public class Mensaje implements Transferable<Mensaje.Transfer> {
	
	private static Logger log = LogManager.getLogger(Mensaje.class);	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@ManyToOne
	private User sender;

	private String text;
	
	private LocalDateTime dateSent;

	@ManyToOne
	private Tour tour;


	
	/**
	 * Objeto para persistir a/de JSON
	 * @author mfreire
	 */
    @Getter
    @AllArgsConstructor
	public static class Transfer {
		private long id_sender;
		private String from;
		private String sent;
		private String text;

		public Transfer(Mensaje m) {
			this.id_sender = m.getSender().getId();
			this.from = m.getSender().getUsername();
			this.sent = DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(m.getDateSent());
			this.text = m.getText();
		}
	}

	@Override
	public Transfer toTransfer() {
		return new Transfer(
			sender.getId(),
			sender.getUsername(),
			DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(dateSent),
			text
        );
    }

}
