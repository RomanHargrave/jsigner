package br.com.jsigner.diagram.elements.method;

import java.util.Iterator;
import java.util.List;

import br.com.jsigner.interpreter.Visitor;

public class MethodVisitor implements Visitor<Method, String>{
	
	private StringBuilder diagramCode = new StringBuilder();

	public String getResult() {
		return diagramCode.toString();
	}

	public void visit(Method method) {
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

}
