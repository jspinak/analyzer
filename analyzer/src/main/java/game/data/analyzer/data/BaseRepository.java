package game.data.analyzer.data;

import game.data.analyzer.model.GameObject;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface BaseRepository <T extends GameObject> extends CrudRepository<T, Long> {
	List<T> findAll();
	Optional<T> findById(long id);
	List<T> findByNameContainingIgnoreCase(String name);
}
