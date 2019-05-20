package game.data.analyzer.data;

import game.data.analyzer.model.GameObject;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
public interface GameObjectRepository extends CrudRepository<GameObject, Long> {
	List<GameObject> findAll();
	Optional<GameObject> findById(long id);
	List<GameObject> findByNameContainingIgnoreCase(String name);
}
