package br.com.jsigner.diagram;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import br.com.jsigner.JsignerConfiguration;
import br.com.jsigner.annotations.Domain;

public class DiagramBuilder {

	private HashMap<String, ClassDiagram> diagrams = new HashMap<String, ClassDiagram>();

	public void build(List<Class<?>> diagramClasses) {
		for (Class<?> clazz : diagramClasses) {
			String[] diagramNames = clazz.getAnnotation(Domain.class).value();

			for (String diagramName : diagramNames) {
				ClassDiagram classDiagram = diagrams.get(diagramName);
				if (classDiagram == null) {
					classDiagram = new ClassDiagram(diagramName);
				}
				classDiagram.addClass(clazz);
				diagrams.put(classDiagram.getName(), classDiagram);
			}
		}
	}

	public Collection<ClassDiagram> getDiagrams() {
		return diagrams.values();
	}

}
