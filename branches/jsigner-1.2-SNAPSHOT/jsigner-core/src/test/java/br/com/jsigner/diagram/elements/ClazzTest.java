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

package br.com.jsigner.diagram.elements;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import br.com.jsigner.diagram.ClassDiagram;
import br.com.jsigner.diagram.elements.relationship.RelationshipTest;
import br.com.jsigner.interpreter.ClazzVisitor;
import br.com.jsigner.modsl.interpreter.ModslClazzVisitor;

public class ClazzTest {

	@Test
	public void classTest() throws Exception {
		ClassDiagram diagram = new ClassDiagram("test");
		diagram.getClassesNames().add(RelationshipTest.Engine.class.getName());
		Clazz clazz = new Clazz(RelationshipTest.Car.class, diagram);

		ClazzVisitor visitor = new ModslClazzVisitor();
		clazz.accept(visitor);

		String result = visitor.getResult();

		assertTrue(result.contains("class Car"));
		assertFalse(result.contains("1->1(Engine)"));

		clazz.setup();
		clazz.accept(visitor);
		result = visitor.getResult();
		assertTrue(result.contains("1->1(Engine)"));
	}
}
