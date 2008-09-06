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

import java.util.ArrayList;
import java.util.List;

import br.com.jsigner.diagram.elements.Clazz;
import br.com.jsigner.interpreter.Visitor;

public class ClassDiagram {

	private String name;
	private List<Class<?>> classesOld = new ArrayList<Class<?>>();
	private List<String> classesNames = new ArrayList<String>();
	
	private List<Clazz> classes = new ArrayList<Clazz>();

	public ClassDiagram(String diagramName) {
		this.name = diagramName;
	}

	public void addClass(Class<?> clazz) {
		this.classesOld.add(clazz);
		this.classesNames.add(clazz.getName());
	}
	
	
	public void accept(Visitor<ClassDiagram> visitor) {
		visitor.visit(this);
	}
	//TODO replace with visitor
	public String generateDiagramCode() {
		StringBuilder builder = new StringBuilder();

		
	}

	public String getName() {
		return name;
	}

	public List<Clazz> getClasses() {
		return classes;
	}

	public List<Class<?>> getClassesOld() {
		return classesOld;
	}

	public List<String> getClassesNames() {
		return classesNames;
	}

}
