package es.ucm.fdi.iw.gotour.model;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import lombok.Data;

@Entity
@Data
public class Tour {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotNull
	private String descripcion;

	private String portada;

	@NotNull
	private String ciudad;

	@NotNull
	private String titulo;

	private String lugar;

	@NotNull
	private String pais;

	private String mapa;
	private boolean disponible;

	@NotNull
	private double precio;

	@NotNull
	private Date fecha_ini;

	@NotNull
	private Date fecha_fin;

	@ElementCollection
	private List<String>  tags = new ArrayList<>();

	@NotNull
	@ManyToOne
	private User guia;

	@ManyToMany (mappedBy="tourscontratados")
	private List<User>  turistas = new ArrayList<>();
	
	@Override
	public String toString() {
		return "";
	}
}