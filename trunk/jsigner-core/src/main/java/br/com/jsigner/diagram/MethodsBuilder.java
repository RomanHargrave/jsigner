package br.com.jsigner.diagram;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class MethodsBuilder {

	public static String generateMethodsCode(Class<?> clazz) {
		StringBuilder builder = new StringBuilder();

		Method[] methods = clazz.getDeclaredMethods();
		for (Method method : methods) {
			int mod = method.getModifiers();

			if (Modifier.isPrivate(mod) || method.getName().startsWith("get")
					|| method.getName().startsWith("set") || method.getName().startsWith("is")) {
				continue;
			}
			builder.append("\n");
			builder.append("\t");
			extractVisibilityModifier(builder, mod);
			extractCustomModifier(builder, mod);
			builder.append(method.getName()).append("(");
			 Class<?>[] parameterTypes = method.getParameterTypes();
			 boolean isFirstParameter = true;
			 
			 for (Class<?> parameterClass : parameterTypes) {
				 if (!isFirstParameter) {
					 builder.append(", ");
				 }
				 builder.append(parameterClass.getSimpleName());
				 isFirstParameter = false;
			 }
			 builder.append(")");
			builder.append(":").append(method.getReturnType().getSimpleName())
					.append(";");
		}
		return builder.toString();
	}

	private static void extractCustomModifier(StringBuilder builder, int mod) {
		if (Modifier.isStatic(mod)) {
			builder.append("static ");
		}
		if (Modifier.isAbstract(mod)) {
			builder.append("abstract ");
		}
	}

	private static void extractVisibilityModifier(StringBuilder builder, int mod) {
		if (Modifier.isProtected(mod)) {
			builder.append("# ");
		} else if (Modifier.isPublic(mod)) {
			builder.append("+ ");
		}
	}
}
