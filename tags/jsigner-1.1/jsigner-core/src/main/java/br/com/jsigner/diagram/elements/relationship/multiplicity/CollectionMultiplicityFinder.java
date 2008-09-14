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

package br.com.jsigner.diagram.elements.relationship.multiplicity;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.util.Collection;

public class CollectionMultiplicityFinder implements RelationshipMultiplicityFinder {

	public Multiplicity findRelationshipMultiplicity(AccessibleObject object) {
		Field f = (Field) object;
		Class<?>[] interfaces = f.getType().getInterfaces();
		for (Class<?> clazz : interfaces) {
			if (clazz.equals(Collection.class)) {
				return Multiplicity.OneToMany;
			}
		}
			return Multiplicity.OneToOne;
	}

}
