package game.data.analyzer.data;

import game.data.analyzer.model.Item;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface ItemRepository extends BaseRepository<Item> {
	@Query("from Item i left join fetch i.locations")
	List<Item> findAllEager();

	@Query("from Item i left join fetch i.locations")
	List<Item> findByNameContainingIgnoreCaseEager(String name);

}
