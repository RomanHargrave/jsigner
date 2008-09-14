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

import java.util.Iterator;
import java.util.List;

import br.com.jsigner.diagram.elements.method.Method;
import br.com.jsigner.interpreter.MethodVisitor;

public class ModslMethodVisitor implements MethodVisitor{
	
	private StringBuilder diagramCode = new StringBuilder();

	public void visit(Method method) {
		diagramCode.append("\t\t");
		if(method.isPrivate()) {
			diagramCode.append("- ");
		} else if (method.isProtected()) {
			diagramCode.append("# ");
		} else if (method.isPublic()) {
			diagramCode.append("+ ");
		}
		
		if (method.isStatic()) {
			diagramCode.append("static ");
		}
		if (method.isAbstract()) {
			diagramCode.append("abstract ");
		}
		
		diagramCode.append(method.getName()).append("(");
		
		List<String> parameters = method.getParameters();
		Iterator<String> iterator = parameters.iterator();
		
		if (iterator.hasNext()) {
			diagramCode.append(iterator.next()); 
		}
		while (iterator.hasNext()) {
			String parameter = (String) iterator.next();
			diagramCode.append(", ");
			diagramCode.append(parameter);
		}
		diagramCode.append("):");
		diagramCode.append(method.getReturnType());
		diagramCode.append(";\n");
	}

	public String getResult() {
		String result = diagramCode.toString();
		diagramCode = new StringBuilder();
		return result;
	}
}
