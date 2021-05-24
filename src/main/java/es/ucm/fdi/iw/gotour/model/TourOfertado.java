package es.ucm.fdi.iw.gotour.model;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.persistence.FetchType;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
//INSERT INTO TOUR_OFERTADO VALUES (1, 'Alcala', 'tour1', 1, '2021-12-12 20:00:00', '2021-12-12 20:00:00', 'tour1', 'adsa', 2, 'adas', 'dada', 8, 'adas', 2);

@Entity
@Data
@NamedQueries({
	@NamedQuery(name="TourOfertado.getTour",
			query="SELECT u FROM TourOfertado u "
					+ "WHERE u.id = :id "),
	@NamedQuery(name="AllToursOfer", query="Select t from TourOfertado t")

})
@NamedNativeQueries({
	@NamedNativeQuery(name="TourOfertado.getEtiquetas",
	query="SELECT ETIQUETAS FROM TOUR_OFERTADO_ETIQUETAS WHERE TOUR_OFERTADO_ID = :id")
})
public class TourOfertado {

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
	private int maxTuristas;

	@OneToMany (mappedBy="datos", cascade = CascadeType.ALL, orphanRemoval = true, fetch=FetchType.EAGER)
	private List<Tour> instancias = new ArrayList<>();

	@NotNull
	@ManyToOne
	private User guia;
	
	@ElementCollection
	@CollectionTable(joinColumns = @JoinColumn(name="TourOfertado_id"))
	private List<String> etiquetas = new ArrayList<String>();

	@Override
	public String toString() {
		return "";
	}
	private String formatDate(LocalDateTime date){
		String fecha=date.toString();
		String[] parts=fecha.split("-");
		return parts[2].split("T")[0]+"/"+parts[1]+"/"+parts[0];
	}
	public String getFechaUltimaInstancia(){
		return formatDate(instancias.get(instancias.size()-1).getFechaIni());
	}
	
}