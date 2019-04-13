package game.data.analyzer.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Entity
//@Data
//@ToString(exclude = {"outputItem","recipe"})
public class RecipeOutput extends GameObject {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	String name;
	String description;
	@OneToOne //an item can be an output in many recipes, but can only be in one RecipeOutput
	private ItemObject outputItem;
	private double productionProbability; //<100% when when production can fail (for example, when item is a sideproduct)
	@ManyToOne
	@JoinColumn
	private Recipe recipe;

	public RecipeOutput() {
		productionProbability = 1.0;
	}

	public RecipeOutput(String name, ItemObject item, double productionProbability) {
		this.name = name;
		this.outputItem = item;
		this.productionProbability = productionProbability;
	}

	public String getName() {
		return name;
	}

	public ItemObject getItem() {
		return outputItem;
	}

	public double getProductionProbability() {
		return productionProbability;
	}

	public Recipe getRecipe() {
		return recipe;
	}
}
