package game.data.analyzer.data;

import game.data.analyzer.model.Location;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface LocationRepository extends BaseRepository<Location> {
	//for some reason, this query returns 2 of each location in the repository
	@Query("from Location l left join fetch l.items")
	List<Location> findAllEager();

	@Query("from Location l left join fetch l.items")
	List<Location> findByNameContainingIgnoreCaseEager(String name);
}
