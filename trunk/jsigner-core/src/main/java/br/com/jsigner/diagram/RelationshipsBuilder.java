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

import br.com.jsigner.relationship.Relationship;
import br.com.jsigner.repository.RelationshipRepository;

public class RelationshipsBuilder {

	public static String generateRelationShipsCode(Class<?> clazz,
			ClassDiagram classDiagram) {

		Field[] fields = clazz.getDeclaredFields();
		StringBuilder builder = new StringBuilder();

		for (Field field : fields) {
			builder.append("\n");
			builder.append("\t");

			RelationshipSpecification specification = new RelationshipSpecification();
			if (specification.isRelationship(clazz, classDiagram
					.getClassesNames(), field)) {
				Relationship relationship = new Relationship(clazz, field,
						classDiagram);

				if (!RelationshipRepository.inverseRelationshipExists(relationship)) {
					builder.append(relationship.getCode());
					RelationshipRepository.store(relationship);
				}
			}
		}
		return builder.toString();
	}
}
