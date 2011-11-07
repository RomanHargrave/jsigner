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

package org.modsl.core.lang.uml.layout.cls;

import static java.lang.Math.max;

import java.util.Arrays;
import java.util.List;

import org.modsl.core.agt.layout.SimpleNodeLabelLayoutVisitor;
import org.modsl.core.agt.model.MetaType;
import org.modsl.core.agt.model.Node;
import org.modsl.core.agt.model.NodeLabel;
import org.modsl.core.agt.model.Pt;
import org.modsl.core.lang.uml.UMLMetaType;
import org.modsl.core.render.Style;

/**
 * Node size calculation based on node's text height and width.
 * @author avishnyakov
 */
public class ClassNodeLayoutVisitor extends SimpleNodeLabelLayoutVisitor {

    @Override
    public void apply(Node node) {

        NodeLabel hl = getHeaderLabel(node);
        log.debug("Setting offset for " + hl);

        Style hs = hl.getType().getStyle();
        hl.setOffset(hs.getLeftPadding(), hs.getTopPadding());

        Pt nodeSize = new Pt(hs.getExtStringWidth(hl.getName()), hs.getExtHeight(1));

        // vars
        Style vs = UMLMetaType.CLASS_VAR_NODE_LABEL.getStyle();
        List<NodeLabel> vls = getVarLabels(node);

        double var_y = nodeSize.y;
        for (int i = 0; i < vls.size(); i++) {
            NodeLabel l = vls.get(i);
            l.setOffset(vs.getLeftPadding(), var_y + vs.getExtPosition(i));
            nodeSize.x = max(nodeSize.x, vs.getLeftPadding() + l.getSize().x + vs.getRightPadding());
            nodeSize.y = l.getOffset().y + vs.getFontHeight();
            log.debug("Setting offset for " + l);
        }

        // methods
        Style ms = UMLMetaType.CLASS_METHOD_NODE_LABEL.getStyle();
        List<NodeLabel> mls = getMethodLabels(node);

        double method_y;
        if (vls.size() > 0) {
            nodeSize.y += vs.getBottomPadding();
            method_y = nodeSize.y;
        } else {
            method_y = var_y;
        }

        for (int i = 0; i < mls.size(); i++) {
            NodeLabel l = mls.get(i);
            l.setOffset(ms.getLeftPadding(), method_y + ms.getExtPosition(i));
            nodeSize.x = max(nodeSize.x, ms.getLeftPadding() + l.getSize().x + ms.getRightPadding());
            nodeSize.y = l.getOffset().y + ms.getFontHeight();
            log.debug("Setting offset for " + l);
        }

        // final node size adjustments
        if (mls.size() > 0) {
            nodeSize.y += ms.getBottomPadding();
        }

        if (vls.size() == 0 && mls.size() == 0) {
            nodeSize.y += hs.getFontSize();
        }

        node.setSize(nodeSize.x, nodeSize.y);

    }

    List<NodeLabel> getMethodLabels(Node node) {
        return node.getLabels(Arrays.asList(new MetaType[] { UMLMetaType.CLASS_METHOD_NODE_LABEL,
                UMLMetaType.CLASS_ABSTRACT_METHOD_NODE_LABEL, UMLMetaType.CLASS_STATIC_METHOD_NODE_LABEL }));
    }

    List<NodeLabel> getVarLabels(Node node) {
        return node.getLabels(Arrays.asList(new MetaType[] { UMLMetaType.CLASS_VAR_NODE_LABEL,
                UMLMetaType.CLASS_STATIC_VAR_NODE_LABEL }));
    }

    NodeLabel getHeaderLabel(Node node) {
        return node.getLabels(
                Arrays.asList(new MetaType[] { UMLMetaType.CLASS_CLASS_NODE_LABEL, UMLMetaType.CLASS_ABSTRACT_CLASS_NODE_LABEL,
                        UMLMetaType.CLASS_INTERFACE_NODE_LABEL })).get(0);
    }

}
