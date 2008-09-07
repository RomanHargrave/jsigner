package br.com.jsigner.diagram.elements.relationship;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Test;

import br.com.jsigner.JsignerConfiguration;
import br.com.jsigner.diagram.ClassDiagram;

public class RelationshipTest {

	@Test
	public void relationshipTest() throws Exception {
		RelationshipMultiplicityFinder multiplicityFinder = EasyMock
				.createNiceMock(RelationshipMultiplicityFinder.class);
		EasyMock.expect(
				multiplicityFinder.findRelationshipMultiplicity(EasyMock
						.anyObject())).andReturn(Multiplicity.OneToMany).once();
		EasyMock.expect(
				multiplicityFinder.findRelationshipMultiplicity(EasyMock
						.anyObject())).andReturn(Multiplicity.OneToOne).once();
		EasyMock.expect(
				multiplicityFinder.findRelationshipMultiplicity(EasyMock
						.anyObject())).andReturn(Multiplicity.ManyToMany).once();
		EasyMock.replay(multiplicityFinder);
		
		JsignerConfiguration.setMultiplicityFinder(multiplicityFinder);
		Field field = Car.class.getDeclaredField("engine");
		ClassDiagram diagram = new ClassDiagram("testDiagram");
		diagram.getClassesNames().add(Car.class.getName());
		diagram.getClassesNames().add(Engine.class.getName());

		Relationship relationship = new Relationship(new Car().getClass(), field, diagram);
		Relationship relationship2 = new Relationship(new Car().getClass(), field, diagram);
		Relationship relationship3 = new Relationship(new Car().getClass(), field, diagram);
		
		assertEquals("Engine", relationship.getTargetClassName());
		
		RelationshipVisitor visitor = new RelationshipVisitor();
		visitor.visit(relationship);
		visitor.visit(relationship2);
		visitor.visit(relationship3);
		
		String result = visitor.getResult();
		assertTrue(result.contains("1->*(Engine);"));
		assertTrue(result.contains("1->1(Engine);"));
		assertTrue(result.contains("*->*(Engine);"));
		
		assertFalse(relationship.isInverseRelation(relationship2));
		
		Field carField = Engine.class.getDeclaredField("car");
		Relationship relationship4 = new Relationship(new Engine().getClass(), carField, diagram);
		assertTrue(relationship.isInverseRelation(relationship4));
		
		Field carsField = Engine.class.getDeclaredField("cars");
		Relationship relationship5 = new Relationship(new Engine().getClass(), carsField, diagram);
		assertTrue(relationship.isInverseRelation(relationship5));
	}

	private static class Car {
		@SuppressWarnings("unused")
		private Engine engine;

	}

	private static class Engine {
		@SuppressWarnings("unused")
		private List<Car> cars;
		@SuppressWarnings("unused")
		private Car car;
	}
}
