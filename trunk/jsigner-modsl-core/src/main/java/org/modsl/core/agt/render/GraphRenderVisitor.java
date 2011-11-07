/**
 * Copyright 2008 Andrew Vishnyakov <avishn@gmail.com>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */

package org.modsl.core.agt.render;

import java.awt.RenderingHints;

import org.modsl.core.agt.model.Graph;
import org.modsl.core.render.Style;

public class GraphRenderVisitor extends AbstractRenderVisitor {

    @Override
    public void apply(Graph graph) {

        Style s = graph.getType().getStyle();

        RenderingHints rh = new RenderingHints(null);
        rh.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        rh.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        rh.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        g.setRenderingHints(rh);
        g.setColor(s.getFillColor());
        g.fillRect(0, 0, width, height);

        g.setFont(s.getFont());
        g.setColor(s.getFontColor());

        g.drawString("http://www.modsl.org", s.getLeftPadding(), height - s.getExtHeight(1) + s.getExtBaseline(0));

    }

}
