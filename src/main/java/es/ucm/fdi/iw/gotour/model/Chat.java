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

@Entity
@Data
public class Chat {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
    @ManyToMany 
	private List<Usuario> users = new ArrayList<>();
	@OneToMany
	@JoinColumn(name="id_chat")
	private List<Mensajes> mensajes = new ArrayList<>();


    @Override
	public String toString() {
		return "Chat #" + id;
	}
}
