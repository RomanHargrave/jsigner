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

			// Se a classe do atributo esta mapeada com @Domain, entao nao eh
			// tratada como atributo
			if (classNames.contains(field.getType().getName())
					|| verifyGenericParameter(classNames, field)
					|| field.getName().equals("serialVersionUID") || field.getName().contains("$")) {
				continue;
			}

			builder.append(field.getName()).append(":");
			builder.append(field.getType().getSimpleName()).append(";");
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

}
