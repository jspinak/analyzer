package game.data.analyzer.model;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class ItemObject extends GameObject {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String description;

/*
	@OneToMany//(cascade = CascadeType.ALL,
			(fetch = FetchType.EAGER)//, //EAGER causes problems when there is no associated repository
			//mappedBy = "effectItem") //in the relational database, creates an "item_id" column in the "effect" table
	@JoinColumn
	private Set<Effect> effects = new HashSet<>();

	public Set<Effect> getEffects() {
		return effects;
	}
*/

/*
	private void makeJsonEffects() {
		effects.forEach(effect -> {
			effectsJson.add(effect.toJson());
		});
	}

	public void prepareJson() {
		makeJsonEffects();
	}
	*/


}
