package game.data.analyzer.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class AmountGameObjectTuple {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	double amount = 0.0;

	@OneToOne
	GameObject gameObject;

}
