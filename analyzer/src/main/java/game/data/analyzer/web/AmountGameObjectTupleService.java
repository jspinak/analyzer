package game.data.analyzer.web;

import game.data.analyzer.data.AmountGameObjectTupleRepository;
import game.data.analyzer.model.AmountGameObjectTuple;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class AmountGameObjectTupleService {

    //@Autowired
    private AmountGameObjectTupleRepository amountGameObjectTupleRepository;

    public AmountGameObjectTupleService(AmountGameObjectTupleRepository amountGameObjectTupleRepository) {
        this.amountGameObjectTupleRepository = amountGameObjectTupleRepository;
    }

    public AmountGameObjectTuple get(long id) {
        Optional<AmountGameObjectTuple> item = amountGameObjectTupleRepository.findById(id);
        //if (!item.isPresent()) throw new ItemDoesntExist();
        //else return item.get();
        return item.get();
    }

    public List<AmountGameObjectTuple> findAll() {
        return amountGameObjectTupleRepository.findAll();
    }

    public AmountGameObjectTuple save(AmountGameObjectTuple gameObject) {
        return amountGameObjectTupleRepository.save(gameObject);
    }

    /*public List<AmountGameObjectTuple> retrieveByName(String name) {
        if (name == null || name.equals("")) return findAll();
        return amountGameObjectTupleRepository.findByNameContainingIgnoreCase(name);
    }*/

}
