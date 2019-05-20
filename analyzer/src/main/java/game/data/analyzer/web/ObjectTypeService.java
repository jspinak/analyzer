package game.data.analyzer.web;

import com.monitorjbl.json.JsonViewModule;
import game.data.analyzer.data.ObjectTypeRepository;
import game.data.analyzer.model.ObjectType;
import game.data.analyzer.model.ObjectTypeDTO;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;

@Component
public class ObjectTypeService {

    //initialize jackson
    ObjectMapper mapper = new ObjectMapper().registerModule(new JsonViewModule());

    private ObjectTypeRepository objectTypeRepository;

    public ObjectTypeService(ObjectTypeRepository objectTypeRepository) {
        this.objectTypeRepository = objectTypeRepository;
    }

    public ObjectType get(long id) {
        Optional<ObjectType> item = objectTypeRepository.findById(id);
        //if (!item.isPresent()) throw new ItemDoesntExist();
        //else return item.get();
        return item.get();
    }

    public List<ObjectType> findAll() {
        return objectTypeRepository.findAll();
    }

    public ObjectType save(ObjectType objectType) {
        return objectTypeRepository.save(objectType);
    }

    public List<ObjectType> retrieveByName(String name) {
        if (name == null || name.equals("")) return findAll();
        else return objectTypeRepository.findByNameContainingIgnoreCase(name);
    }

    public List<ObjectTypeDTO> getTree(String name) {
        System.out.println("start getTree method in ObjectTypeService");
        List<ObjectType> types = retrieveByName(name);
        List<ObjectTypeDTO> typeDTOs = new ArrayList<>();
        types.forEach(type -> typeDTOs.add(type.nestedDTO(new HashSet<String>(),new HashSet<String>())));
        System.out.println("typeDTOs= "+typeDTOs);
        /*
        String typeTree = "";
        try {
            typeTree = mapper.writeValueAsString(JsonView.with(typeDTOs).onClass(ObjectType.class, match()
                    .exclude("gameObjects")));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return typeTree;
        */
        return typeDTOs;
    }

}
