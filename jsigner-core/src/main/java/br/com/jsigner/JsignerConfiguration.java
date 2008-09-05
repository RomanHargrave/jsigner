package br.com.jsigner;

import java.util.ArrayList;
import java.util.List;

import br.com.jsigner.relationship.PersistenceMultiplicityFinder;
import br.com.jsigner.relationship.RelationshipMultiplicityFinder;

public abstract class JsignerConfiguration {
	
	private static List<Class<?>> diagramClasses;
	private static List<String> diagramClassesNames;

	public static RelationshipMultiplicityFinder getMultiplicityFinder() {
		return new PersistenceMultiplicityFinder();
	}
	
	public static void setDiagramClasses(List<Class<?>> diagramClasses) {
		JsignerConfiguration.diagramClasses = diagramClasses;
		
		diagramClassesNames = new ArrayList<String>(diagramClasses.size());
		for (Class<?> clazz : diagramClasses) {
			diagramClassesNames.add(clazz.getName());
		}
	}

	public static List<Class<?>> getDiagramClasses() {
		return diagramClasses;
	}
	
	public static List<String> getClassesNames() {
		return diagramClassesNames;
	}
}
