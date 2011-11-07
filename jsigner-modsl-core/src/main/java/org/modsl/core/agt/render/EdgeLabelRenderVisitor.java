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

import org.modsl.core.agt.model.EdgeLabel;
import org.modsl.core.render.Style;

public class EdgeLabelRenderVisitor extends AbstractRenderVisitor {

    @Override
    public void apply(EdgeLabel label) {
        
        Style s = label.getType().getStyle();

        g.setColor(s.getFillColor());
        g.fillRect((int) label.getPos().x, (int) label.getPos().y, (int) label.getSize().x, (int) label.getSize().y);

        g.setFont(s.getFont());
        g.setColor(s.getFontColor());
        g.drawString(label.getName(), (float) label.getTextPos().x, (float) label.getTextPos().y);
        
    }

}
