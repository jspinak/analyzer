package game.data.analyzer.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Data
@EqualsAndHashCode(exclude = "effects")
//@ToString(exclude = "effects")
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

	@ManyToMany(
			fetch = FetchType.EAGER,
			cascade = {CascadeType.PERSIST, CascadeType.MERGE}
	)
	@JoinTable(name = "ItemGroup_Effect",
			joinColumns = { @JoinColumn(name = "itemGroup_id") },
			inverseJoinColumns = { @JoinColumn(name = "effect_id") }
	)
	Set<Effect> effects = new HashSet<>();

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
*/

	public void addEffect(Effect effect) {
		effects.add(effect);
		effect.returnItemGroups().add(this);
	}

	public ItemGroup DTO() {
		return new ItemGroup(name);
	}

	public Set<Effect> getEffects() {
		return effects.stream().map(effect -> effect.DTO()).collect(Collectors.toSet());
	}

}
