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

import org.modsl.core.agt.model.AbstractBox;
import org.modsl.core.agt.model.Edge;
import org.modsl.core.agt.model.Graph;
import org.modsl.core.agt.model.Node;

/**
 * Pretty printer for abstract graph trees
 * 
 * @author avishnyakov
 * 
 * @param <T> meta type class
 */
public class ToStringVisitor extends AbstractVisitor {

    /**
     * Indentation unit
     */
    protected final static String IND_UNIT = "  ";

    /**
     * Collects output
     */
    protected StringBuilder sb = new StringBuilder();

    /**
     * Current indentation
     */
    protected String indentation = "";

    @Override
    public void in(Graph node) {
        sb.append("\n").append(indentation).append(node.toString()).append(" {");
        indentation += IND_UNIT;
    }

    @Override
    public void out(Graph node) {
        indentation = indentation.substring(0, indentation.length() - IND_UNIT.length());
        sb.append("\n").append(indentation).append("}");
    }

    @Override
    public void in(Node node) {
        sb.append("\n").append(indentation).append(node.toString());
        indentation += IND_UNIT;
    }

    @Override
    public void out(Node node) {
        indentation = indentation.substring(0, indentation.length() - IND_UNIT.length());
    }

    @Override
    public void in(Edge edge) {
        sb.append("\n").append(indentation).append(edge.toString());
    }

    @Override
    public String toString() {
        return sb.toString();
    }

    /**
     * Render given AGT as a string
     * @param box
     * @return string
     */
    public String toString(AbstractBox<?> box) {
        box.accept(this);
        return toString();
    }

}
