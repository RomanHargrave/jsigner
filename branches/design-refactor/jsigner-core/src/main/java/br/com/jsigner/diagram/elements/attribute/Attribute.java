package br.com.jsigner.diagram.elements.attribute;

import java.lang.reflect.Field;

import br.com.jsigner.interpreter.modsl.AttributeVisitor;

public class Attribute {

	private Field wrappedAttribute;
	
	public Attribute(Field wrappedAttribute) {
		super();
		this.wrappedAttribute = wrappedAttribute;
	}

	public String getName() {
		return wrappedAttribute.getName();
	}
	
	public String getType() {
		return wrappedAttribute.getType().getSimpleName();
	}

	public void accept(AttributeVisitor attributeVisitor) {
		attributeVisitor.visit(this);
	}
}
