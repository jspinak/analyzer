package game.data.analyzer.data;

import game.data.analyzer.model.Recipe;

import javax.transaction.Transactional;

@Transactional
public interface RecipeRepository extends BaseRepository<Recipe> {

}
