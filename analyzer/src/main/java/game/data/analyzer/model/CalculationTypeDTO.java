package game.data.analyzer.model;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class CalculationTypeDTO {

	String title;
	Set<ObjectTypeDTO> objectTypeDTOs = new HashSet<>();
	Set<Object> children = new HashSet<>(); //for tree view

	public CalculationTypeDTO() { }
	public CalculationTypeDTO(String name) {
		this.title = name;
	}

	public CalculationTypeDTO nestedDTO(CalculationType calculationType, Set<String> objectTypesSeen, Set<String> calculationTypesSeen) {
		if (title == null) title = calculationType.getName(); //in case this object is new (a temp DTO)
		if (!calculationTypesSeen.contains(title)) {
			calculationTypesSeen.add(title);

			System.out.println("____ this type not seen yet ____ "+ this.getTitle());
			System.out.println("  calculationTypesSeen= "+calculationTypesSeen);
			System.out.println("  CalculationTypeDTO= "+this);

			calculationType.getChildGameObjectTypes().forEach(
					childObjectType -> objectTypeDTOs.add(childObjectType.nestedDTO(objectTypesSeen,calculationTypesSeen))
			);
		}
		System.out.println("/////  end of method /////");
		System.out.println("thisObject= "+ this.getTitle());
		System.out.println("  objectTypesSeen= "+objectTypesSeen);
		System.out.println("  objectTypeDTO= "+this);
		objectTypeDTOs.forEach(objectTypeDTO -> children.add(objectTypeDTO));
		return this;
	}
}
