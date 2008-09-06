/**
 * Copyright (C) 2008 Rafael Farias Silva <rafaferry@gmail.com>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *         http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
