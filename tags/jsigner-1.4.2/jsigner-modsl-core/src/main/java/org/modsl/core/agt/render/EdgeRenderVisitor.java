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

package org.modsl.core.agt.render;

import java.awt.BasicStroke;

import org.modsl.core.agt.model.Bend;
import org.modsl.core.agt.model.Edge;
import org.modsl.core.agt.model.Pt;
import org.modsl.core.render.Style;

public class EdgeRenderVisitor extends AbstractRenderVisitor {

    protected static BasicStroke NORMAL_STROKE = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);

    protected static BasicStroke DASHED_STROKE = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f,
            new float[] { 10.0f, 5.0f }, 0.0f);

    @Override
    public void apply(Edge e) {
        g.setStroke(NORMAL_STROKE);
        draw(e, e.getNode1Port(), e.getNode2Port());
    }

    protected void draw(Edge e, Pt startPoint, Pt endPoint) {
        Style s = e.getType().getStyle();
        g.setColor(s.getStrokeColor());
        int cx = (int) startPoint.x;
        int cy = (int) startPoint.y;
        for (Bend b : e.getBends()) {
            int nx = (int) b.getPos().x;
            int ny = (int) b.getPos().y;
            g.drawLine(cx, cy, nx, ny);
            cx = nx;
            cy = ny;
        }
        g.drawLine(cx, cy, (int) endPoint.x, (int) endPoint.y);
    }

}
