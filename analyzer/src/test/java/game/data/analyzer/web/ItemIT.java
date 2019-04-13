package game.data.analyzer.web;

import game.data.analyzer.data.*;
import game.data.analyzer.model.Item;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ItemIT {
	TestData testData;
	ItemService itemService;

	@Autowired
	ItemRepository itemRepository;
	@Autowired
	RecipeRepository recipeRepository;
	@Autowired
	ItemGroupRepository itemGroupRepository;
	@Autowired
	LocationRepository locationRepository;
	@Autowired
	RecipeOutputRepository recipeOutputRepository;
	@Autowired
	RecipeInputRepository recipeInputRepository;

	@Before
	public void onStartup() {
		testData = new TestData();
		testData.initRepositories(itemRepository, recipeRepository, itemGroupRepository, locationRepository,
				recipeInputRepository, recipeOutputRepository);
		itemService = new ItemService(itemRepository);
	}

	@Test
	public void testSimpleSearch_1_nullQuery() {
		List<Item> itemsFromRepo = itemService.retrieveByName(null);
		itemsFromRepo.forEach(x -> System.out.println(x.getClass()));
		assertEquals(testData.getItems(), itemsFromRepo);
	}

	@Test
	public void testBierSearch() {
		List<Item> simple = itemService.retrieveByName("bier");
		assertEquals(1, simple.size());
		Item bierFromList = testData.getItems().stream().filter(o -> o.getName().equals("bier")).findFirst().get();
		Item bierFromRepo = simple.get(0);
		assertEquals(bierFromList, bierFromRepo);
	}

	@Test
	public void testSearchForNonexistantItem() {
		List<Item> notInGame = itemService.retrieveByName("paella");
		assertEquals(0, notInGame.size());
	}

}
