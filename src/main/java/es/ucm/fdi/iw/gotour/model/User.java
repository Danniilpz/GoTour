package es.ucm.fdi.iw.gotour.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.persistence.ManyToMany;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.ElementCollection;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * A user; can be an Admin, a User, or a Moderator
 *
 * Users can log in and send each other messages.
 *
 * @author mfreire
 */
/**
 * An authorized user of the system.
 */
@Entity
@Data
@NoArgsConstructor
@NamedQueries({
        @NamedQuery(name="User.byUsername",
                query="SELECT u FROM User u "
                        + "WHERE u.username = :username AND u.enabled = 1"),
		@NamedQuery(name="User.byId",
				query="SELECT u FROM User u "
						+ "WHERE u.id = :id AND u.enabled = 1"),
        @NamedQuery(name="User.hasUsername",
                query="SELECT COUNT(u) "
                        + "FROM User u "
                        + "WHERE u.username = :username"),
		@NamedQuery(name="userByLogin",
				query="select u from User u where u.email = :loginParam"),
		@NamedQuery(name="AllUsers", query="Select u from User u"),
		@NamedQuery(name="RolSearchUsers", query="SELECT u FROM User u "
		+ "WHERE Roles = :rolparameter"),
		@NamedQuery(name="AllUsersByPuntuacion",
				query="select u from User u order by puntuacion desc"),
		@NamedQuery(name="UsersByAdminSearchEmailUser", query="SELECT u FROM User u "
		+ "WHERE u.username LIKE :usernameParam OR u.email LIKE :emailParam "),
		@NamedQuery(name="UsersByAdminSearchUser", query="SELECT u FROM User u "
		+ "WHERE u.username LIKE :usernameParam"),
		@NamedQuery(name="UsersByAdminSearchEmail", query="SELECT u FROM User u "
		+ "WHERE  u.email LIKE :emailParam "
		)
		// @NamedQuery(name="User.byTour",
		// 		query="select u FROM User u JOIN User_Tours_Asistidos t WHERE t.Tours_Asistidos_Id= :tourParam")
})

@NamedNativeQueries({
	@NamedNativeQuery(name="User.getToursOfrecidos",
		query="SELECT * from tour_ofertado WHERE Guia_id = :guia_id"),
		@NamedNativeQuery(name="User.getIdToursOfrecidos",
		query="SELECT id from tour_ofertado WHERE Guia_id = :guia_id"),
	@NamedNativeQuery(name="User.getToursConcretos",
		query="SELECT * from tour WHERE Datos_id = :datos_id"),
	@NamedNativeQuery(name="User.getReviewsRecibidas",
		query="SELECT * FROM Review WHERE Destinatario_id = :dest"),
	@NamedNativeQuery(name="User.getcountReview",
		query="SELECT COUNT(Id) FROM Review WHERE Creador_id = :creador"),
	@NamedNativeQuery(name="User.haslanguajes",
	query="SELECT idiomas_hablados from user_idiomas_hablados WHERE user_idiomas_hablados.User_id = :user_id")
})
public class User implements Transferable<User.Transfer> {


	private static Logger log = LogManager.getLogger(User.class);	

	public enum Role {
		USER,			// used for logged-in, non-priviledged users
		ADMIN,			// used for maximum priviledged users
		MODERATOR,		// remove or add roles as needed
	}
	
	// do not change these fields

	/** 
	 * not a DB column, but very useful to handle passwords; 
	 * see passwordMatches & encodePassword 
	 * All those annotations prevent persistence and Lombok-generated getters & setters
	 */
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	/** username for login purposes; must be unique */
	@Column(nullable = false, unique = true)
	private String username;
	/** encoded password; use setPassword(SecurityConfig.encode(plaintextPassword)) to encode it  */
	@Column(nullable = false)
	private String password;
	@Column(nullable = false)
	private String roles; // split by ',' to separate roles
	private int enabled;

