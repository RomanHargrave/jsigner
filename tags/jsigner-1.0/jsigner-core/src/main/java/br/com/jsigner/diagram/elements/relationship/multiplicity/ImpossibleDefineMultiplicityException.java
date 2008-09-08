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

import java.lang.reflect.Field;

public class ImpossibleDefineMultiplicityException extends RuntimeException {

	private static final long serialVersionUID = -5685081505713145406L;

	public ImpossibleDefineMultiplicityException(Class<?> clazz, Field field) {
		super("Impossible to define multiplicity for the relationship:"
				+ clazz.getSimpleName() + " -> " + field.getName());
	}
}
