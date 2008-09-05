package br.com.jsigner.relationship;

import java.lang.reflect.AccessibleObject;

import javax.persistence.Embedded;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

public class PersistenceMultiplicityFinder implements RelationshipMultiplicityFinder {

	public Multiplicity findRelationshipMultiplicity(AccessibleObject obj) {
			if (obj.isAnnotationPresent(OneToOne.class)
					|| obj.isAnnotationPresent(ManyToOne.class)
					|| obj.isAnnotationPresent(Embedded.class)) {
				return Multiplicity.OneToOne;
			} else if (obj.isAnnotationPresent(OneToMany.class)) {
				return Multiplicity.OneToMany;
			} else if (obj.isAnnotationPresent(ManyToMany.class)) {
				return Multiplicity.ManyToMany;
			}
			return null;
	}
}
