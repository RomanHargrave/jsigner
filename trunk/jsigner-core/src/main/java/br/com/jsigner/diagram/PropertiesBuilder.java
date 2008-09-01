package br.com.jsigner.diagram;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class PropertiesBuilder {

	public static String generatePropertiesCode(Class<?> clazz,
			List<Class<?>> diagramClasses) {
		List<String> classNames = new ArrayList<String>();
		for (Class<?> diagramClazz : diagramClasses) {
			classNames.add(diagramClazz.getName());
		}

		Field[] fields = clazz.getDeclaredFields();
		StringBuilder builder = new StringBuilder();
		builder.append("\t");
		for (Field field : fields) {

			FieldOrRelationshipSpecification specification = new FieldOrRelationshipSpecification();
			if (specification.isRelationship(clazz, classNames, field)
					|| field.getName().equals("serialVersionUID")
					|| field.getName().contains("$")) {
				continue;
			}

			builder.append(field.getName()).append(":");
			builder.append(field.getType().getSimpleName()).append(";");
		}
		return builder.toString();
	}
}
