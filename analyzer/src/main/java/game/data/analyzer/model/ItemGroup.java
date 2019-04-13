package game.data.analyzer.model;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data //Lombok causes an out-of-bounds exception. problem with Item and ItemGroup.
//a group contains items which contain the group...
//@ToString(exclude = "items")
public class ItemGroup extends ItemObject {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	String name;
	String description;

	/*
	@OneToMany//(//fetch = FetchType.EAGER,
				//mappedBy = "group")
	@JoinColumn
	private Set<Item> items;
	*/

	//@OneToMany//(cascade = CascadeType.ALL,
			//(fetch = FetchType.EAGER)//, //EAGER causes problems when there is no associated repository
			//mappedBy = "effectItem") //in the relational database, creates an "item_id" column in the "effect" table
/*	@ManyToMany(fetch = FetchType.EAGER, //EAGER ok here since few effects
			mappedBy = "effectItemGroups") //for ManyToMany, sets the join table use
	Set<Effect> effects = new HashSet<>();
*/
	public ItemGroup() {}
	public ItemGroup(String name, Item... items) {
		this.name = name;
		for (Item item : items) {
			//addItem(item);
		}
	}
/*
	public void addItem(Item item) {
		if (items == null) {
			items = new HashSet<>();
		}
		items.add(item);
		item.setGroup(this);
	}


	public void addEffect(Effect effect) {
		if (effects == null) {
			effects = new HashSet<>();
		}
		effects.add(effect);
		effect.getEffectItemGroups().add(this);
	}
*/
}
