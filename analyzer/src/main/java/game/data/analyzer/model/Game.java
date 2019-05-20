package game.data.analyzer.model;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Game {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	String name;
	String description;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	Set<GameObject> gameObjects = new HashSet<>();


}
