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

package br.com.jsigner.diagram.elements.relationship.multiplicity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.junit.Test;

public class PersistenceMultiplicityFinderTest {

	@Test
	public void MultiplicityCantBeDefinedTest() throws Exception {
		PersistenceMultiplicityFinder multiplicityFinder = new PersistenceMultiplicityFinder();

		Field oneToOne = TestClass.class.getDeclaredField("testClass");
		Multiplicity result = multiplicityFinder
				.findRelationshipMultiplicity(oneToOne);

		assertNull(result);
	}

	@Test
	public void findMultiplicityInAttributeTest() throws Exception {
		PersistenceMultiplicityFinder multiplicityFinder = new PersistenceMultiplicityFinder();

		Field oneToOne = TestClass.class.getDeclaredField("oneToOne");
		Multiplicity result = multiplicityFinder
				.findRelationshipMultiplicity(oneToOne);
		assertEquals(Multiplicity.OneToOne, result);

		Field oneToMany = TestClass.class.getDeclaredField("oneToMany");
		result = multiplicityFinder.findRelationshipMultiplicity(oneToMany);
		assertEquals(Multiplicity.OneToMany, result);

		Field manyToMany = TestClass.class.getDeclaredField("manyToMany");
		result = multiplicityFinder.findRelationshipMultiplicity(manyToMany);
		assertEquals(Multiplicity.ManyToMany, result);

		Field manyToOne = TestClass.class.getDeclaredField("manyToOne");
		result = multiplicityFinder.findRelationshipMultiplicity(manyToOne);
		assertEquals(Multiplicity.OneToOne, result);
	}

	@Test
	public void findMultiplicityInGettersTest() throws Exception {
		PersistenceMultiplicityFinder multiplicityFinder = new PersistenceMultiplicityFinder();

		Class<?>[] args = {};
		Method getter = TestClass.class.getDeclaredMethod("getOneToOne", args);
		Multiplicity result = multiplicityFinder
				.findRelationshipMultiplicity(getter);
		assertEquals(Multiplicity.OneToOne, result);

		getter = TestClass.class.getDeclaredMethod("getOneToMany", args);
		result = multiplicityFinder.findRelationshipMultiplicity(getter);
		assertEquals(Multiplicity.OneToMany, result);

		getter = TestClass.class.getDeclaredMethod("getManyToMany", args);
		result = multiplicityFinder.findRelationshipMultiplicity(getter);
		assertEquals(Multiplicity.ManyToMany, result);

		getter = TestClass.class.getDeclaredMethod("getManyToOne", args);
		result = multiplicityFinder.findRelationshipMultiplicity(getter);
		assertEquals(Multiplicity.OneToOne, result);

	}

	private static class TestClass {

		@OneToOne
		private TestClass oneToOne;
		@OneToMany
		private TestClass oneToMany;

		@ManyToMany
		private TestClass manyToMany;

		@ManyToOne
		private TestClass manyToOne;

		@SuppressWarnings("unused")
		private TestClass testClass;

		@OneToOne
		public TestClass getOneToOne() {
			return oneToOne;
		}

		@OneToMany
		public TestClass getOneToMany() {
			return oneToMany;
		}

		@ManyToMany
		public TestClass getManyToMany() {
			return manyToMany;
		}

		@ManyToOne
		public TestClass getManyToOne() {
			return manyToOne;
		}
	}
}
