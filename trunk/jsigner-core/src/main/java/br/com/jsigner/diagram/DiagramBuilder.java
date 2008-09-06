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

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import br.com.jsigner.JsignerConfiguration;
import br.com.jsigner.annotations.Domain;

public class DiagramBuilder {

	private HashMap<String, ClassDiagram> diagrams = new HashMap<String, ClassDiagram>();

	public void build(List<Class<?>> diagramClasses) {
		for (Class<?> clazz : diagramClasses) {
			String[] diagramNames = clazz.getAnnotation(Domain.class).value();

			for (String diagramName : diagramNames) {
				ClassDiagram classDiagram = diagrams.get(diagramName);
				if (classDiagram == null) {
					classDiagram = new ClassDiagram(diagramName);
				}
				classDiagram.addClass(clazz);
				diagrams.put(classDiagram.getName(), classDiagram);
			}
		}
	}

	public Collection<ClassDiagram> getDiagrams() {
		return diagrams.values();
	}

}
