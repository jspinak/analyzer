package game.data.analyzer.model;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class ObjectTypeDTO {

	String title;
	Set<ObjectTypeDTO> objectTypeDTOs = new HashSet<>();
	Set<CalculationTypeDTO> calculationTypeDTOs = new HashSet<>();
	Set<Object> children = new HashSet<>(); //for tree view

	public ObjectTypeDTO() {}
	public ObjectTypeDTO(String title) {this.title = title;}

	public ObjectTypeDTO DTO() {
		return new ObjectTypeDTO(title);
	}

	public ObjectTypeDTO nestedDTO(ObjectType objectType, Set<String> objectTypesSeen, Set<String> calculationTypesSeen) {
		if (title == null) title = objectType.getName(); //in case this object is new (a temp DTO)
		if (!objectTypesSeen.contains(title)) {
			objectTypesSeen.add(title);

			System.out.println("____ this type not seen yet ____ "+ this.getTitle());
			System.out.println("  objectTypesSeen= "+objectTypesSeen);
			System.out.println("  objectTypeDTO= "+this);

			objectType.getChildGameObjectTypes().forEach(
					childObjectType -> objectTypeDTOs.add(childObjectType.nestedDTO(objectTypesSeen,calculationTypesSeen))
			);
			objectType.getChildCalculationTypes().forEach(
					childCalculationType -> calculationTypeDTOs.add(childCalculationType.nestedDTO(objectTypesSeen,calculationTypesSeen))
			);
		}
		System.out.println("/////  end of method /////");
		System.out.println("thisObject= "+ this.getTitle());
		System.out.println("  objectTypesSeen= "+objectTypesSeen);
		System.out.println("  objectTypeDTO= "+this);
		objectTypeDTOs.forEach(objectTypeDTO -> children.add(objectTypeDTO));
		calculationTypeDTOs.forEach(calculationTypeDTO -> children.add(calculationTypeDTO));
		return this;
	}

}
