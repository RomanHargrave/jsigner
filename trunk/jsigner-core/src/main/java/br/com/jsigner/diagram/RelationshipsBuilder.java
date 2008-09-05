package br.com.jsigner.diagram;

import java.lang.reflect.Field;

import br.com.jsigner.relationship.Relationship;
import br.com.jsigner.repository.RelationshipRepository;

public class RelationshipsBuilder {

	public static String generateRelationShipsCode(Class<?> clazz,
			ClassDiagram classDiagram) {

		Field[] fields = clazz.getDeclaredFields();
		StringBuilder builder = new StringBuilder();

		for (Field field : fields) {
			builder.append("\n");
			builder.append("\t");

			RelationshipSpecification specification = new RelationshipSpecification();
			if (specification.isRelationship(clazz, classDiagram
					.getClassesNames(), field)) {
				Relationship relationship = new Relationship(clazz, field,
						classDiagram);

				if (!RelationshipRepository.relationshipExists(relationship)) {
					builder.append(relationship.getCode());
					RelationshipRepository.store(relationship);
				}
			}
		}
		return builder.toString();
	}
}
