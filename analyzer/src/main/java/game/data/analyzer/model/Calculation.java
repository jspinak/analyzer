package game.data.analyzer.model;

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
@EqualsAndHashCode(exclude = {"types"})
@ToString(exclude = {"types"}) //must be excluded if fetch = Lazy
public class Calculation {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	String name;
	String description;

	String operation; //"add","choose", etc.
	double addTo = 0.0;

	@ManyToMany(
			fetch = FetchType.EAGER,
			cascade = {CascadeType.PERSIST, CascadeType.MERGE}
	)
	@JoinTable(name = "Calculation_CalculationType",
			joinColumns = { @JoinColumn(name = "calculation_id") },
			inverseJoinColumns = { @JoinColumn(name = "calculationType_id") }
	)
	Set<CalculationType> types = new HashSet<>(); //can be multiple types, including subtypes
	//String type; //give a list of types, and a text box to add a new type. i.e. Recipe

	@OneToMany(
			cascade = CascadeType.ALL,
			fetch = FetchType.EAGER
	)
	Set<AmountGameObjectTuple> amountGameObjectTuples = new HashSet<>();

	public Calculation() {}
	public Calculation(String name) {
		this.name = name;
	}

	public void addAmountGameObjectTuple(AmountGameObjectTuple amountGameObjectTuple) {
		amountGameObjectTuples.add(amountGameObjectTuple);
	}

	public void addType(CalculationType calculationType) {
		types.add(calculationType);
		calculationType.addCalculation(this);
	}

	public Calculation DTO() {
		return new Calculation(name);
	}

	public Set<ObjectType> retrieveChildGameObjectTypes() {
		Set<ObjectType> objectTypeSet = new HashSet<>();
		amountGameObjectTuples
				.forEach(tuple -> tuple.getGameObject().getTypes()
				.forEach(type -> objectTypeSet.add(type)));
		return objectTypeSet;
	}
}
