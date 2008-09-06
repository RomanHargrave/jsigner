package br.com.jsigner.interpreter.modsl;

import java.util.List;

import br.com.jsigner.diagram.ClassDiagram;
import br.com.jsigner.diagram.elements.Clazz;
import br.com.jsigner.interpreter.Visitor;

public class DiagramVisitor implements Visitor<ClassDiagram> {

	private StringBuilder diagramCode = new StringBuilder();

	public void visit(ClassDiagram classDiagram) {
		diagramCode.append("class diagram ").append(classDiagram.getName())
				.append(" {\n");
		
		List<Clazz> classes = classDiagram.getClasses();
		
		ClazzVisitor clazzVisitor = new ClazzVisitor();
		
		for (Clazz clazz : classes) {
			clazz.accept(clazzVisitor);
		}
		
		diagramCode.append("}");
	}
}
