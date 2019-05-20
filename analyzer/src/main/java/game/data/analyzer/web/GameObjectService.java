package game.data.analyzer.web;

import game.data.analyzer.data.GameObjectRepository;
import game.data.analyzer.model.GameObject;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class GameObjectService {

    //@Autowired
    private GameObjectRepository gameObjectRepository;

    public GameObjectService(GameObjectRepository gameObjectRepository) {
        this.gameObjectRepository = gameObjectRepository;
    }

    public GameObject get(long id) {
        Optional<GameObject> item = gameObjectRepository.findById(id);
        //if (!item.isPresent()) throw new ItemDoesntExist();
        //else return item.get();
        return item.get();
    }

    public List<GameObject> findAll() {
        return gameObjectRepository.findAll();
    }

    public GameObject save(GameObject gameObject) {
        return gameObjectRepository.save(gameObject);
    }

    public List<GameObject> retrieveByName(String name) {
        if (name == null || name.equals("")) return findAll();
        return gameObjectRepository.findByNameContainingIgnoreCase(name);
    }

}
