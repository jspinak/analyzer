package game.data.analyzer.data;

import game.data.analyzer.model.ObjectType;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
public interface ObjectTypeRepository extends CrudRepository<ObjectType, Long> {
	List<ObjectType> findAll();
	Optional<ObjectType> findById(long id);
	List<ObjectType> findByNameContainingIgnoreCase(String name);
}
