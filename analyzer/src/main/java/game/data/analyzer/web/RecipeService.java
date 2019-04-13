package game.data.analyzer.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import game.data.analyzer.data.RecipeRepository;
import game.data.analyzer.model.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class RecipeService {

	@Autowired
	private RecipeRepository recipeRepository;

	public RecipeService(RecipeRepository recipeRepository) {
		this.recipeRepository = recipeRepository;
	}

	public Recipe get(long id) {
		Optional<Recipe> recipe = recipeRepository.findById(id);
		if (!recipe.isPresent()) throw new ItemDoesntExist();
		else return recipe.get();
	}

	public List<Recipe> findAll() {
		return recipeRepository.findAll();
	}

	public void save(Recipe recipe) {
		recipeRepository.save(recipe);
	}

	public List<Recipe> retrieveByName(String name) {
		if (name == null || name.equals("")) return findAll();
		return recipeRepository.findByNameContainingIgnoreCase(name);
	}
/*
	public void prepareJson() {
		List<Recipe> recipes = recipeRepository.findAll();
		recipes.forEach(recipe -> {
			recipe.getInputs().forEach(input -> {
				input.getItem().prepareJson();
			});
		});
	}
	*/
}
