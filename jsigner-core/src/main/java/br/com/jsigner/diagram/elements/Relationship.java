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

package br.com.jsigner.diagram.elements;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import br.com.jsigner.JsignerConfiguration;
import br.com.jsigner.diagram.ClassDiagram;
import br.com.jsigner.diagram.ImpossibleDefineMultiplicityException;
import br.com.jsigner.relationship.Multiplicity;
import br.com.jsigner.relationship.RelationshipMultiplicityFinder;

public class Relationship {

	private Multiplicity multiplicity;
	private String rootClassName;
	private String targetClassName;
	private List<String> classesNames;

	public Relationship(Class<?> root, Field field, ClassDiagram classDiagram) {
		this.classesNames = classDiagram.getClassesNames();
		setup(root, field);
	}
	
	public Relationship(Class<?> root, Field field, List<String> classesNames) {
		this.classesNames = classesNames;
		setup(root, field);
	}

	private void setup(Class<?> root, Field field) {
		this.rootClassName = root.getSimpleName();
		this.discoverMultiplicity(root, field);

		if (this.isGeneric(field)) {
			String fieldName = field.getGenericType().toString();
			this.targetClassName = fieldName.substring(fieldName.lastIndexOf(".") + 1,
					fieldName.length() - 1);
		} else {
			this.targetClassName = field.getType().getSimpleName();
		}
	}

	private boolean isGeneric(Field field) {
		for (String className : classesNames) {
			if (field.getGenericType().toString().contains(className + ">")) {
				return true;
			}
		}
		return classesNames.contains(field.getGenericType());
	}

	public void discoverMultiplicity(Class<?> root, Field field) {
		try {
			RelationshipMultiplicityFinder multiplicityFinder = JsignerConfiguration
					.getMultiplicityFinder();
			Multiplicity multiplicity = multiplicityFinder
					.findRelationshipMultiplicity(field);

			if (multiplicity == null) {
				String fieldName = field.getName();
				String firstCharacter = fieldName.substring(0, 1);
				String getterName = "get"
						+ fieldName.replaceFirst(firstCharacter, firstCharacter
								.toUpperCase());

				Class<?>[] params = {};
				Method getter = root.getMethod(getterName, params);

				multiplicity = multiplicityFinder
						.findRelationshipMultiplicity(getter);
				if (multiplicity == null) {
					throw new ImpossibleDefineMultiplicityException(root, field);
				}
			}
			this.multiplicity = multiplicity;
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			this.multiplicity = Multiplicity.OneToOne;
		}
	}

	// TODO replace with visitor
	public String getCode() {
		StringBuilder builder = new StringBuilder();
		switch (multiplicity) {
		case OneToOne:
			builder.append("1->1");
			break;
		case OneToMany:
			builder.append("1->*");
			break;
		case ManyToMany:
			builder.append("*->*");
			break;
		}
		builder.append("(").append(targetClassName).append("); ");
		return builder.toString();
	}

	// TODO when the target is a Collection, dont store the colection as target,
	// we need to store the type of collection!
	public boolean isInverseRelation(Relationship other) {
		return other.targetClassName.equals(this.rootClassName)
				&& other.rootClassName.equals(this.targetClassName);
	}
}
