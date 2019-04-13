package game.data.analyzer.web;

import game.data.analyzer.data.*;
import game.data.analyzer.model.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*
Initially, I wanted to place this class in the 'data' package and use it also with the DatabaseInitializer.
I then remembered that this data needs to be static so that the tests don't change unexpectedly.
*/

@Data
public class TestData {
	private List<Item> items;
	private List<ItemGroup> itemGroups;
	private List<Recipe> recipes;
	private List<RecipeInput> recipeInputs;
	private List<RecipeOutput> recipeOutputs;
	private List<Location> locations;
	private Set<Location> locationSet;

	public TestData() {
		initData();
	}

	private void initData() {

		//locations
		locations = new ArrayList<>();
		Location velia = new Location("Velia");
		locations.add(velia);
		Location heidel = new Location("Heidel");
		locations.add(heidel);
		locationSet = new HashSet<>(locations);

		//items
		items = new ArrayList<>();
		itemGroups = new ArrayList<>();
		Item wheat = new Item("wheat", "corn flakes", locationSet);
		items.add(wheat);
		ItemGroup cereals = new ItemGroup("cereals", wheat);
		itemGroups.add(cereals);
		Item bier = new Item("bier","glug glug glug", locationSet);
		items.add(bier);
		Item wasser = new Item("wasser", "glug glug", locationSet);
		items.add(wasser);
		Item garmittel = new Item("garmittel","to leaven", locationSet);
		items.add(garmittel);
		Item zucker = new Item("zucker","sweet", locationSet);
		items.add(zucker);

		//recipes
		recipes = new ArrayList<>();
		Recipe bierRecipe = new Recipe("bier");
		RecipeInput bierCereals = new RecipeInput("cereals for beer", cereals, 5);
		RecipeInput bierWasser = new RecipeInput("water for beer", wasser, 6);
		RecipeInput bierGarmittel = new RecipeInput("leavener for beer", garmittel, 2);
		RecipeInput bierZucker = new RecipeInput("sugar for beer", zucker, 1);
		recipeInputs = new ArrayList<>();
		recipeInputs.add(bierCereals);
		recipeInputs.add(bierWasser);
		recipeInputs.add(bierZucker);
		recipeInputs.add(bierGarmittel);
		Set<RecipeInput> bierInputs = new HashSet<>();
		bierInputs.add(bierCereals);
		bierInputs.add(bierWasser);
		bierInputs.add(bierGarmittel);
		bierInputs.add(bierZucker);
		bierRecipe.setInputs(bierInputs);
		Set<RecipeOutput> bierRecipeOutputs = new HashSet<>();
		RecipeOutput bierOutput = new RecipeOutput("beer", bier, 1.0);

		recipeOutputs = new ArrayList<>();
		recipeOutputs.add(bierOutput);
		bierRecipeOutputs.add(bierOutput);
		bierRecipe.setOutputs(bierRecipeOutputs);
		recipes.add(bierRecipe);
	}

	public void initRepositories(ItemRepository itemRepository, RecipeRepository recipeRepository,
								 ItemGroupRepository itemGroupRepository, LocationRepository locationRepository,
								 RecipeInputRepository recipeInputRepository,
								 RecipeOutputRepository recipeOutputRepository) {
		System.out.println("Populating the database");
		items.forEach(x -> itemRepository.save(x));
		itemGroups.forEach(x -> itemGroupRepository.save(x));
		locations.forEach(x -> locationRepository.save(x));
		recipeInputs.forEach(x -> recipeInputRepository.save(x));
		recipeOutputs.forEach(x -> recipeOutputRepository.save(x));
		recipes.forEach(x -> recipeRepository.save(x));
	}
}