	// application-specific fields
	@NotNull
	@Size(max=244)
	private String nombre;

	@NotNull
	@Size(max=244)
	private String apellidos;
	
	private String email;
	@Size(max=4)
    private int numTarjeta;
	private String caducidadTarjeta;
	private int numSecreto;

	@NotNull
	private long numTelefono;

	@NotNull
	@Size(max=100)
	private String preguntaSeguridad;

	@NotNull
	@Size(max=100)
	private String respuestaSeguridad;

	private int puntuacion;

	@OneToMany(targetEntity=Tour.class)
	@JoinColumn(name="creador_id")
	private List<Tour> tourOfertados=new ArrayList<>();

	@ManyToMany(targetEntity=Tour.class, fetch=FetchType.EAGER)
	private List<Tour> toursAsistidos=new ArrayList<>(); 

	@OneToMany(targetEntity=Review.class, fetch=FetchType.EAGER)
	@JoinColumn(name="Creador_id")
	private List<Review> reviewsHechas=new ArrayList<>();

	@OneToMany
	@JoinColumn(name="Creador_id")
	private List<Mensaje> mensajes = new ArrayList<>();
	
	@OneToMany(targetEntity=Review.class)
	@JoinColumn(name="destinatario_id")
	private List<Review> reviewsRecibidas=new ArrayList<>();

	@ElementCollection
	private List<String> idiomasHablados=new ArrayList<>();


	@OneToMany
	@JoinColumn(name = "Sender_id")
	private List<Mensaje> sent = new ArrayList<>();
	@OneToMany
	@JoinColumn(name = "Recipient_id")	
	private List<Mensaje> received = new ArrayList<>();
    
	@ManyToMany(fetch = FetchType.EAGER)
	private List<Reporte> reporteCreados = new ArrayList<>();
	
	@ManyToMany(fetch = FetchType.EAGER)
	private List<Reporte> reporteRecibidos = new ArrayList<>();
	
	@ManyToMany(fetch = FetchType.EAGER)
	private List<Reporte> reportesAdmin = new ArrayList<>();	
	
	// utility methods
	
	/**
	 * Checks whether this user has a given role.
	 * @param role to check
	 * @return true iff this user has that role.
	 */
	public boolean hasRole(Role role) {
		String roleName = role.name();
		return Arrays.stream(roles.split(","))
				.anyMatch(r -> r.equals(roleName));
	}
	
	public void addTour(Tour t){
		this.toursAsistidos.add(t);
	}

	public void addLanguaje(String idioma){
		idiomasHablados.add(idioma);
	}

	public void addReporteRespuestas(Reporte e){
		reporteRecibidos.add(e);
	}
	public void addReporteCreados(Reporte e){
		reporteCreados.add(e);
	}

	public void addReportesAdmin(Reporte e){
		reportesAdmin.add(e);
	}

    @Getter
    @AllArgsConstructor
    public static class Transfer {
		private long Id;
		private String Apellidos;
		private String Nombre;
        private String Username;
		private long Numtelefono;
		private int Puntuacion;
		private List<Tour> TourOfertados;
		private List<Tour> ToursAsistidos;
		private List<Review> ReviewsHechas;
		private List<Mensaje> Sent;
		private List<Mensaje> Received;
		private List<Review> ReviewsRecibidas;
		private List<String> IdiomasHablados;
		private List<Reporte> ReporteRecibidos;
		private List<Reporte> ReporteCreados;
		private List<Reporte> ReportesAdmin;

    }

	@Override
    public Transfer toTransfer() {
		return new Transfer(id, apellidos, nombre,	username, numTelefono, puntuacion, tourOfertados, toursAsistidos, reviewsHechas, sent,  received, reviewsRecibidas, idiomasHablados, reporteRecibidos, reporteCreados, reportesAdmin);
    }

	@Override
	public String toString() {
		return toTransfer().toString();
	}
}
