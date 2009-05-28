/**
 * Copyright (C) 2008 Rafael Farias Silva <rafaferry@gmail.com>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *         http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package br.com.jsigner.diagram.elements.relationship;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Test;

import br.com.jsigner.JsignerConfiguration;
import br.com.jsigner.diagram.ClassDiagram;
import br.com.jsigner.diagram.elements.relationship.multiplicity.Multiplicity;
import br.com.jsigner.diagram.elements.relationship.multiplicity.RelationshipMultiplicityFinder;
import br.com.jsigner.modsl.interpreter.ModslRelationshipVisitor;

public class RelationshipTest {

	@SuppressWarnings("unchecked")
	@Test
	public void relationshipTest() throws Exception {
		RelationshipMultiplicityFinder multiplicityFinder = EasyMock
				.createNiceMock(RelationshipMultiplicityFinder.class);
		EasyMock.expect(
				multiplicityFinder.findRelationshipMultiplicity((AccessibleObject) EasyMock
						.anyObject())).andReturn(Multiplicity.OneToMany).once();
		EasyMock.expect(
				multiplicityFinder.findRelationshipMultiplicity((AccessibleObject) EasyMock
						.anyObject())).andReturn(Multiplicity.OneToOne).once();
		EasyMock.expect(
				multiplicityFinder.findRelationshipMultiplicity((AccessibleObject) EasyMock
						.anyObject())).andReturn(Multiplicity.ManyToMany).once();
		EasyMock.replay(multiplicityFinder);
		
		JsignerConfiguration.setMultiplicityFinder(multiplicityFinder);
		Field field = Car.class.getDeclaredField("engine");
		ClassDiagram diagram = new ClassDiagram("testDiagram", Arrays.asList(Car.class, Engine.class));

		Relationship relationship = new Relationship(new Car().getClass(), field, diagram);
		Relationship relationship2 = new Relationship(new Car().getClass(), field, diagram);
		Relationship relationship3 = new Relationship(new Car().getClass(), field, diagram);
		
		assertEquals("Engine", relationship.getTargetClassName());
		
		ModslRelationshipVisitor visitor = new ModslRelationshipVisitor();
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

	public static class Car {
		@SuppressWarnings("unused")
		private Engine engine;

	}

	public static class Engine {
		@SuppressWarnings("unused")
		private List<Car> cars;
		@SuppressWarnings("unused")
		private Car car;
	}
}
