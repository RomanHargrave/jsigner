package br.com.jsigner.diagram;

import java.util.ArrayList;
import java.util.List;

public class ClassDiagram {

	private String name;
	private List<Class<?>> classes = new ArrayList<Class<?>>();
	private List<String> classesNames = new ArrayList<String>();

	public ClassDiagram(String diagramName) {
		this.name = diagramName;
	}

	public void addClass(Class<?> clazz) {
		this.classes.add(clazz);
		this.classesNames.add(clazz.getName());
	}

	public String generateDiagramCode() {
		StringBuilder builder = new StringBuilder();

		builder.append("class diagram ").append(name).append("{\n");
		builder.append(ClassesBuilder.generateClassesCode(this));
		builder.append("}");

		
		return builder.toString();
	}

	public String getName() {
		return name;
	}

	public List<Class<?>> getClasses() {
		return classes;
	}

	public List<String> getClassesNames() {
		return classesNames;
	}

}
