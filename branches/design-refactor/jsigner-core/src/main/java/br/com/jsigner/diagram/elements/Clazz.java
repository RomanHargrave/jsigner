package br.com.jsigner.diagram.elements;

import java.util.List;

import br.com.jsigner.interpreter.Visitor;

public class Clazz {

	private Class<?> wrappedClass;
	private List<Attribute> attributes;
	private List<Relationship> relationships;
	private List<Method> methods;

	public Clazz(Class<?> wrappedClass) {
		super();
		this.wrappedClass = wrappedClass;

		this.buildAttributes();
		this.buildRelationships();
		this.buildMethods();
	}

	private void buildAttributes() {
		// TODO Auto-generated method stub
	}

	private void buildRelationships() {
		// TODO Auto-generated method stub
	}

	private void buildMethods() {
		// TODO Auto-generated method stub
	}
	
	public void accept(Visitor<Clazz> v) {
		
	}

}
