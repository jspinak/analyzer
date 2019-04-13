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
@ToString(exclude = "locations")
//@ToString(exclude = {"group","locations","effects","recipeInputs","recipeOutputs"})
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

	// with manytomany, we shouldn’t default to CascadeType.ALL because the CascadeType.REMOVE
	// might end-up deleting more than we’re expecting
	// we could use: cascade = {CascadeType.PERSIST, CascadeType.MERGE}; however, you can't cascade with entities
	// that are already saved in a repository

	//@JoinTable
	@ManyToMany(
			fetch = FetchType.EAGER,
			cascade = {CascadeType.PERSIST, CascadeType.MERGE}
	)
	@JoinTable(name = "Item_Location",
				joinColumns = { @JoinColumn(name = "item_id") },
				inverseJoinColumns = { @JoinColumn(name = "location_id") }
	)
    Set<Location> locations = new HashSet<>();

	//@ManyToMany(fetch = FetchType.EAGER, //EAGER ok here since few effects
	//		mappedBy = "effectItems") //for ManyToMany, sets the join table use
	Set<Effect> effects = new HashSet<>();

	//fetch = FetchType.EAGER, //EAGER causes problems when there is no associated repository
	//mappedBy = "effectItem") //in the relational database, creates an "item_id" column in the "effect" table
	//cascading is ok when the child entities are created along with the parent entity (one to one, one to many)
	//  when cascading, the child entities shouldn't be saved separately to the repository
	//@JoinColumn
	//@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "effectItem", orphanRemoval = true)

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
        if (locations == null) {
            locations = new HashSet<>();
        }
        locations.add(location);
        location.getItems().add(this);
    }

    //if this isn't here, despite having the addEffect method in the parent class, the effects won't be saved
    public void addEffect(Effect effect) {
		if (effects == null) {
			effects = new HashSet<>();
		}
		effects.add(effect);
		effect.getEffectItems().add(this);
	}

	public Item DTO() {
    	return new Item(name,description);
	}

	public Set<Location> locationsDTO() {
		Set<Location> locationsDTO = locations.stream().map(location -> location.DTO()).collect(Collectors.toSet());
		return locationsDTO;
	}


}
