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

package br.com.jsigner;

import java.util.ArrayList;
import java.util.List;

import br.com.jsigner.diagram.elements.relationship.PersistenceMultiplicityFinder;
import br.com.jsigner.diagram.elements.relationship.RelationshipMultiplicityFinder;

public abstract class JsignerConfiguration {

	private static List<Class<?>> diagramClasses;
	private static List<String> diagramClassesNames;
	private static RelationshipMultiplicityFinder configuredFinder = new PersistenceMultiplicityFinder();

	public static RelationshipMultiplicityFinder getMultiplicityFinder() {
		return configuredFinder;
	}

	public static void setMultiplicityFinder(
			RelationshipMultiplicityFinder multiplicityFinder) {
		configuredFinder = multiplicityFinder;
	}

	public static void setDiagramClasses(List<Class<?>> diagramClasses) {
		JsignerConfiguration.diagramClasses = diagramClasses;

		diagramClassesNames = new ArrayList<String>(diagramClasses.size());
		for (Class<?> clazz : diagramClasses) {
			diagramClassesNames.add(clazz.getName());
		}
	}

	public static List<Class<?>> getDiagramClasses() {
		return diagramClasses;
	}

	public static List<String> getClassesNames() {
		return diagramClassesNames;
	}

	public static boolean hidePrivateMethods() {
		return true;
	}

	public static boolean hideSetters() {
		return true;
	}

	public static boolean hideGetters() {
		return true;
	}

}
