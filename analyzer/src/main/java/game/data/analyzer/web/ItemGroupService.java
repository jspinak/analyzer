package game.data.analyzer.web;

import game.data.analyzer.data.ItemGroupRepository;
import game.data.analyzer.model.ItemGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ItemGroupService {

    @Autowired
    private ItemGroupRepository itemGroupRepository;

    public ItemGroupService(ItemGroupRepository itemGroupRepository) {
        this.itemGroupRepository = itemGroupRepository;
    }

    public ItemGroup get(long id) {
        Optional<ItemGroup> item = itemGroupRepository.findById(id);
        if (!item.isPresent()) throw new ItemDoesntExist();
        else return item.get();
    }

    public List<ItemGroup> findAll() {
        return itemGroupRepository.findAll();
    }

    public void save(ItemGroup item) {
        itemGroupRepository.save(item);
    }

    public List<ItemGroup> retrieveByName(String name) {
        if (name == null || name.equals("")) return findAll();
        return itemGroupRepository.findByNameContainingIgnoreCase(name);
    }
}
