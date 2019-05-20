package game.data.analyzer.data;

import game.data.analyzer.model.CalculationType;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
public interface CalculationTypeRepository extends CrudRepository<CalculationType, Long> {
	List<CalculationType> findAll();
	Optional<CalculationType> findById(long id);
	List<CalculationType> findByNameContainingIgnoreCase(String name);
}
