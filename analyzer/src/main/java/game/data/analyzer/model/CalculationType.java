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
@EqualsAndHashCode(exclude = {"calculations"})
@ToString(exclude = {"calculations"}) //must be excluded if fetch = Lazy
public class CalculationType {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	String name;

	@ManyToMany(
			fetch = FetchType.EAGER, //when LAZY, no items will be returned.
			cascade = {CascadeType.PERSIST, CascadeType.DETACH},
			mappedBy = "types" //for ManyToMany, sets the join table use
	)
	Set<Calculation> calculations = new HashSet<>();

	public CalculationType() {
	}

	//this function returns the items lazy:  | Calculation -> calculations{ Calculation } |
	//without this function it would be      | Calculation -> calculations{ Calculation -> calculations... } |
	public Set<Calculation> getCalculations() {
		return calculations.stream().map(calc -> calc.DTO()).collect(Collectors.toSet());
	}

	public void addCalculation(Calculation calculation) {
		calculations.add(calculation);
	}

	public Set<ObjectType> getChildGameObjectTypes() {
		Set<ObjectType> childObjectTypes = new HashSet<>();
		calculations.forEach(calculation -> childObjectTypes.addAll(calculation.retrieveChildGameObjectTypes()));
		return childObjectTypes;
	}

	//this could also use an ObjectTypeDTO variable stored locally (for reuse).
	public CalculationTypeDTO nestedDTO(Set<String> objectTypesSeen, Set<String> calculationTypesSeen) {
		return new CalculationTypeDTO().nestedDTO(this,objectTypesSeen,calculationTypesSeen);
	}

}
