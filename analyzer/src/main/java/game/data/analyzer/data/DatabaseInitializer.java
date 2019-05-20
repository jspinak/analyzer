package game.data.analyzer.data;

import game.data.analyzer.model.*;
import game.data.analyzer.web.*;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import java.util.List;

@Component
public class DatabaseInitializer implements ServletContextInitializer {

	private GameObjectService gameObjectService;
	private CalculationService calculationService;
	private ObjectTypeService objectTypeService;
	private AmountGameObjectTupleService amountGameObjectTupleService;
	private CalculationTypeService calculationTypeService;

	public DatabaseInitializer(CalculationService calculationService, GameObjectService gameObjectService,
							   ObjectTypeService objectTypeService,
							   AmountGameObjectTupleService amountGameObjectTupleService,
							   CalculationTypeService calculationTypeService) {
		this.calculationService = calculationService;
		this.gameObjectService = gameObjectService;
		this.objectTypeService = objectTypeService;
		this.amountGameObjectTupleService = amountGameObjectTupleService;
		this.calculationTypeService = calculationTypeService;

	}

	public void onStartup(ServletContext servletContext) {
		System.out.println("populate the database");

		//object types
		ObjectType location = new ObjectType();
		location = objectTypeService.save(location);
		location.setName("location");
		location = objectTypeService.save(location);

		ObjectType item = new ObjectType();
		item = objectTypeService.save(item);
		item.setName("item");
		item = objectTypeService.save(item);

		ObjectType recipe = new ObjectType();
		recipe = objectTypeService.save(recipe);
		recipe.setName("recipe");
		recipe = objectTypeService.save(recipe);

		//locations
		GameObject velia = new GameObject();
		velia = gameObjectService.save(velia);
		velia.addType(location);
		velia.setName("velia");
		velia = gameObjectService.save(velia);

		GameObject heidel = new GameObject();
		heidel = gameObjectService.save(heidel);
		heidel.addType(location);
		heidel.setName("heidel");
		heidel = gameObjectService.save(heidel);

		//items
		GameObject beer = new GameObject();
		beer = gameObjectService.save(beer);
		beer.addType(item);
		beer.setName("beer");
		beer.linkGameObject(velia);
		beer = gameObjectService.save(beer);

		GameObject corn = new GameObject();
		corn = gameObjectService.save(corn);
		corn.addType(item);
		corn.setName("corn");
		corn.linkGameObject(heidel);
		corn = gameObjectService.save(corn);

		GameObject water = new GameObject();
		water = gameObjectService.save(water);
		water.addType(item);
		water.setName("water");
		water = gameObjectService.save(water);

		//recipes
		GameObject beerRecipe = new GameObject();
		beerRecipe = gameObjectService.save(beerRecipe);
		beerRecipe.addType(recipe);
		beerRecipe.setName("beer recipe");
		 //beer calculation object
		Calculation beerCost = new Calculation();
		beerCost = calculationService.save(beerCost);
		beerCost.setOperation("add");
		  //beer calculation "beerCost" amount-gameobject tuples
		AmountGameObjectTuple waterForBeer = new AmountGameObjectTuple();
		waterForBeer = amountGameObjectTupleService.save(waterForBeer);
		waterForBeer.setGameObject(water);
		waterForBeer.setAmount(-6);
		waterForBeer = amountGameObjectTupleService.save(waterForBeer);
		AmountGameObjectTuple cornForBeer = new AmountGameObjectTuple();
		cornForBeer = amountGameObjectTupleService.save(cornForBeer);
		cornForBeer.setGameObject(corn);
		cornForBeer.setAmount(-5);
		cornForBeer = amountGameObjectTupleService.save(cornForBeer);
		  //beerCost calculation type: inputs
		CalculationType inputs = new CalculationType();
		inputs = calculationTypeService.save(inputs);
		inputs.setName("inputs");
		inputs = calculationTypeService.save(inputs);
		  //
		beerCost.addType(inputs);
		beerCost.addAmountGameObjectTuple(waterForBeer);
		beerCost.addAmountGameObjectTuple(cornForBeer);
		beerCost = calculationService.save(beerCost);
		 //
		beerRecipe.addCalculation(beerCost);
		beerRecipe = gameObjectService.save(beerRecipe);

		//for debugging
		List<GameObject> listFromDB = gameObjectService.findAll();
		List<Calculation> calcListFromDB = calculationService.findAll();
		System.out.println("database initialized");

	}
}
