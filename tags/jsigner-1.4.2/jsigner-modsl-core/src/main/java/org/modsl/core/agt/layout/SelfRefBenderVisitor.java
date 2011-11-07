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

import java.util.List;

import org.modsl.core.agt.model.Bend;
import org.modsl.core.agt.model.Edge;
import org.modsl.core.agt.model.Node;
import org.modsl.core.agt.model.Pt;

public class SelfRefBenderVisitor extends AbstractNonConfigurableLayoutVisitor {

    protected double xOffset;

    @Override
    public void apply(Node n) {
        List<Edge> se = n.getSelfEdges();
        if (se.size() > 0) {
            double yInc = Math.min(xOffset * 0.6d, n.getSize().y / (se.size() * 2d - 1d));
            double y = (n.getSize().y - yInc * (se.size() * 2d - 1d)) / 2d;
            for (Edge e : se) {
                Pt rt = n.getPos().plus(n.getSize().x, 0d);
                e.getBends().add(new Bend(rt.plus(xOffset, y)));
                y += yInc;
                e.getBends().add(new Bend(rt.plus(xOffset, y)));
                y += yInc;
            }
        }
    }

    public SelfRefBenderVisitor xOffset(double xOffset) {
        this.xOffset = xOffset;
        return this;
    }

}
