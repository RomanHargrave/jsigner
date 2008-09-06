package br.com.jsigner.diagram;

import java.lang.reflect.Field;

import br.com.jsigner.attribute.AttributeSpecification;

public class AttributesBuilder {

	public static String generateAttributesCode(Class<?> clazz,
			ClassDiagram classDiagram) {
		
		Field[] fields = clazz.getDeclaredFields();
		StringBuilder builder = new StringBuilder();
		builder.append("\t");
		for (Field field : fields) {
			AttributeSpecification specification = new AttributeSpecification(); 
			if (specification.isAttribute(clazz, classDiagram.getClassesNames(), field)) {
				builder.append(field.getName()).append(":");
				builder.append(field.getType().getSimpleName()).append(";");
			}
		}
		return builder.toString();
	}
}
