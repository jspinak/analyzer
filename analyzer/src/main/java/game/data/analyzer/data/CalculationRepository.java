package game.data.analyzer.data;

import game.data.analyzer.model.Calculation;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
public interface CalculationRepository extends CrudRepository<Calculation, Long> {
	List<Calculation> findAll();
	Optional<Calculation> findById(long id);
	List<Calculation> findByNameContainingIgnoreCase(String name);
}
