package game.data.analyzer.web;

import game.data.analyzer.data.*;
import org.springframework.boot.web.servlet.ServletContextInitializer;

import javax.servlet.ServletContext;

public class TestDBInitializer implements ServletContextInitializer {
	private TestData testData;
	private ItemRepository itemRepository;
	private RecipeRepository recipeRepository;
	private ItemGroupRepository itemGroupRepository;
	private LocationRepository locationRepository;
	private RecipeInputRepository recipeInputRepository;
	private RecipeOutputRepository recipeOutputRepository;

	@Override
	public void onStartup(ServletContext servletContext) {
		testData = new TestData();
		testData.initRepositories(itemRepository, recipeRepository, itemGroupRepository, locationRepository,
				recipeInputRepository, recipeOutputRepository);
	}
}
