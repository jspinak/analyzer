package game.data.analyzer.data;

import game.data.analyzer.model.*;
import game.data.analyzer.web.*;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class DatabaseInitializer implements ServletContextInitializer {

    private ItemService itemService;
    private RecipeService recipeService;
	private LocationService locationService;
	private EffectService effectService;
	private ItemGroupService itemGroupService;
	private RecipeInputRepository recipeInputRepository;
	private RecipeOutputRepository recipeOutputRepository;

    public DatabaseInitializer(ItemService itemService, ItemGroupService itemGroupService,
							   RecipeService recipeService, LocationService locationService,
							   EffectService effectService, RecipeInputRepository recipeInputRepository,
							   RecipeOutputRepository recipeOutputRepository) {
        this.itemService = itemService;
        this.itemGroupService = itemGroupService;
        this.recipeService = recipeService;
		this.locationService = locationService;
		this.effectService = effectService;
		this.recipeInputRepository = recipeInputRepository;
		this.recipeOutputRepository = recipeOutputRepository;
	}

    @Override
    public void onStartup(ServletContext servletContext) {
        System.out.println("populate the database");

        //locations
		Location velia = new Location("Velia");
		Location heidel = new Location("Heidel");
		locationService.save(velia);
		locationService.save(heidel);
		Set<Location> locations = new HashSet<>();
		locations.add(velia);
		locations.add(heidel);

		List<Location> list =locationService.findAll();

		//effects
		Effect baseCooking = new Effect("Cooking", 1.0, "Base cooking ingredient");
		effectService.save(baseCooking);
		Effect workerRegeneration1 = new Effect("Workers",3.0,"Regenerate worker stamina");
		//effectService.save(workerRegeneration1);
		Effect workerRegeneration2 = new Effect("Workers",2.0,"Regenerate worker stamina");
		//effectService.save(workerRegeneration2);
		Set<Effect> baseCookingEffects = new HashSet<>();
		baseCookingEffects.add(baseCooking);

		//items
		Item bier = itemService.save(new Item("bier", "glug glug glug"));
		bier.addLocation(velia);
		bier.addLocation(heidel);
		bier.addEffect(workerRegeneration1);
		bier.addEffect(workerRegeneration2);
		bier = itemService.save(bier);

		Item wheat = new Item("wheat");
		wheat = itemService.save(wheat);
		wheat.addLocation(heidel);
		wheat.addEffect(baseCooking);
		wheat = itemService.save(wheat);

		ItemGroup cereals = new ItemGroup("cereals", wheat);
		//itemGroupService.save(cereals);
		cereals.addEffect(baseCooking);
		itemGroupService.save(cereals);

		Item wasser = new Item("wasser", "glug glug"); //detached entity passed to persist: game.data.analyzer.model.Effect
		itemService.save(wasser);
		wasser.addLocation(velia);
		wasser.addEffect(baseCooking);
		itemService.save(wasser);

		Item garmittel = new Item("garmittel", "to leaven");
		itemService.save(garmittel);
		garmittel.addEffect(baseCooking);
		itemService.save(garmittel);

		Item zucker = new Item("zucker", "sweet");
		itemService.save(zucker);
		zucker.addEffect(baseCooking);
		itemService.save(zucker);

		Item salz = new Item("salz","salty");
		itemService.save(salz);
		salz.addEffect(baseCooking);
		itemService.save(salz);

		Item wheatFlour = new Item("wheat flour", "flour from wheat");
		itemService.save(wheatFlour);
		wheatFlour.addEffect(baseCooking);
		itemService.save(wheatFlour);

		ItemGroup flour = new ItemGroup("flour", wheatFlour);
		itemGroupService.save(flour);
		flour.addEffect(baseCooking);
		itemGroupService.save(flour);

		Item grapes = new Item("grapes", "squish");
		itemService.save(grapes);
		grapes.addEffect(baseCooking);
		itemService.save(grapes);

		ItemGroup fruit = new ItemGroup("fruit", grapes);
		itemGroupService.save(fruit);
		fruit.addEffect(baseCooking);
		itemGroupService.save(fruit);

		Item essenceOfLiquor = new Item("Essence of Liquor", "base material for cooking");
		itemService.save(essenceOfLiquor);
		essenceOfLiquor.addEffect(baseCooking);
		itemService.save(essenceOfLiquor);

		//recipe inputs
		RecipeInput bierCereals = new RecipeInput("cereals for beer", cereals, 5);
		RecipeInput bierWasser = new RecipeInput("water for beer", wasser, 6);
		RecipeInput bierGarmittel = new RecipeInput("leavening agent for beer", garmittel, 2);
		RecipeInput bierZucker = new RecipeInput("sugar for beer", zucker, 1);
		recipeInputRepository.save(bierCereals);
		recipeInputRepository.save(bierWasser);
		recipeInputRepository.save(bierGarmittel);
		recipeInputRepository.save(bierZucker);
		RecipeInput essenceOfLiquorLeavening = new RecipeInput("Leavening Agent for Essence of Liquor", garmittel, 1);
		RecipeInput essenceOfLiquorFlour = new RecipeInput("flour for Essence of Liquor", flour, 1);
		RecipeInput essenceOfLiquorFruit = new RecipeInput("fruit for Essence of Liquor", fruit, 1);
		recipeInputRepository.save(essenceOfLiquorFlour);
		recipeInputRepository.save(essenceOfLiquorFruit);
		recipeInputRepository.save(essenceOfLiquorLeavening);

		//recipe outputs
		RecipeOutput bierOutput1 = new RecipeOutput("Beer", bier, 1.0);
		recipeOutputRepository.save(bierOutput1);
		RecipeOutput essenceOfLiquorOutput1 = new RecipeOutput("Essence of Liquor", essenceOfLiquor, 1.0);
		recipeOutputRepository.save(essenceOfLiquorOutput1);

		//recipes
		Recipe bierRecipe = new Recipe("Bier");
		recipeService.save(bierRecipe); //when you only save below: detached entity passed to persist: game.data.analyzer.model.RecipeInput
		Set<RecipeInput> bierInputs = new HashSet<>();
		bierInputs.add(bierCereals);
		bierInputs.add(bierWasser);
		bierInputs.add(bierGarmittel);
		bierInputs.add(bierZucker);
		bierRecipe.setInputs(bierInputs);
		Set<RecipeOutput> bierRecipeOutputs = new HashSet<>();
		bierRecipeOutputs.add(bierOutput1);
		bierRecipe.setOutputs(bierRecipeOutputs);
		recipeService.save(bierRecipe); //when you don't save again, the inputs and outputs remain detached and are not saved

		Recipe essenceOfLiquorRecipe = new Recipe("Essence of Liquor");
		recipeService.save(essenceOfLiquorRecipe);
		Set<RecipeInput> essenceOfLiquorInputs = new HashSet<>();
		essenceOfLiquorInputs.add(essenceOfLiquorFlour);
		essenceOfLiquorInputs.add(essenceOfLiquorFruit);
		essenceOfLiquorInputs.add(essenceOfLiquorLeavening);
		essenceOfLiquorRecipe.setInputs(essenceOfLiquorInputs);
		Set<RecipeOutput> essenceOfLiquorOutputs = new HashSet<>();
		essenceOfLiquorOutputs.add(essenceOfLiquorOutput1);
		essenceOfLiquorRecipe.setOutputs(essenceOfLiquorOutputs);
		recipeService.save(essenceOfLiquorRecipe);

		System.out.println("_____________________ database ______________________");
		System.out.println("Recipe Service, findAll _____");
		recipeService.findAll().forEach(x -> System.out.println(x));
		System.out.println("Item Service, findAll _____");
		itemService.findAll().forEach(x -> System.out.println(x));
		Item bierInDb = itemService.retrieveByName("bier").get(0);
		Item wheatInDb = itemService.retrieveByName("wheat").get(0);
		System.out.println("bier id/locations in db: "+bierInDb.getId()+"/"+bierInDb.getLocations());
		System.out.println("wheat id/locations in db: "+wheatInDb.getId()+"/"+wheatInDb.getLocations());
		System.out.println("Location Service, findAll _____");
		locationService.findAll().forEach(x -> System.out.println(x));
		System.out.println("item variables (not in database) _____");
		System.out.println(bier);
		System.out.println(wheat);
		System.out.println(zucker);
		System.out.println(wasser);
		System.out.println("effects _____");
		System.out.println(essenceOfLiquor.getEffects());

	}
}
