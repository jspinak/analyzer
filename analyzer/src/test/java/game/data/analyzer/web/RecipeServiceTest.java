package game.data.analyzer.web;

import game.data.analyzer.data.ItemRepository;
import game.data.analyzer.data.RecipeRepository;
import game.data.analyzer.model.Item;
import game.data.analyzer.model.Recipe;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class RecipeServiceTest {

	//This field is required for the tests to work but will cause spotBugs to detect dodgy code
	@Rule
	public MockitoRule mockitoRule = MockitoJUnit.rule();
	@Mock
	private ItemRepository itemRepository;
	private List<Item> itemList;
	private ItemService itemService;
	@Mock
	private RecipeRepository recipeRepository;
	private List<Recipe> recipeList;
	private RecipeService recipeService;
	private TestData testData;

	@Before
	public void prepareData() {
		testData = new TestData();

		recipeService = new RecipeService(recipeRepository);
		recipeList = testData.getRecipes();
		when(recipeRepository.findAll()).thenReturn(recipeList);

		itemService = new ItemService(itemRepository);
		itemList = testData.getItems();
		when(itemRepository.findAll()).thenReturn(itemList);
	}

	@Test
	public void findAll() {
		recipeList.forEach(x -> System.out.println(x.getName()));
		List<Recipe> l = recipeRepository.findAll();
		assertEquals(1, l.size());
		assertEquals(1,recipeService.findAll().size());
	}

	private void searchItemsByName(String s, int numberReturned) {
		List<Item> itemsReturnedInSearch = itemService.retrieveByName(s);
		assertEquals(numberReturned, itemsReturnedInSearch.size());
		assertTrue(itemsReturnedInSearch.containsAll(itemRepository.findAll()));
	}

	private void searchRecipesByName(String s, int numberReturned) {
		List<Recipe> recipesReturnedInSearch = recipeService.retrieveByName(s);
		assertEquals(numberReturned, recipesReturnedInSearch.size());
		assertTrue(recipesReturnedInSearch.containsAll(recipeRepository.findAll()));
	}

	@Test
	public void testSearchItemsByNoName() {
		int totalItems = testData.getItems().size();
		searchItemsByName(null, totalItems);
		searchItemsByName("", totalItems);
	}

	@Test
	public void testSearchRecipesByNoName() {
		searchRecipesByName(null, 1);
		searchRecipesByName("", 1);
	}

}