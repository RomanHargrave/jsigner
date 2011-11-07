/**
 * Copyright 2008 Andrew Vishnyakov <avishn@gmail.com>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */

package org.modsl.core.lang.basic;

import static org.junit.Assert.assertEquals;

import org.antlr.runtime.RecognitionException;
import org.junit.Test;
import org.modsl.core.agt.model.Graph;

public class BasicGrammarTest extends AbstractBasicTest {

	@Test
	public void graph() throws RecognitionException {
		Graph graph = translator.parse("graph g {}");
		assertEquals("g", graph.getName());
		assertEquals(null, graph.getParent());
		assertEquals(0, graph.getNodes().size());
	}

	@Test
	public void nodes() throws RecognitionException {
		Graph graph = translator.parse("graph g {\n stmt1; \n \"stmt2\"; \n }");
		assertEquals(2, graph.getNodes().size());
		assertEquals("stmt1", graph.getNodes().get(0).getName());
		assertEquals("stmt1", graph.getNode("stmt1").getName());
		assertEquals("\"stmt2\"", graph.getNodes().get(1).getName());
		assertEquals("\"stmt2\"", graph.getNode("\"stmt2\"").getName());
	}

	@Test
	public void edges() throws RecognitionException {
		Graph graph = translator.parse("graph g { n0; n1; n1->n2; n3->n4->n5; n6->n7; }");
		// log.debug(new ToStringVisitor().toString(root));
		assertEquals(8, graph.getNodes().size());
		assertEquals(4, graph.getEdges().size());
	}

}
