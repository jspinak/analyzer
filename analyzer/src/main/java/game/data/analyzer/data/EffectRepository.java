package game.data.analyzer.data;

import game.data.analyzer.model.Effect;

import javax.transaction.Transactional;

@Transactional
public interface EffectRepository extends BaseRepository<Effect> {

}
