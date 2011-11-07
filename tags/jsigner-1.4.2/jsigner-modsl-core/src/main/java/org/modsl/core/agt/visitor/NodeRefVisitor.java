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

package org.modsl.core.agt.visitor;

import org.modsl.core.agt.model.AbstractElement;
import org.modsl.core.agt.model.Edge;
import org.modsl.core.agt.model.Graph;
import org.modsl.core.agt.model.Node;

/**
 * Resolves node names to node reference on all edges
 * 
 * @author avishnyakov
 * 
 * @param <T> meta type class
 */
public class NodeRefVisitor extends AbstractVisitor {

    @Override
    public void in(Edge edge) {
        edge.setNode1(resolveNode(edge.getParent(), edge.getNode1(), edge.getNode1Name()));
        edge.setNode2(resolveNode(edge.getParent(), edge.getNode2(), edge.getNode2Name()));
    }

    /**
     * If node reference is null will resolve nodeName to node reference,
     * otherwise just returns existing node reference
     * @param node
     * @param nodeName
     * @return node
     */
    private Node resolveNode(AbstractElement<?> parent, Node node, String nodeName) {
        if (node == null) {
            Node n = ((Graph) parent).getNode(nodeName);
            if (n == null) {
                throw new InvalidNodeNameException(nodeName);
            }
            return n;
        }
        return node;
    }

}
