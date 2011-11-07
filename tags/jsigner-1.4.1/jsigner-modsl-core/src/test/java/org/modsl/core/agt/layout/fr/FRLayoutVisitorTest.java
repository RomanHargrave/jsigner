/**
 * Copyright 2008 Andrew Vishnyakov <avishn@gmail.com>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package org.modsl.core.agt.layout.fr;

import static org.junit.Assert.assertEquals;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.modsl.core.TMetaType;
import org.modsl.core.agt.model.Edge;
import org.modsl.core.agt.model.Graph;
import org.modsl.core.agt.model.Node;
import org.modsl.core.agt.visitor.AbstractVisitor;

public class FRLayoutVisitorTest {

    Logger log = Logger.getLogger(getClass());

    Graph graph;
    Node n1;
    Node n2;
    Edge e1_2;

    AbstractVisitor layout = new FRLayoutVisitor().type(TMetaType.GRAPH);

    @Before
    public void setUp() throws Exception {

        graph = new Graph(TMetaType.GRAPH, "g");
        n1 = new Node(TMetaType.NODE, "n1");
        n2 = new Node(TMetaType.NODE, "n2");
        e1_2 = new Edge(TMetaType.EDGE, n1, n2);

        graph.setReqSize(100d, 100d);
        graph.add(n1);
        n1.setSize(10d, 10d);
        graph.add(n2);
        n2.setSize(10d, 10d);
        graph.add(e1_2);

    }

    @Test
    public void twoBit() {
        layout.in(graph);
        assertEquals(60d, n1.getCtrDelta(n2).len(), 10d);
        // log.debug(new ToStringVisitor().toString(graph));
    }

}
