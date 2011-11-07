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

import java.awt.Graphics2D;

import org.modsl.core.agt.visitor.AbstractMetaTypeVisitor;

public abstract class AbstractRenderVisitor extends AbstractMetaTypeVisitor {

    protected Graphics2D g;
    protected int width, height;

    public AbstractRenderVisitor graphics(Graphics2D g, int width, int height) {
        this.g = g;
        this.width = width;
        this.height = height;
        return this;
    }
}
