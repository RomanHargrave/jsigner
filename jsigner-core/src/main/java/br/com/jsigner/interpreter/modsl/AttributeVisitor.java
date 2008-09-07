package br.com.jsigner.interpreter.modsl;

import br.com.jsigner.diagram.elements.attribute.Attribute;
import br.com.jsigner.interpreter.Visitor;

public class AttributeVisitor implements Visitor<Attribute, String> {

	private StringBuilder diagramCode = new StringBuilder();

	public void visit(Attribute attribute) {
		diagramCode.append(attribute.getName());
		diagramCode.append(":");
		diagramCode.append(attribute.getType());
		diagramCode.append(";");
	}

	public String getResult() {
		return diagramCode.toString();
	}

}
