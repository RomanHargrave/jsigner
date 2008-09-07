package br.com.jsigner.diagram.elements.relationship;

import br.com.jsigner.interpreter.Visitor;

public class RelationshipVisitor implements Visitor<Relationship, String> {
	
	private StringBuilder diagramCode = new StringBuilder();

	public String getResult() {
		return diagramCode.toString();
	}

	public void visit(Relationship relationship) {
		Multiplicity multiplicity = relationship.getMultiplicity();
		switch (multiplicity) {
		case OneToOne:
			diagramCode.append("1->1");
			break;
		case OneToMany:
			diagramCode.append("1->*");
			break;
		case ManyToMany:
			diagramCode.append("*->*");
			break;
		}
		diagramCode.append("(").append(relationship.getTargetClassName()).append(");\n ");
	}

}
