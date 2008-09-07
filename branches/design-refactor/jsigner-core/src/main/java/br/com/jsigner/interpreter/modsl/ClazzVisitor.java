package br.com.jsigner.interpreter.modsl;

import br.com.jsigner.diagram.elements.Clazz;
import br.com.jsigner.diagram.elements.method.MethodVisitor;
import br.com.jsigner.diagram.elements.relationship.RelationshipVisitor;
import br.com.jsigner.interpreter.Visitor;

public class ClazzVisitor implements Visitor<Clazz, String>{

	private StringBuilder diagramCode = new StringBuilder();
	
	public void visit(Clazz clazz) {
		diagramCode.append("\t");
			if (clazz.isAbstract()) {
				diagramCode.append("abstract ");
			}
			diagramCode.append("class ").append(clazz.getSimpleName()).append(" ");

			if (clazz.hasSuperclass()) {
				diagramCode.append("extends ").append(
						clazz.getSuperclassName());
			}
	}

	public AttributeVisitor getAttributeVisitor() {
		return null;
	}

	public RelationshipVisitor getRelationshipVisitor() {
		return null;
	}

	public String getResult() {
		return diagramCode.toString();
	}

	public MethodVisitor getMethodVisitor() {
		return null;
	}
}
