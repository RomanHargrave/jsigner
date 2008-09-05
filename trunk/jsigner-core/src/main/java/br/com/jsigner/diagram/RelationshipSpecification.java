package br.com.jsigner.diagram;

import java.lang.reflect.Field;
import java.util.List;

public class RelationshipSpecification {

	public RelationshipSpecification() {
	}

	public boolean isRelationship(Class<?> clazz, List<String> classNames,
			Field field) {
		return (classNames.contains(field.getType().getName()) && !isCiclicReference(
				field, clazz))
				|| (isGeneric(classNames, field) && !isCiclicReference(field,
						clazz));
	}

	private boolean isCiclicReference(Field field, Class<?> clazz) {
		String fieldName;
		if (field.getGenericType().toString().contains(">")) {
			fieldName = field.getGenericType().toString().substring(
					field.getGenericType().toString().lastIndexOf(".") + 1,
					field.getGenericType().toString().length() - 1);
		} else {
			fieldName = field.getType().getSimpleName();
		}
		return fieldName.equals(clazz.getSimpleName());
	}

	public boolean isGeneric(List<String> classNames, Field field) {
		for (String className : classNames) {
			if (field.getGenericType().toString().contains(className + ">")) {
				return true;
			}
		}
		return classNames.contains(field.getGenericType());
	}

}
