package br.com.jsigner.diagram;

import java.util.HashMap;
import java.util.List;

import br.com.agile.jsigner.Domain;

public class DiagramBuilder {

	private HashMap<String, ClassDiagram> diagrams = new HashMap<String, ClassDiagram>();

	public void build(List<Class<?>> classes) {
		for (Class<?> clazz : classes) {
			String diagramName = clazz.getAnnotation(Domain.class).value();
			ClassDiagram classDiagram = diagrams.get(diagramName);
			if (classDiagram == null) {
				classDiagram = new ClassDiagram(diagramName);
			}
			classDiagram.addClass(clazz);
			diagrams.put(classDiagram.getName(), classDiagram);
		}
	}
	
	public ClassDiagram getClassDiagram(String name) { 
		return diagrams.get(name);
	}
	
	
	
}
