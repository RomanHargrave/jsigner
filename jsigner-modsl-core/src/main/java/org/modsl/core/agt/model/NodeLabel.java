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

package org.modsl.core.agt.model;

import org.modsl.core.agt.visitor.AbstractVisitor;

public class NodeLabel extends AbstractLabel<Node> {

    Pt offset = new Pt(0d, 0d);

    public NodeLabel(MetaType type, String name) {
        super(type, name);
    }

    @Override
    public void accept(AbstractVisitor visitor) {
        visitor.in(this);
        visitor.out(this);
    }

    public Pt getOffset() {
        return offset;
    }

    @Override
    public Pt getPos() {
        return getParent().getPos().plus(offset);
    }

    public void setOffset(double x, double y) {
        this.offset.x = x;
        this.offset.y = y;
    }

    @Override
    public String toString() {
        return getName() + ":" + getType() + "@" + getOffset();
    }

}
