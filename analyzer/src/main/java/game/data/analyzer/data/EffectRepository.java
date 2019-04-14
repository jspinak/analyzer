package game.data.analyzer.data;

import game.data.analyzer.model.Effect;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface EffectRepository extends BaseRepository<Effect> {
	@Query("from Effect e left join fetch e.items")
	List<Effect> findAllEager();

	@Query("from Effect e left join fetch e.items")
	List<Effect> findByNameContainingIgnoreCaseEager(String name);
}
