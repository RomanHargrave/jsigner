package br.com.jsigner.diagram;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class ClassesBuilder {

	protected static String generateClassesCode(List<Class<?>> diagramClasses) {
		StringBuilder builder = new StringBuilder();
		builder = new StringBuilder();
		
		List<String> classNames = new ArrayList<String>();
		for (Class<?> diagramClass : diagramClasses) {
			classNames.add(diagramClass.getName());
		}

		for (Class<?> clazz : diagramClasses) {

			if (Modifier.isAbstract(clazz.getModifiers())) {
				builder.append("abstract ");
			}
			builder.append("class ").append(clazz.getSimpleName()).append(" ");
			
			if (classNames.contains(clazz.getSuperclass().getName())) {
				builder.append("extends ").append(clazz.getSuperclass().getSimpleName());
			}
			
			builder.append("{\n");
			
			builder.append(PropertiesBuilder.generatePropertiesCode(clazz, diagramClasses));
			builder.append(RelationshipsBuilder.generateRelationShipsCode(clazz, diagramClasses));
			builder.append(MethodsBuilder.generateMethodsCode(clazz));

			builder.append("\n}\n");
		}

		return builder.toString();
	}
}
