package br.com.jsigner.interpreter.modsl;

import java.util.List;

import br.com.jsigner.diagram.ClassDiagram;
import br.com.jsigner.diagram.elements.Clazz;
import br.com.jsigner.interpreter.Visitor;

public class ClassDiagramVisitor implements Visitor<ClassDiagram, String> {

	private StringBuilder diagramCode = new StringBuilder();
	private List<Clazz> diagramClasses;

	public void setup(List<Clazz> diagramClasses) {
		this.diagramClasses = diagramClasses;
	}

	public void visit(ClassDiagram classDiagram) {
		diagramCode.append("class diagram ").append(classDiagram.getName())
				.append(" {\n");

		ClazzVisitor clazzVisitor = new ClazzVisitor();
		for (Clazz clazz : diagramClasses) {
			clazz.accept(clazzVisitor);
		}

		diagramCode.append("}");
	}

	public String getResult() {
		return diagramCode.toString();
	}
}
