package game.data.analyzer.data;

import game.data.analyzer.model.RecipeInput;

import javax.transaction.Transactional;

@Transactional
public interface RecipeInputRepository extends BaseRepository<RecipeInput> {

}
