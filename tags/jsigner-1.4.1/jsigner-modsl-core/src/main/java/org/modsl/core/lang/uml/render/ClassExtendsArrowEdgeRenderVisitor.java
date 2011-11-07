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

import static java.lang.Math.PI;

import org.modsl.core.agt.model.Edge;
import org.modsl.core.agt.model.Pt;
import org.modsl.core.lang.uml.UMLMetaType;

public class ClassExtendsArrowEdgeRenderVisitor extends AbstractArrowEdgeRenderVisitor {

    @Override
    public void apply(Edge e) {
        g.setStroke(NORMAL_STROKE);
        draw(e, e.getNode1Port(), getMidPoint(e));
        g.setStroke(NORMAL_STROKE);
        drawSides(e);
        g.setStroke(NORMAL_STROKE);
        drawButt(e);
    }

    void drawButt(Edge e) {
        Pt offl = getOffsetPoint2(e, e.angle2() - getArrowAngle() / 2d, getArrowLength());
        Pt offr = getOffsetPoint2(e, e.angle2() + getArrowAngle() / 2d, getArrowLength());
        g.drawLine((int) offl.x, (int) offl.y, (int) offr.x, (int) offr.y);
    }

    Pt getMidPoint(Edge e) {
        return getOffsetPoint2(e, e.angle2(), getArrowLength() * Math.cos(getArrowAngle() / 2d));
    }

    @Override
    protected double getArrowAngle() {
        return PI / 3.5d;
    }

    @Override
    protected double getArrowLength() {
        return UMLMetaType.COLLAB_EDGE.getStyle().getArrowLength();
    }

}
