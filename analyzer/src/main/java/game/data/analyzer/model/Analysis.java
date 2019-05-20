package game.data.analyzer.model;

import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class Analysis {

	String name;
	String description;
	String analysisType;
	Set<String> objectTypes;

	public Analysis() {
	}
}
