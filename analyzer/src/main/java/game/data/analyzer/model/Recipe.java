package game.data.analyzer.model;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Recipe extends GameObject {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	String name;
	String description;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	//@JoinColumn
	Set<RecipeInput> inputs;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn
	Set<RecipeOutput> outputs;

	public Recipe() {}
	public Recipe(String name) {
		this.name = name;
	}

	public void addRecipeInput(RecipeInput recipeInput) {
		if (inputs == null) {
			inputs = new HashSet<>();
		}
		inputs.add(recipeInput);
	}

	public void addRecipeOutput(RecipeOutput recipeOutput) {
		if (outputs == null) {
			outputs = new HashSet<>();
		}
		outputs.add(recipeOutput);
	}


}
