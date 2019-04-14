package game.data.analyzer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Data
@EqualsAndHashCode(exclude = {"items","itemGroups"}) //otherwise infinite loop
@ToString(exclude = {"items","itemGroups"}) //otherwise infinite loop
public class Effect extends GameObject {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	String use; //for example, Fighting
	Double effect; //for example, +5%
	String effectType; //for example, HP regeneration

	@ManyToMany(
			fetch = FetchType.EAGER, //when LAZY, no items will be returned.
			cascade = {CascadeType.PERSIST, CascadeType.DETACH},
			mappedBy = "effects" //for ManyToMany, sets the join table use
	)
	Set<Item> items = new HashSet<>();

	@ManyToMany(
			fetch = FetchType.EAGER, //when LAZY, no items will be returned.
			cascade = {CascadeType.PERSIST, CascadeType.DETACH},
			mappedBy = "effects" //for ManyToMany, sets the join table use
	)
	Set<ItemGroup> itemGroups = new HashSet<>();

	public Effect() {}
	public Effect(String use, Double effect, String effectType) {
		this.use = use;
		this.effect = effect;
		this.effectType = effectType;
	}

	public Effect DTO() {
		return new Effect(use, effect, effectType);
	}

	//returns infinite loop in JSON without this function
	public Set<Item> getItems() {
		return items.stream().map(item -> item.DTO()).collect(Collectors.toSet());
	}

	public Set<Item> returnItems() {
		return items;
	}

	//returns infinite loop in JSON without this function
	public Set<ItemGroup> getItemGroups() {
		return itemGroups.stream().map(itemGroup -> itemGroup.DTO()).collect(Collectors.toSet());
	}

	public Set<ItemGroup> returnItemGroups() {
		return itemGroups;
	}

}
