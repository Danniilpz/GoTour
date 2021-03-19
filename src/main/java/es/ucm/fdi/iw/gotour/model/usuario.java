package es.ucm.fdi.iw.gotour.model;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.Data;


@Entity
@Data
public class usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	
	private String nombre;
	private String apellidos;
	
	@OneToMany(targetEntity = Mensajes.class)
	@JoinColumn(name="id_creador")
	private List<Mensajes> mensajes = new ArrayList<>();
	/*@OneToMany
	private List<Tour> toursofrecidos=new ArrayList<>();

    @ManyToMany
	private List<Tour> tourscontratados=new ArrayList<>();

	@ManyToMany
	private List<Review> reviews=new ArrayList<>();
	*/

	@Override
	public String toString() {
		return "usuario #" + id;
	}		
}
