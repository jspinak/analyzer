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

@Entity
@Data
@EqualsAndHashCode(exclude = {"effectItems","effectItemGroups"})
@ToString(exclude = {"effectItems","effectItemGroups"})
public class Effect extends GameObject {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	String use; //for example, Fighting
	Double effect; //for example, +5%
	String effectType; //for example, HP regeneration

	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(name = "Effect_Item",
			joinColumns = {
					@JoinColumn(
							name = "effect_id",
							referencedColumnName = "id"
					)
			},
			inverseJoinColumns = {
					@JoinColumn(
							name = "item_id",
							referencedColumnName = "id"
					)
			}
	)
	Set<ItemObject> effectItems = new HashSet<>();
/*
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(name = "Effect_ItemGroup",
			joinColumns = {
					@JoinColumn(
							name = "effect_id",
							referencedColumnName = "id"
					)
			},
			inverseJoinColumns = {
					@JoinColumn(
							name = "itemgroup_id",
							referencedColumnName = "id"
					)
			}
	)
	Set<ItemObject> effectItemGroups = new HashSet<>();
*/
	public Effect() {}
	public Effect(String use, Double effect, String effectType) {
		this.use = use;
		this.effect = effect;
		this.effectType = effectType;
	}


}
