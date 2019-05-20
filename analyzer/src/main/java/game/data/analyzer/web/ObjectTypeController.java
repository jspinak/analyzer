package game.data.analyzer.web;

import game.data.analyzer.model.ObjectType;
import game.data.analyzer.model.ObjectTypeDTO;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin //enables the API to be accessed by the React app.
public class ObjectTypeController {

	private ObjectTypeService objectTypeService;
	private List<ObjectType> objectTypes = new ArrayList<>();

	public ObjectTypeController(ObjectTypeService objectTypeService) {
		this.objectTypeService = objectTypeService;
	}

	@GetMapping(value = "/api/objectTypes")
	public @ResponseBody List<ObjectType> getObjectTypes() {
		return objectTypeService.findAll();
	}

	@GetMapping(value = "/api/itemTree")
	public @ResponseBody List<ObjectTypeDTO> getTree() {
		return objectTypeService.getTree("recipe");
	}
}
