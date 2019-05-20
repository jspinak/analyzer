package game.data.analyzer.web;

import game.data.analyzer.data.CalculationRepository;
import game.data.analyzer.model.Calculation;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CalculationService {

    //@Autowired
    private CalculationRepository calculationRepository;

    public CalculationService(CalculationRepository calculationRepository) {
        this.calculationRepository = calculationRepository;
    }

    public Calculation get(long id) {
        Optional<Calculation> item = calculationRepository.findById(id);
        //if (!item.isPresent()) throw new ItemDoesntExist();
        //else return item.get();
        return item.get();
    }

    public List<Calculation> findAll() {
        return calculationRepository.findAll();
    }

    public Calculation save(Calculation calculation) {
        return calculationRepository.save(calculation);
    }

    public List<Calculation> retrieveByName(String name) {
        if (name == null || name.equals("")) return findAll();
        return calculationRepository.findByNameContainingIgnoreCase(name);
    }


}
