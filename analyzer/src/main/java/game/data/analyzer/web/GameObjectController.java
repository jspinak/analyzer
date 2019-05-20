package game.data.analyzer.web;

import game.data.analyzer.model.GameObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin //enables the API to be accessed by the React app.
public class GameObjectController {

	private GameObjectService gameObjectService;

	public GameObjectController(GameObjectService gameObjectService) {
		this.gameObjectService = gameObjectService;
	}

	@GetMapping(value = "/api/gameObjects")
	public @ResponseBody List<GameObject> getGameObjects() {
		return gameObjectService.findAll();
	}
}
