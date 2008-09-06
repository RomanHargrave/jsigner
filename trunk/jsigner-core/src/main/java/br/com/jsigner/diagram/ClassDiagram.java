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

import java.util.ArrayList;
import java.util.List;

public class ClassDiagram {

	private String name;
	private List<Class<?>> classes = new ArrayList<Class<?>>();
	private List<String> classesNames = new ArrayList<String>();

	public ClassDiagram(String diagramName) {
		this.name = diagramName;
	}

	public void addClass(Class<?> clazz) {
		this.classes.add(clazz);
		this.classesNames.add(clazz.getName());
	}

	public String generateDiagramCode() {
		StringBuilder builder = new StringBuilder();

		builder.append("class diagram ").append(name).append("{\n");
		builder.append(ClassesBuilder.generateClassesCode(this));
		builder.append("}");

		
		return builder.toString();
	}

	public String getName() {
		return name;
	}

	public List<Class<?>> getClasses() {
		return classes;
	}

	public List<String> getClassesNames() {
		return classesNames;
	}

}
