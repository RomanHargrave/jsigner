package br.com.jsigner.diagram.elements;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import br.com.jsigner.diagram.ClassDiagram;
import br.com.jsigner.diagram.elements.attribute.Attribute;
import br.com.jsigner.diagram.elements.attribute.AttributeSpecification;
import br.com.jsigner.diagram.elements.method.Method;
import br.com.jsigner.diagram.elements.method.MethodSpecification;
import br.com.jsigner.diagram.elements.method.MethodVisitor;
import br.com.jsigner.diagram.elements.relationship.Relationship;
import br.com.jsigner.diagram.elements.relationship.RelationshipSpecification;
import br.com.jsigner.diagram.elements.relationship.RelationshipVisitor;
import br.com.jsigner.interpreter.modsl.AttributeVisitor;
import br.com.jsigner.interpreter.modsl.ClazzVisitor;

public class Clazz {

	private Class<?> wrappedClass;
	private List<Attribute> attributes = new ArrayList<Attribute>();
	private List<Relationship> relationships = new ArrayList<Relationship>();
	private List<Method> methods = new ArrayList<Method>();

	private ClassDiagram containerDiagram;

	public Clazz(Class<?> wrappedClass, ClassDiagram classDiagram) {
		super();
		this.wrappedClass = wrappedClass;
		this.containerDiagram = classDiagram;
		this.setup();
	}

	private void setup() {
		AttributeSpecification attributeSpecification = new AttributeSpecification();
		RelationshipSpecification relationshipSpecification = new RelationshipSpecification();

		Field[] fields = wrappedClass.getDeclaredFields();
		for (Field field : fields) {
			if (attributeSpecification.isAttribute(wrappedClass,
					containerDiagram.getClassesNames(), field)) {
				this.attributes.add(new Attribute(field));
			} else if (relationshipSpecification.isRelationship(wrappedClass,
					containerDiagram.getClassesNames(), field)) {
				this.relationships.add(new Relationship(wrappedClass, field,
						containerDiagram));
			}
		}
		
		MethodSpecification methodSpecification = new MethodSpecification();
		for (java.lang.reflect.Method method : wrappedClass.getMethods()) {
			if (methodSpecification.isMethod(method)) {
				this.methods.add(new Method(method));
			}
		}
	}


	public boolean isAbstract() {
		return Modifier.isAbstract(wrappedClass.getModifiers());
	}

	public String getName() {
		return wrappedClass.getName();
	}

	public String getSimpleName() {
		return wrappedClass.getSimpleName();
	}

	public boolean hasSuperclass() {
		return wrappedClass.getSuperclass() != null
				&& containerDiagram.containsClass(wrappedClass.getSuperclass());
	}

	public String getSuperclassName() {
		if (!hasSuperclass()) {
			throw new RuntimeException(
					"this method should be used only when hasSuplerclass is true!");
		}
		return wrappedClass.getSuperclass().getSimpleName();
	}

	public boolean wrappedClassEquals(Clazz existingClazz) {
		return existingClazz.getName().equals(wrappedClass.getName());
	}

	public void accept(ClazzVisitor visitor) {
		visitor.visit(this);
		
		AttributeVisitor attributeVisitor = visitor.getAttributeVisitor();
		for (Attribute attribute : this.attributes) {
			attribute.accept(attributeVisitor);			
		}
		
		RelationshipVisitor relationshipVisitor = visitor.getRelationshipVisitor();
		for (Relationship relationship : this.relationships) {
			relationship.accept(relationshipVisitor);
		}
		
		MethodVisitor methodVisitor = visitor.getMethodVisitor();
	}
}
