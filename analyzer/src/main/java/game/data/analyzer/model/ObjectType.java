package game.data.analyzer.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Data

@EqualsAndHashCode(exclude = {"gameObjects"})
@ToString(exclude = {"gameObjects"}) //must be excluded if fetch = Lazy
public class ObjectType {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	String name;

	@ManyToMany(
			fetch = FetchType.EAGER, //when LAZY, no items will be returned.
			cascade = {CascadeType.PERSIST, CascadeType.DETACH},
			mappedBy = "types" //for ManyToMany, sets the join table use
	)
	Set<GameObject> gameObjects = new HashSet<>();

	public ObjectType() {}
	public ObjectType(String name) {this.name = name;}

	//this function returns the items lazy:  | GameObject -> gameObjects{ GameObject } |
	//without this function it would be      | GameObject -> gameObjects{ GameObject -> gameObjects... } |
	public Set<GameObject> getGameObjects() {
		return gameObjects.stream().map(gameObject -> gameObject.DTO()).collect(Collectors.toSet());
	}

	public void addGameObject(GameObject gameObject) {
		gameObjects.add(gameObject);
	}
/*
	public void addObjectType(ObjectType objectType) {
		objectTypes.add(objectType);
		objectType.addObjectTypeInverse(this);
	}

	private void addObjectTypeInverse(ObjectType objectType) {
		objectTypes.add(objectType);
	}
*/
	public ObjectType DTO() {
		return new ObjectType(name);
	}

	//this could also use an ObjectTypeDTO variable stored locally (for reuse).
	public ObjectTypeDTO nestedDTO(Set<String> objectTypesSeen, Set<String> calculationTypesSeen) {
		return new ObjectTypeDTO().nestedDTO(this,objectTypesSeen,calculationTypesSeen);
	}

	public Set<ObjectType> getChildGameObjectTypes() {
		Set<ObjectType> childObjectTypes = new HashSet<>();
		gameObjects.forEach(gameObject -> childObjectTypes.addAll(gameObject.retrieveChildGameObjectTypes()));
		return childObjectTypes;
	}

	public Set<CalculationType> getChildCalculationTypes() {
		Set<CalculationType> childCalculationTypes = new HashSet<>();
		gameObjects.forEach(gameObject -> childCalculationTypes.addAll(gameObject.retrieveChildCalculationTypes()));
		return childCalculationTypes;
	}
}
