package game.data.analyzer.data;

import game.data.analyzer.model.RecipeOutput;

import javax.transaction.Transactional;

@Transactional
public interface RecipeOutputRepository extends BaseRepository<RecipeOutput> {

}
