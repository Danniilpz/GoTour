package es.ucm.fdi.iw.gotour.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.persistence.ManyToMany;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.ElementCollection;
import javax.validation.constraints.Size;


import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Entity
@Data
@NamedQueries({
	@NamedQuery(name="AllReportes", query="Select r from Reporte r"),
	@NamedQuery(name="AllTypeReportes", query="Select r from Reporte r  where r.tipo=:tipo"),
	@NamedQuery(name="ReportesByAdminSearch", query="Select r from Reporte r  where r.creador LIKE :usernameParam OR r.motivo LIKE :motivoParam OR r.texto LIKE :textoParam")
	
})
public class Reporte {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long Id;

	private String motivo;

	private String texto;
	
	@NotNull
	@ManyToOne(targetEntity=User.class)
	private User creador;

	@ManyToOne(targetEntity=User.class)
	private User userReportado;

	@ManyToOne(targetEntity=Tour.class)
	private Tour tourReportado;
	
    @ManyToOne(targetEntity=User.class)
	private User userContestado;

	private String tipo;

	private boolean contestada;

		
	

}