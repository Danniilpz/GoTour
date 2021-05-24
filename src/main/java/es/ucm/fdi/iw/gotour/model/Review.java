package es.ucm.fdi.iw.gotour.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class Review {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String texto;
	private int puntuacion;
	
	@NotNull
	@ManyToOne(targetEntity=User.class)
	private User creador;

	@ManyToOne(targetEntity=User.class)
	private User destinatario;

	@ManyToOne(targetEntity=Tour.class)
	private Tour tourValorado;

}
