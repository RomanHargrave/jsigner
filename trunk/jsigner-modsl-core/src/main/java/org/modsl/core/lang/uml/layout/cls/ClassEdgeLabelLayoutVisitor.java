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

import org.modsl.core.agt.layout.AbstractNonConfigurableLayoutVisitor;
import org.modsl.core.agt.model.Edge;
import org.modsl.core.agt.model.EdgeLabel;
import org.modsl.core.lang.uml.UMLMetaType;

/**
 * Edge label placement
 * @author avishnyakov
 */
public class ClassEdgeLabelLayoutVisitor extends AbstractNonConfigurableLayoutVisitor {

    @Override
    public void apply(Edge edge) {

        double arrowLength = UMLMetaType.CLASS_MULTIPLICITY_FROM_EDGE_LABEL.getStyle().getArrowLength();

        EdgeLabel from = edge.getLabels(UMLMetaType.CLASS_MULTIPLICITY_FROM_EDGE_LABEL).get(0);

        from.setPlacement(EdgeLabel.Placement.ANCHOR1);
        from.setAnchor1(edge.getNode1());
        from.setAnchor2(edge.getFirstBend());
        from.setOffset(arrowLength * 2d);

        EdgeLabel to = edge.getLabels(UMLMetaType.CLASS_MULTIPLICITY_TO_EDGE_LABEL).get(0);

        to.setPlacement(EdgeLabel.Placement.ANCHOR2);
        to.setAnchor1(edge.getLastBend());
        to.setAnchor2(edge.getNode2());
        to.setOffset(arrowLength * 1.75d);

    }

}
