package game.data.analyzer.web;

import game.data.analyzer.data.ItemGroupRepository;
import game.data.analyzer.data.ItemRepository;
import game.data.analyzer.data.RecipeRepository;
import game.data.analyzer.model.Recipe;
import game.data.analyzer.model.RecipeInput;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RecipeIT {
	TestData testData;
	RecipeService recipeService;

	@Autowired
	ItemRepository itemRepository;
	@Autowired
	ItemGroupRepository itemGroupRepository;
	@Autowired
	RecipeRepository recipeRepository;

	@Before
	public void onStartup() {
		System.out.println("Populating the database");
		testData = new TestData();
		testData.getItemGroups().forEach(x -> itemGroupRepository.save(x));
		testData.getItems().forEach(x -> itemRepository.save(x));
		testData.getRecipes().forEach(x -> recipeRepository.save(x));
		recipeService = new RecipeService(recipeRepository);
	}

	@Test
	public void testSimpleSearch_1_nullQuery() {
		List<Recipe> simple = recipeService.retrieveByName(null);
		assertEquals(1, simple.size());
		assertEquals(testData.getRecipes(), simple);
	}

	@Test
	public void testBierSearch() {
		List<Recipe> simple = recipeService.retrieveByName("bier");
		System.out.println(simple.size());
		simple.forEach(x -> System.out.println(x.getName()));
		assertEquals(1, simple.size());
		assertEquals(testData.getRecipes(), simple);
	}

	@Test
	public void testSearchForNonexistantRecipe() {
		List<Recipe> notInGame = recipeService.retrieveByName("paella");
		assertEquals(0, notInGame.size());
	}

	@Test
	public void testRecipeInputs() {
		Recipe bierInRepo = recipeService.retrieveByName("bier").get(0);
		Recipe bierInList = testData.getRecipes().get(0);
		assertEquals(bierInList.getInputs().size(),bierInRepo.getInputs().size());
		RecipeInput input1 = testData.getRecipeInputs().get(0);
		RecipeInput input2 = testData.getRecipeInputs().get(1);
		bierInRepo.getInputs().forEach(input -> System.out.println("input use = "+input.getName()));
		bierInList.getInputs().forEach(input -> System.out.println("input use = "+input.getName()));
		System.out.println("size of inputs in repo = "+bierInRepo.getInputs().size());
		System.out.println("size of inputs in list = "+bierInList.getInputs().size());
		assertFalse(bierInRepo.getInputs().contains(null));
		assertTrue(bierInRepo.getInputs().stream().filter(o -> o.getName().equals(input1.getName())).findFirst().isPresent());
		assertTrue(bierInRepo.getInputs().stream().filter(o -> o.getName().equals(input2.getName())).findFirst().isPresent());
	}

}
