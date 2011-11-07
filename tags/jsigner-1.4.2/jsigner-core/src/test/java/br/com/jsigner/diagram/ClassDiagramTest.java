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

package br.com.jsigner.diagram;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import br.com.jsigner.diagram.elements.relationship.RelationshipTest;
import br.com.jsigner.interpreter.ClassDiagramVisitor;
import br.com.jsigner.modsl.interpreter.ModslClassDiagramVisitor;

public class ClassDiagramTest {

	@Test
	public void test() throws Exception {
		final List<Class<?>> classes = new ArrayList<Class<?>>();
		classes.add(RelationshipTest.Car.class);
		ClassDiagram diagram = new ClassDiagram("Test", classes);

		
		assertTrue(diagram.containsClass(RelationshipTest.Car.class));
		assertFalse(diagram.containsClass(RelationshipTest.Engine.class));
		
		ClassDiagramVisitor visitor = new ModslClassDiagramVisitor();
		diagram.accept(visitor);
		
		String result = visitor.getResult();
		assertTrue(result.contains("class diagram Test {"));
		assertTrue(result.contains("class Car"));
	}
}
