package game.data.analyzer.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
//@ToString(exclude = {"inputItem","recipe"})
public class RecipeInput extends GameObject {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	String name;
	String description;
	@OneToOne //an item can be an input in many recipes, but can only be in one RecipeInput
	//(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private ItemObject item;//inputItem;
	private int amount;

	@ManyToOne
	@JoinColumn
	private Recipe recipe;

	public RecipeInput() {}
	public RecipeInput(String name, ItemObject item, int amount) {
		this.name = name;
		this.item = item;
		this.amount = amount;
	}

/*	public String getName() {
		return name;
	}

	public int getAmount() { return amount; }

	public ItemObject getItem() {
		return item;
	}

	public Recipe getRecipe() {
		return recipe;
	}
*/
}
