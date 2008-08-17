package br.com.jsigner.diagram;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

public class RelationshipsBuilder {

	public static String generateRelationShipsCode(Class<?> clazz,
			List<Class<?>> diagramClasses) {

		List<String> classNames = new ArrayList<String>();
		for (Class<?> diagramClazz : diagramClasses) {
			classNames.add(diagramClazz.getName());
		}

		Field[] fields = clazz.getDeclaredFields();
		StringBuilder builder = new StringBuilder();

		for (Field field : fields) {
			builder.append("\n");
			builder.append("\t");
			// Trata campos normais
			if (classNames.contains(field.getType().getName())) {
				Cardinality cardinality = discoverCardinality(field, clazz);
				switch (cardinality) {
				case OneToOne:
					builder.append("1->1");
					break;
				case OneToMany:
					builder.append("1->*");
					break;
				case ManyToMany:
					builder.append("*->*");
					break;
				default:
					continue;
				}
				builder.append("(").append(field.getType().getSimpleName())
						.append("); ");
			}
			// Trata campos do tipo COllection com generics
			else if (verifyGenericParameter(classNames, field)) {
				Cardinality cardinality = discoverCardinality(field, clazz);
				switch (cardinality) {
				case OneToOne:
					builder.append("1->1");
					break;
				case OneToMany:
					builder.append("1->*");
					break;
				case ManyToMany:
					builder.append("*->*");
					break;
				default:
					continue;
				}
				String fieldName = field.getGenericType().toString();
				fieldName = fieldName.substring(fieldName.lastIndexOf(".") + 1,
						fieldName.length() - 1);

				builder.append("(").append(fieldName).append("); ");
			}
		}
		return builder.toString();
	}

	private static boolean verifyGenericParameter(List<String> classNames,
			Field field) {
		for (String className : classNames) {
			if (field.getGenericType().toString().contains(className + ">")) {
				return true;
			}
		}
		return classNames.contains(field.getGenericType());
	}

	private static Cardinality discoverCardinality(Field field, Class<?> clazz) {
		try {
			Cardinality cardinality = testCardinality(field);

			if (cardinality == null) {
				String fieldName = field.getName();
				String firstCharacter = fieldName.substring(0, 1);
				String getterName = "get"
						+ fieldName.replaceFirst(firstCharacter, firstCharacter
								.toUpperCase());

				Class<?>[] params = {};
				Method getter = clazz.getMethod(getterName, params);

				cardinality = testCardinality(getter);
			}
			return cardinality;
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			return Cardinality.OneToOne;
		}
	}

	private static Cardinality testCardinality(AccessibleObject obj) {
		if (obj.isAnnotationPresent(OneToOne.class)) {
			return Cardinality.OneToOne;
		} else if (obj.isAnnotationPresent(OneToMany.class)) {
			return Cardinality.OneToMany;
		} else if (obj.isAnnotationPresent(ManyToMany.class)) {
			return Cardinality.ManyToMany;
		}
		return null;
	}

	private static enum Cardinality {
		OneToOne, OneToMany, ManyToMany
	}

}
