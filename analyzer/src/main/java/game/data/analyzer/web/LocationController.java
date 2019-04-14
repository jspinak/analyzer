package game.data.analyzer.web;

import game.data.analyzer.model.Location;
import game.data.analyzer.model.Recipe;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin //enables the API to be accessed by the React app.
public class LocationController {

	private final LocationService locationService;
	private List<Location> locations = new ArrayList<>();

	public LocationController(LocationService locationService) {
		this.locationService = locationService;
	}

	@GetMapping(value = "/api/locations")
	public @ResponseBody List<Location> getLocations() {
		//recipeService.prepareJson();
		return locationService.findAll();
	}

	@GetMapping(value = "/api/locations2")
	public @ResponseBody List<Location> getLocationsWithItems() {
		//recipeService.prepareJson();
		return locationService.findAllEager();
	}
}
