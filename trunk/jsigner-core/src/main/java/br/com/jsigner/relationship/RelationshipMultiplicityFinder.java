package br.com.jsigner.relationship;

import java.lang.reflect.AccessibleObject;

public interface RelationshipMultiplicityFinder {
	
	public Multiplicity findRelationshipMultiplicity(AccessibleObject object);
		

}
