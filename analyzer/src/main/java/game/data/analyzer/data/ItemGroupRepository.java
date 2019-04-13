package game.data.analyzer.data;

import game.data.analyzer.model.ItemGroup;

import javax.transaction.Transactional;

@Transactional
public interface ItemGroupRepository extends BaseRepository<ItemGroup> {

}
