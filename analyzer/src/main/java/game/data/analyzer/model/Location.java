package game.data.analyzer.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Data
@EqualsAndHashCode(exclude = "items")
@ToString(exclude = "items")
public class Location extends GameObject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    String name;
    String description;

    @ManyToMany(
            //fetch = FetchType.EAGER, //EAGER necessary, otherwise fails to initialize
            //cascade = {CascadeType.PERSIST, CascadeType.DETACH},
            mappedBy = "locations" //for ManyToMany, sets the join table use
    )
    Set<Item> items = new HashSet<>();

    public Location() {}
    public Location(String name) {
        this.name = name;
    }

    public Location DTO() {
        return new Location(name);
    }

    public Set<Item> getItems() {
        Set<Item> itemsDTO = items.stream().map(item -> item.DTO()).collect(Collectors.toSet());
        return itemsDTO;
    }
}
