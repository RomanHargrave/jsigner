package br.com.jsigner.relationship;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import br.com.jsigner.JsignerConfiguration;
import br.com.jsigner.diagram.ClassDiagram;
import br.com.jsigner.diagram.ImpossibleDefineMultiplicityException;

public class Relationship {

	private Multiplicity multiplicity;
	private Class<?> root;
	private Field target;
	private ClassDiagram classDiagram;

	public Relationship(Class<?> root, Field field, ClassDiagram classDiagram) {
		this.root = root;
		this.target = field;

		this.discoverMultiplicity();
	}

	private boolean isTargetGeneric() {
		for (String className : classDiagram.getClassesNames()) {
			if (target.getGenericType().toString().contains(className + ">")) {
				return true;
			}
		}
		return classDiagram.getClassesNames().contains(target.getGenericType());
	}

	public void discoverMultiplicity() {
		try {
			RelationshipMultiplicityFinder multiplicityFinder = JsignerConfiguration
					.getMultiplicityFinder();
			Multiplicity multiplicity = multiplicityFinder
					.findRelationshipMultiplicity(target);

			if (multiplicity == null) {
				String fieldName = target.getName();
				String firstCharacter = fieldName.substring(0, 1);
				String getterName = "get"
						+ fieldName.replaceFirst(firstCharacter, firstCharacter
								.toUpperCase());

				Class<?>[] params = {};
				Method getter = root.getMethod(getterName, params);

				multiplicity = multiplicityFinder
						.findRelationshipMultiplicity(getter);
				if (multiplicity == null) {
					throw new ImpossibleDefineMultiplicityException(root,
							target);
				}
			}
			this.multiplicity = multiplicity;
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			this.multiplicity = Multiplicity.OneToOne;
		}
	}

	// TODO replace with visitor
	public String getCode() {
		StringBuilder builder = new StringBuilder();
		switch (multiplicity) {
		case OneToOne:
			builder.append("1->1");
			break;
		case OneToMany:
			builder.append("1->*");
			break;
		case ManyToMany:
			builder.append("*->*");
			break;
		}

		if (isTargetGeneric()) {
			String fieldName = target.getGenericType().toString();
			fieldName = fieldName.substring(fieldName.lastIndexOf(".") + 1,
					fieldName.length() - 1);
			builder.append("(").append(fieldName).append("); ");
		} else {
			builder.append("(").append(target.getType().getSimpleName())
					.append("); ");
		}
		return builder.toString();
	}
	
	//TODO when the target is a Collection, dont store the colection as target, we need to store the type of collection!
	public boolean isInverseRelation(Relationship other) {
		return other.target.getClass().equals(this.root)
				&& other.root.equals(this.target.getClass());
	}
}
