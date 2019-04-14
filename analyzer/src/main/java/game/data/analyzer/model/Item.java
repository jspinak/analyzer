package game.data.analyzer.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;

@Entity
@Data
@EqualsAndHashCode(exclude = "locations")
//@ToString(exclude = "locations") //must be excluded if fetch = Lazy
public class Item extends ItemObject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    String name;
    String description;
/*
    @ManyToOne
	@JoinColumn
	ItemGroup group;
*/

/*
	with Many-to-Many, we shouldn’t default to CascadeType.ALL because the CascadeType.REMOVE
	might end-up deleting more than we’re expecting
	we could use: cascade = {CascadeType.PERSIST, CascadeType.MERGE};
	FetchType.EAGER causes problems when there is no associated repository
*/

	// needs to be mapped by Item variable 'locations'
	@ManyToMany(
			fetch = FetchType.EAGER,
			cascade = {CascadeType.PERSIST, CascadeType.MERGE}
	)
	@JoinTable(name = "Item_Location",
				joinColumns = { @JoinColumn(name = "item_id") },
				inverseJoinColumns = { @JoinColumn(name = "location_id") }
	)
    Set<Location> locations = new HashSet<>();

/*
	@ManyToMany(
			fetch = FetchType.EAGER,
			mappedBy = "items") //for ManyToMany, sets the join table use
	Set<Location> locations = new HashSet<>();
*/

	@ManyToMany(
		fetch = FetchType.EAGER,
		cascade = {CascadeType.PERSIST, CascadeType.MERGE}
	)
	@JoinTable(name = "Item_Effect",
		joinColumns = { @JoinColumn(name = "item_id") },
		inverseJoinColumns = { @JoinColumn(name = "effect_id") }
	)
	Set<Effect> effects = new HashSet<>();

    public Item() {}
    public Item(String name) {
        this.name = name;
    }
	public Item(String name, String description) {
		this.name = name;
		this.description = description;
	}
    public Item(String name, String description, Set<Location> locations) {
        this.name = name;
        this.description = description;
        this.locations = locations;
    }
	public Item(String name, String description, Set<Location> locations, Set<Effect> effects) {
		this.name = name;
		this.description = description;
		this.locations = locations;
		this.effects = effects;
	}
    public void addLocation(Location location) {
        locations.add(location);
        location.returnItems().add(this);
    }

    //if this isn't here, despite having the addEffect method in the parent class, the effects won't be saved
    public void addEffect(Effect effect) {
		effects.add(effect);
		effect.returnItems().add(this);
	}

	public Item DTO() {
    	return new Item(name);
	}

	//this returns the locations LAZY (without items):  | Item -> Location -> Item |
	//with this funtion it is							| Item -> Location |
	public Set<Location> getLocations() {
		return locations.stream().map(location -> location.DTO()).collect(Collectors.toSet());
	}

	public Set<Effect> getEffects() {
    	return effects.stream().map(effect -> effect.DTO()).collect(Collectors.toSet());
	}

}
