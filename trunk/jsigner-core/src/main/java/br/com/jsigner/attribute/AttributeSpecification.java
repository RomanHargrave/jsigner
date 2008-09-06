package br.com.jsigner.attribute;

import java.lang.reflect.Field;
import java.util.List;

import br.com.jsigner.diagram.RelationshipSpecification;

public class AttributeSpecification {

	public boolean isAttribute(Class<?> clazz, List<String> classesNames,
			Field field) {
		RelationshipSpecification relationshipSpecification = new RelationshipSpecification();
		
		//TODO Aplicar regras configuraveis
		return !relationshipSpecification.isRelationship(clazz, classesNames,
				field)
				&& !field.getName().equals("serialVersionUID")
				&& !field.getName().contains("$");
	}

}
