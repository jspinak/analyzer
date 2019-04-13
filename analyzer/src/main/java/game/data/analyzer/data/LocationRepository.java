package game.data.analyzer.data;

import game.data.analyzer.model.Location;

import javax.transaction.Transactional;

@Transactional
public interface LocationRepository extends BaseRepository<Location> {

}
