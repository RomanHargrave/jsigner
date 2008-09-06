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

package br.com.jsigner.relationship;

import java.lang.reflect.AccessibleObject;

import javax.persistence.Embedded;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

public class PersistenceMultiplicityFinder implements RelationshipMultiplicityFinder {

	public Multiplicity findRelationshipMultiplicity(AccessibleObject obj) {
			if (obj.isAnnotationPresent(OneToOne.class)
					|| obj.isAnnotationPresent(ManyToOne.class)
					|| obj.isAnnotationPresent(Embedded.class)) {
				return Multiplicity.OneToOne;
			} else if (obj.isAnnotationPresent(OneToMany.class)) {
				return Multiplicity.OneToMany;
			} else if (obj.isAnnotationPresent(ManyToMany.class)) {
				return Multiplicity.ManyToMany;
			}
			return null;
	}
}
