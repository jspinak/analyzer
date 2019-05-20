package game.data.analyzer.web;

import game.data.analyzer.model.ObjectType;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TreeService {

    private GameObjectService gameObjectService;
    private ObjectTypeService objectTypeService;

    public TreeService(GameObjectService gameObjectService, ObjectTypeService objectTypeService) {
        this.gameObjectService = gameObjectService;
        this.objectTypeService = objectTypeService;
    }


}
