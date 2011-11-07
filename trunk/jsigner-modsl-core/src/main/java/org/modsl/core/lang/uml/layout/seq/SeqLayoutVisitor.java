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

package org.modsl.core.lang.uml.layout.seq;

import org.modsl.core.agt.layout.AbstractLayoutVisitor;
import org.modsl.core.agt.model.Graph;
import org.modsl.core.agt.model.Node;

/**
 * Layout algorithm for seq diagram 
 * @author avishnyakov
 */
public class SeqLayoutVisitor extends AbstractLayoutVisitor {

    static final double X_SEP = 25;

    @Override
    public void apply(Graph graph) {
        double offset = 0d;
        for (int i = 0; i < graph.getNodes().size(); i++) {
            Node n = graph.getNode(i);
            n.getPos().x = offset;
            offset = n.getPos().x + n.getSize().x + X_SEP;
        }
    }

}
