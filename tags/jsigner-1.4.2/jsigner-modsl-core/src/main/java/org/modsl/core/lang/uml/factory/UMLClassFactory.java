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

package org.modsl.core.lang.uml.factory;

import java.util.LinkedList;
import java.util.List;

import org.antlr.runtime.Token;
import org.modsl.core.agt.model.Edge;
import org.modsl.core.agt.model.EdgeLabel;
import org.modsl.core.agt.model.Graph;
import org.modsl.core.agt.model.Node;
import org.modsl.core.agt.model.NodeLabel;
import org.modsl.core.lang.uml.UMLMetaType;

/**
 * Responsible for supporting the grammar when new abstract graph tree elements
 * need to be instantiated.
 * @author AVishnyakov
 */
public class UMLClassFactory extends AbstractUMLFactory {

    Node createNode(Graph parent, Token token, List<Token> genericTokens, UMLMetaType nodeType, UMLMetaType labelType,
            List<NodeLabel> elements, List<Token> extendTokens, List<Token> implementTokens) {

        Node n = new Node(nodeType, token.getText());

        n.addLabel(new NodeLabel(labelType, token.getText()));
        n.addLabels(elements);
        parent.add(n);

        if (extendTokens != null) {
            for (Token etoken : extendTokens) {
                Edge e = new Edge(UMLMetaType.CLASS_EXTENDS_EDGE, n, etoken.getText());
                parent.add(e);
            }
        }

        if (implementTokens != null) {
            for (Token itoken : implementTokens) {
                Edge e = new Edge(UMLMetaType.CLASS_IMPLEMENTS_EDGE, n, itoken.getText());
                parent.add(e);
            }
        }

        return n;

    }

    public void createClassNode(Token abs, Graph parent, Token token, List<Token> genericTokens, List<NodeLabel> elements,
            List<Token> extendTokens, List<Token> implementTokens, LinkedList<String> aggs) {

        UMLMetaType mt = (abs == null) ? UMLMetaType.CLASS_CLASS_NODE : UMLMetaType.CLASS_ABSTRACT_CLASS_NODE;
        UMLMetaType mtl = (abs == null) ? UMLMetaType.CLASS_CLASS_NODE_LABEL : UMLMetaType.CLASS_ABSTRACT_CLASS_NODE_LABEL;

        Node n = createNode(parent, token, genericTokens, mt, mtl, elements, extendTokens, implementTokens);

        int i = 0;
        while (i < aggs.size()) {
            EdgeLabel fromLabel = new EdgeLabel(UMLMetaType.CLASS_MULTIPLICITY_FROM_EDGE_LABEL, aggs.get(i++));
            EdgeLabel toLabel = new EdgeLabel(UMLMetaType.CLASS_MULTIPLICITY_TO_EDGE_LABEL, aggs.get(i++));
            String node2Name = aggs.get(i++);
            Edge e = new Edge(UMLMetaType.CLASS_AGGREGATION_EDGE, n, node2Name);
            parent.add(e);
            e.addLabel(fromLabel);
            e.addLabel(toLabel);
        }

    }

    public void createInterfaceNode(Graph parent, Token token, List<Token> genericTokens, List<NodeLabel> elements,
            List<Token> extendTokens) {
        createNode(parent, token, genericTokens, UMLMetaType.CLASS_INTERFACE_NODE, UMLMetaType.CLASS_INTERFACE_NODE_LABEL,
                elements, extendTokens, null);
    }

    public NodeLabel createNodeElement(UMLMetaType type, String text) {
        return new NodeLabel(type, text);
    }

    public Graph createGraph() {
        return new Graph(UMLMetaType.CLASS_GRAPH);
    }

}
