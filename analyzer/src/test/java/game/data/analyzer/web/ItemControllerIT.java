package game.data.analyzer.web;

import game.data.analyzer.data.DatabaseInitializer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ItemControllerIT {

	MockMvc mockMvc;

	@Autowired
	protected WebApplicationContext wac;

	@Autowired
	DatabaseInitializer dbInit;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
		dbInit.onStartup(mockMvc.getDispatcherServlet().getServletContext());
	}

	@Test
	public void simpleItemSearch() throws Exception {
		mockMvc.perform(get("/"))
				.andExpect(status().isOk())
				.andExpect(model().attribute("items", hasItem(hasProperty("name", is("bier")))));
	}

	@Test
	public void recipes() throws Exception {
		mockMvc.perform(get("/"))
				.andExpect(status().isOk())
				.andExpect(model().attribute("recipes",
						hasItem(hasProperty("name",
								is("Bier"))))); //this is the recipe use
	}

	@Test
	public void bierRecipeInputs() throws Exception {
		mockMvc.perform(get("/"))
				.andExpect(status().isOk())
				.andExpect(model().attribute("recipes",
						hasItem(hasProperty("inputs",
								hasItem(hasProperty("name", is("water for beer"))))))); //this is the recipe input use
	}

	@Test
	public void bierRecipeInputItem() throws Exception {
		mockMvc.perform(get("/"))
				.andExpect(status().isOk())
				.andExpect(model().attribute("recipes",
						hasItem(hasProperty("inputs",
								hasItem(hasProperty("item",
										hasProperty("name", is("cereals")))))))); //this is the recipe input item use
	}
}