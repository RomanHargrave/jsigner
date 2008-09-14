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

package br.com.jsigner.diagram.elements.relationship;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import br.com.jsigner.JsignerConfiguration;
import br.com.jsigner.diagram.ClassDiagram;
import br.com.jsigner.diagram.elements.relationship.multiplicity.CollectionMultiplicityFinder;
import br.com.jsigner.diagram.elements.relationship.multiplicity.Multiplicity;
import br.com.jsigner.diagram.elements.relationship.multiplicity.RelationshipMultiplicityFinder;
import br.com.jsigner.interpreter.RelationshipVisitor;
import br.com.jsigner.log.JsignerLog;

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
		
		if (this.isGeneric(field)) {
			String fieldName = field.getGenericType().toString();
			this.targetClassName = fieldName.substring(fieldName
					.lastIndexOf(".") + 1, fieldName.length() - 1);

			// Skips inner classes
			this.targetClassName = targetClassName.substring(targetClassName
					.lastIndexOf("$") + 1, targetClassName.length());
		} else {
			this.targetClassName = field.getType().getSimpleName();
		}
		
		this.discoverMultiplicity(root, field);
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
					multiplicityFinder = new CollectionMultiplicityFinder();
					JsignerLog log = JsignerConfiguration.getLog();
					log.info("Could not define multiplicity for relationship " + this.toString()
							+ " with persistence annotations. The multiplicity will be defined based on collections.");
					multiplicity = multiplicityFinder.findRelationshipMultiplicity(field);
				}
			}
			this.multiplicity = multiplicity;
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			this.multiplicity = Multiplicity.OneToOne;
		}
	}

	public void accept(RelationshipVisitor relationshipVisitor) {
		relationshipVisitor.visit(this);
	}

	public boolean isInverseRelation(Relationship other) {
		return other.targetClassName.equals(this.rootClassName)
				&& other.rootClassName.equals(this.targetClassName);
	}

	public Multiplicity getMultiplicity() {
		return multiplicity;
	}

	public void setMultiplicity(Multiplicity multiplicity) {
		this.multiplicity = multiplicity;
	}

	public String getTargetClassName() {
		return targetClassName;
	}

	public void setTargetClassName(String targetClassName) {
		this.targetClassName = targetClassName;
	}

	@Override
	public String toString() {
		return rootClassName + "->" + targetClassName;
	}

}
