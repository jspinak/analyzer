package game.data.analyzer.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Data
@EqualsAndHashCode(exclude = "items")
//@ToString(exclude = "items")
public class Location extends GameObject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    String name;
    String description;

    @ManyToMany(
            fetch = FetchType.EAGER, //when LAZY, no items will be returned.
            cascade = {CascadeType.PERSIST, CascadeType.DETACH},
            mappedBy = "locations" //for ManyToMany, sets the join table use
    )
    Set<Item> items = new HashSet<>();

/*
    @ManyToMany(
            fetch = FetchType.EAGER,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE}
    )
    @JoinTable(name = "Location_Item",
            joinColumns = { @JoinColumn(name = "location_id") },
            inverseJoinColumns = { @JoinColumn(name = "item_id") }
    )
    Set<Item> items = new HashSet<>();
*/

    public Location() {}
    public Location(String name) {
        this.name = name;
    }

    public Location DTO() {
        return new Location(name);
    }

    //this function returns the items lazy, without locations:  | Locations -> Items |
    //without this function it would be                         | Locations -> Items -> Locations |
    public Set<Item> getItems() {
        return items.stream().map(item -> item.DTO()).collect(Collectors.toSet());
    }

    public Set<Item> returnItems() {
        return items;
    }
}
