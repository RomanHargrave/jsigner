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

package org.modsl.core.lang.basic;

import java.util.LinkedList;
import java.util.List;

import org.antlr.runtime.Token;
import org.modsl.core.agt.model.Edge;
import org.modsl.core.agt.model.Graph;
import org.modsl.core.agt.model.Node;
import org.modsl.core.agt.model.NodeLabel;
import org.modsl.core.lang.ElementFactory;

/**
 * Responsible for supporting the grammar when new abstract graph tree elements
 * need to be instantiated.
 * @author AVishnyakov
 */
public class BasicFactory implements ElementFactory {

    /**
     * Create list of edges given list of edge tokens
     * @param parent parent node the edges belong to
     * @param tokens edge tokens
     * @return list of edges
     */
    public List<Edge> createEdges(Graph parent, List<Token> tokens) {

        List<Edge> es = new LinkedList<Edge>();

        Node n1 = null, n2 = null;
        Token t1, t2;

        for (int i = 0; i < tokens.size() - 1; i++) {

            t1 = tokens.get(i);
            t2 = tokens.get(i + 1);

            if (i == 0) {
                n1 = createNodeIfDoesntExist(parent, t1);
            }
            n2 = createNodeIfDoesntExist(parent, t2);

            Edge e = new Edge(BasicMetaType.EDGE, n1, n2);
            parent.add(e);
            es.add(e);

            n1 = n2; // << shift

        }

        return es;

    }

    /**
     * Create node from token
     * @param token node token
     * @return new node
     */
    public Node createNode(Graph parent, Token token) {
        Node n = new Node(BasicMetaType.NODE, token.getText());
        n.addLabel(new NodeLabel(BasicMetaType.NODE_LABEL, token.getText()));
        parent.add(n);
        return n;
    }

    /**
     * Will create node by given name if the node with such name doesn't exist
     * yet
     * @param parent parent node
     * @param token new node token
     * @return node
     */
    private Node createNodeIfDoesntExist(Graph parent, Token token) {
        Node n = parent.getNode(token.getText());
        if (n == null) {
            return createNode(parent, token);
        } else {
            return n;
        }
    }

    public Graph createGraph() {
        return new Graph(BasicMetaType.GRAPH);
    }

}
