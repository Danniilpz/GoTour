package es.ucm.fdi.iw.gotour.model;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Data
@Getter
@NoArgsConstructor
@NamedQueries({
	@NamedQuery(name="AllTours", query="Select t from Tour t"),
	@NamedQuery(name="ToursBySearch", query="Select t from Tour t where t.datos.pais=:paisParam and "+
																		"t.datos.ciudad=:ciudadParam and "+
																		"t.datos.lugar=:lugarParam and "+
																		"(t.fechaIni<:fechaIniParam or "+
																		"t.fechaIni=:fechaIniParam) and "+
																		"(t.fechaFin>:fechaFinParam or "+
																		"t.fechaFin=:fechaFinParam)"),
	@NamedQuery(name="Tour.getTour",
		query="SELECT u FROM Tour u "
				+ "WHERE u.id = :id "),
	@NamedQuery(name="Tour.getFirstTour",
		query="SELECT u FROM Tour u "
			+ "WHERE u.datos.guia.id = :guia_id "
			+ "ORDER BY u.fechaIni DESC"),
	@NamedQuery(name="Tour.getToursByUser",
		query="SELECT u FROM Tour u "
			+ "WHERE u.datos.guia.id = :guia_id")
})

public class Tour {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	 @NotNull
	 @ManyToOne (targetEntity=TourOfertado.class)
	 private TourOfertado datos;

	 @NotNull
	private LocalDateTime fechaIni;

	@NotNull
	private LocalDateTime fechaFin;

	@NotNull
	private int actTuristas;

	@NotNull
	private String topicId;

	@OneToMany (mappedBy="tourValorado", fetch=FetchType.EAGER)
	private List<Review>  reviews = new ArrayList<>();
	
	//@OneToOne
	//private Chat chat;

	@OneToMany
	@JoinColumn(name = "tour_id")
	private List<Mensaje> mensajes = new ArrayList<>();

	@ManyToMany (mappedBy="toursAsistidos", fetch=FetchType.EAGER)
	public List<User>  turistas = new ArrayList<>();

	public void addTurista(User u,int numero){
		if(datos.getMaxTuristas() >= (actTuristas+numero)){
			turistas.add(u);
			actTuristas+=numero;
			u.addTour(this);
		}
	}
	public String getFechaIniString(){
		return fechaIni.toString().replace('T', ' ');
	}
	public String getFechaFinString(){
		return fechaFin.toString().replace('T', ' ');
	}
	@Override
	public String toString() {
		return "";
	}
	public String getFirstTourInfo(){
		String[] parts = fechaIni.toString().split("-");
		return "Guía desde "+parts[1]+"/"+parts[0];
	}
}
