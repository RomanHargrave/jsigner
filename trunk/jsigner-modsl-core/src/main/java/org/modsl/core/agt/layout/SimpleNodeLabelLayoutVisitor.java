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

package org.modsl.core.agt.layout;

import org.modsl.core.agt.model.Node;
import org.modsl.core.agt.model.NodeLabel;
import org.modsl.core.render.Style;

/**
 * Does simple node size calculation based on node labels' height and width.
 * @author avishnyakov
 */
public class SimpleNodeLabelLayoutVisitor extends AbstractNonConfigurableLayoutVisitor {

    @Override
    public void apply(Node node) {
        if (node.getLabels().isEmpty()) {
            Style st = node.getType().getStyle();
            node.setSize(st.getExtStringWidth(" "), st.getExtHeight(1));
        } else {
            NodeLabel label = node.getLabels().get(0);
            Style st = label.getType().getStyle();
            node.setSize(st.getExtStringWidth(label.getName()), st.getExtHeight(1));
            label.setOffset(st.getLeftPadding(), st.getExtPosition(0));
        }
    }

}
