/**
 * Copyright (C) 2008 Jsigner <rafaferry@gmail.com>
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

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class ClassesBuilder {

	protected static String generateClassesCode(ClassDiagram classDiagram) {
		StringBuilder builder = new StringBuilder();
		builder = new StringBuilder();

		List<String> classNames = new ArrayList<String>();
		for (Class<?> diagramClass : classDiagram.getClasses()) {
			classNames.add(diagramClass.getName());
		}

		for (Class<?> clazz : classDiagram.getClasses()) {
			if (Modifier.isAbstract(clazz.getModifiers())) {
				builder.append("abstract ");
			}
			builder.append("class ").append(clazz.getSimpleName()).append(" ");

			if (clazz.getSuperclass() != null
					&& classNames.contains(clazz.getSuperclass().getName())) {
				builder.append("extends ").append(
						clazz.getSuperclass().getSimpleName());
			}

			builder.append("{\n");

			builder.append(AttributesBuilder.generateAttributesCode(clazz,
					classDiagram));
			builder.append(RelationshipsBuilder.generateRelationShipsCode(
					clazz, classDiagram));
			builder.append(MethodsBuilder.generateMethodsCode(clazz));

			builder.append("\n}\n");
		}

		return builder.toString();
	}
}
