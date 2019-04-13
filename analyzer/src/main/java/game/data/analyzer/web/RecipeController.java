package game.data.analyzer.web;

import game.data.analyzer.model.Recipe;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin //enables the API to be accessed by the React app.
public class RecipeController {

	private final RecipeService recipeService;
	private List<Recipe> recipes = new ArrayList<>();

	public RecipeController(RecipeService recipeService) {
		this.recipeService = recipeService;
	}

	@GetMapping(value = "/api/recipes")
	public @ResponseBody List<Recipe> getRecipes() {
		//recipeService.prepareJson();
		return recipeService.findAll();
	}
}
