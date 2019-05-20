package game.data.analyzer.data;

import game.data.analyzer.model.AmountGameObjectTuple;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
public interface AmountGameObjectTupleRepository extends CrudRepository<AmountGameObjectTuple, Long> {
	List<AmountGameObjectTuple> findAll();
	Optional<AmountGameObjectTuple> findById(long id);
	//List<AmountGameObjectTuple> findByNameContainingIgnoreCase(String name);
}
