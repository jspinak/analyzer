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

	@ManyToMany(fetch = FetchType.EAGER, //EAGER ok here since few effects
			mappedBy = "effectItems") //for ManyToMany, sets the join table use
	Set<Effect> effects = new HashSet<>();

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
	public void addEffect(Effect effect) {
		if (effects == null) {
			effects = new HashSet<>();
		}
		effects.add(effect);
		effect.getEffectItems().add(this);
	}
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
