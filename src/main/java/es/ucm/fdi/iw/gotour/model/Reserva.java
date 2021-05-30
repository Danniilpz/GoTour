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
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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
@NamedQueries({@NamedQuery(name="User.Reserva",
        query="select r FROM Reserva r WHERE r.usuario.id= :userParam and r.tourReservado.id =:tourParam"),
        //@NamedQuery(name="deleteReserva",
        //    query ="delete FROM Reserva r WHERE r.usuario.id= :userParam and r.tourReservado.id =:tourParam")
    @NamedQuery(name="deleteReserva",
        query ="delete FROM Reserva r WHERE r.id= :reservaParam")
})

public class Reserva{


	private static Logger log = LogManager.getLogger(User.class);	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
    @NotNull
    private int asistentes;

    @NotNull
    @ManyToOne
    private User usuario;

    @NotNull
    @ManyToOne
    private Tour tourReservado;

    @Getter
    @AllArgsConstructor
    public static class Transfer {
		private long id;
		
        private int asistentes;

        private User usuario;

		private Tour tourReservado;

    }
}
