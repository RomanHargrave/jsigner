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

package br.com.jsigner.modsl.interpreter;

import br.com.jsigner.diagram.elements.Clazz;
import br.com.jsigner.interpreter.ClazzVisitor;

public class ModslClazzVisitor implements ClazzVisitor {

	private ModslAttributeVisitor attributeVisitor = new ModslAttributeVisitor();
	private ModslRelationshipVisitor relationshipVisitor = new ModslRelationshipVisitor();
	private ModslMethodVisitor methodVisitor = new ModslMethodVisitor();

	private StringBuilder diagramCode = new StringBuilder();

	public void visit(Clazz clazz) {
		diagramCode.append("\t");
		if (clazz.isAbstract()) {
			diagramCode.append("abstract ");
		}
		diagramCode.append("class ").append(clazz.getSimpleName()).append(" ");

		if (clazz.hasSuperclass()) {
			diagramCode.append("extends ").append(clazz.getSuperclassName());
		}
		
		diagramCode.append(" {\n");
		diagramCode.append("\t\t");
		diagramCode.append(this.attributeVisitor.getResult());
		diagramCode.append("\n");
		diagramCode.append("\t\t");
		diagramCode.append(this.relationshipVisitor.getResult());
		diagramCode.append("\n");
		diagramCode.append("\t\t");
		diagramCode.append(this.methodVisitor.getResult());
		diagramCode.append("\n");
		diagramCode.append("\t");
		diagramCode.append(" }\n");		
		
	}

	public ModslAttributeVisitor getAttributeVisitor() {
		return this.attributeVisitor;
	}

	public ModslRelationshipVisitor getRelationshipVisitor() {
		return this.relationshipVisitor;
	}

	public ModslMethodVisitor getMethodVisitor() {
		return this.methodVisitor;
	}

	public String getResult() {
		String result = diagramCode.toString();
		diagramCode = new StringBuilder();
		return result;
	}

}
