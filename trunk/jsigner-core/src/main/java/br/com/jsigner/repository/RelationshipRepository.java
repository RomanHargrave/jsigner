package br.com.jsigner.repository;

import java.util.ArrayList;
import java.util.List;

import br.com.jsigner.relationship.Relationship;

public class RelationshipRepository {

	private static List<Relationship> relationships = new ArrayList<Relationship>();

	public static void store(Relationship relationship) {
		relationships.add(relationship);
	}

	public static boolean inverseRelationshipExists(Relationship relationship) {
		for (Relationship storedRelationship : relationships) {
			if (storedRelationship.isInverseRelation(relationship)) {
				return true;
			}
		}
		return false;
	}

}
