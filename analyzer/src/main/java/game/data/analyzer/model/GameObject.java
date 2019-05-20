package game.data.analyzer.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Data
@EqualsAndHashCode(exclude = {"gameObjects","types"})
@ToString(exclude = {"gameObjects","types"}) //must be excluded if fetch = Lazy
public class GameObject {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	String name;
	String description;
	//Map<String,Double> values;

	@ManyToMany(
			fetch = FetchType.EAGER,
			cascade = {CascadeType.PERSIST, CascadeType.MERGE}
	)
	@JoinTable(name = "GameObject_ObjectType",
			joinColumns = { @JoinColumn(name = "gameObject_id") },
			inverseJoinColumns = { @JoinColumn(name = "objectType_id") }
	)
	Set<ObjectType> types = new HashSet<>(); //can be multiple types, including subtypes
	//String type; //give a list of types, and a text box to add a new type. i.e. Recipe

	double value;

	@ManyToMany(
			fetch = FetchType.EAGER,
			cascade = {CascadeType.PERSIST, CascadeType.MERGE}
	)
	Set<GameObject> gameObjects = new HashSet<>(); //these are objects that are linked to the current object
	//i.e. an Item type will have Location types linked to it here

	@OneToMany(
			cascade = CascadeType.ALL,
			fetch = FetchType.EAGER
	)
	Set<Calculation> calculations = new HashSet<>();

	public GameObject() {}
	public GameObject(String name) {
		this.name = name;
	}

	//public Double getValue(String key) {
	//	return values.get(key);
	//}

	public GameObject DTO() {
		return new GameObject(name);
	}

	//this function returns the items lazy:  | GameObject -> gameObjects{ GameObject } |
	//without this function it would be      | GameObject -> gameObjects{ GameObject -> gameObjects... } |
	public Set<GameObject> getGameObjects() {
		return gameObjects.stream().map(gameObject -> gameObject.DTO()).collect(Collectors.toSet());
	}

	public void linkGameObject(GameObject gameObject) {
		gameObjects.add(gameObject);
		gameObject.linkGameObjectInverse(this);
	}

	private void linkGameObjectInverse(GameObject gameObject) {
		gameObjects.add(gameObject);
	}

	public void addCalculation(Calculation calculation) {
		calculations.add(calculation);
	}

	public void addType(ObjectType objectType) {
		types.add(objectType);
		objectType.addGameObject(this);
	}

	private Set<ObjectType> getTypesDTO() {
		Set<ObjectType> typesDTO = new HashSet<>();
		types.forEach(type -> typesDTO.add(new ObjectType(type.getName())));
		System.out.println("types: "+types);
		System.out.println("typesDTO: "+typesDTO);
		return typesDTO;
	}

	public Set<ObjectType> childGameObjectTypeDTOs() {
		List<Set<ObjectType>> objectTypeListOfLists = new ArrayList<>();
		objectTypeListOfLists = gameObjects.stream().map(gameObject -> gameObject.getTypesDTO()).collect(Collectors.toList());
		Set<ObjectType> objectTypeSet = new HashSet<>();
		objectTypeListOfLists.forEach(typeList -> typeList.forEach(type -> objectTypeSet.add(type)));
		return objectTypeSet;
	}

	public void replaceObjectTypeWithDTO(Set<ObjectType> objectTypeSet, ObjectType replaceObjectType) {
		if (objectTypeSet.contains(replaceObjectType)) {
			objectTypeSet.remove(replaceObjectType);
			objectTypeSet.add(replaceObjectType.DTO());
		}
	}

	//combine these two methods using the generic form of Set<>
	public Set<ObjectType> retrieveChildGameObjectTypes() {
		Set<ObjectType> objectTypeSet = new HashSet<>();
		gameObjects.forEach(gameObject -> objectTypeSet.addAll(gameObject.getTypes()));
		return objectTypeSet;
	}

	public Set<CalculationType> retrieveChildCalculationTypes() {
		List<Set<CalculationType>> calculationsTypeListOfLists = new ArrayList<>();
		calculationsTypeListOfLists = calculations.stream().map(calculation -> calculation.getTypes()).collect(Collectors.toList());
		Set<CalculationType> calculationsTypeSet = new HashSet<>();
		calculationsTypeListOfLists.forEach(typeList -> typeList.forEach(type -> calculationsTypeSet.add(type)));
		return calculationsTypeSet;
	}

}
