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

import java.lang.reflect.Field;
import java.util.List;

import br.com.jsigner.relationship.Relationship;
import br.com.jsigner.repository.RelationshipRepository;

public class RelationshipSpecification {

	public RelationshipSpecification() {
	}

	public boolean isRelationship(Class<?> clazz, List<String> classNames,
			Field field) {
		
		if (suitableForBeRelationship(clazz, classNames, field)) {
			Relationship relationship = new Relationship(clazz, field, classNames);
			if (!RelationshipRepository.inverseRelationshipExists(relationship)) {
				return true;
			}
		}
		return false;
	}

	private boolean suitableForBeRelationship(Class<?> clazz,
			List<String> classNames, Field field) {
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
