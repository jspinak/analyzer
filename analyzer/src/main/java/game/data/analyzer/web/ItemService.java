package game.data.analyzer.web;

import game.data.analyzer.data.ItemRepository;
import game.data.analyzer.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Component
public class ItemService {

    //@Autowired
    private ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Item get(long id) {
        Optional<Item> item = itemRepository.findById(id);
        if (!item.isPresent()) throw new ItemDoesntExist();
        else return item.get();
    }

    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    public Item save(Item item) {
        return itemRepository.save(item);
    }

    public List<Item> retrieveByName(String name) {
        if (name == null || name.equals("")) return findAll();
        return itemRepository.findByNameContainingIgnoreCase(name);
    }
}
