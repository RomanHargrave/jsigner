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

package org.modsl.core.lang.uml.render;

import java.util.Arrays;
import java.util.List;

import org.modsl.core.agt.model.MetaType;
import org.modsl.core.agt.model.Node;
import org.modsl.core.agt.model.NodeLabel;
import org.modsl.core.agt.render.NodeRenderVisitor;
import org.modsl.core.lang.uml.UMLMetaType;
import org.modsl.core.render.Style;

public class ClassNodeRenderVisitor extends NodeRenderVisitor {

    @Override
    public void apply(Node node) {

        super.apply(node);

        Style s = node.getType().getStyle();
        g.setColor(s.getStrokeColor());

        int x1 = (int) node.getPos().x;
        int x2 = (int) (node.getPos().x + node.getSize().x);
        int y = (int) (node.getPos().y + s.getExtHeight(1) + 1);
        g.drawLine(x1, y, x2, y);

        List<NodeLabel> mls = node.getLabels(Arrays.asList(new MetaType[] { UMLMetaType.CLASS_METHOD_NODE_LABEL,
                UMLMetaType.CLASS_ABSTRACT_METHOD_NODE_LABEL, UMLMetaType.CLASS_STATIC_METHOD_NODE_LABEL }));

        List<NodeLabel> vls = node.getLabels(Arrays.asList(new MetaType[] { UMLMetaType.CLASS_VAR_NODE_LABEL,
                UMLMetaType.CLASS_STATIC_VAR_NODE_LABEL }));

        if (!vls.isEmpty() && !mls.isEmpty()) {
            y += UMLMetaType.CLASS_VAR_NODE_LABEL.getStyle().getExtHeight(vls.size());
            g.drawLine(x1, y, x2, y);
        }

    }

}
