package game.data.analyzer.web;

import game.data.analyzer.data.CalculationTypeRepository;
import game.data.analyzer.model.CalculationType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CalculationTypeService {

    //@Autowired
    private CalculationTypeRepository calculationTypeRepository;

    public CalculationTypeService(CalculationTypeRepository calculationTypeRepository) {
        this.calculationTypeRepository = calculationTypeRepository;
    }

    public CalculationType get(long id) {
        Optional<CalculationType> item = calculationTypeRepository.findById(id);
        //if (!item.isPresent()) throw new ItemDoesntExist();
        //else return item.get();
        return item.get();
    }

    public List<CalculationType> findAll() {
        return calculationTypeRepository.findAll();
    }

    public CalculationType save(CalculationType calculationType) {
        return calculationTypeRepository.save(calculationType);
    }

    public List<CalculationType> retrieveByName(String name) {
        if (name == null || name.equals("")) return findAll();
        return calculationTypeRepository.findByNameContainingIgnoreCase(name);
    }

}
