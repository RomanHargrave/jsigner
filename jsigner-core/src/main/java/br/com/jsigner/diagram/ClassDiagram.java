package br.com.jsigner.diagram;

import java.util.ArrayList;
import java.util.List;

public class ClassDiagram {

	private String name;
	private List<Class<?>> classes = new ArrayList<Class<?>>();

	public ClassDiagram(String diagramName) {
		this.name = diagramName;
	}

	public void addClass(Class<?> clazz) {
		this.classes.add(clazz);
	}

	public String generateDiagramCode() {
		StringBuilder builder = new StringBuilder();

		builder.append("class diagram ").append(name).append("{\n");
		builder.append(ClassesBuilder.generateClassesCode(classes));
		builder.append("}");

		
		return builder.toString();
	}

	public String getName() {
		return name;
	}

}
