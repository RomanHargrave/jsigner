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

public class EdgeLabel extends AbstractLabel<Edge> {

    public static enum Placement {
        MID, ANCHOR1, ANCHOR2;
    }

    AbstractBox<?> anchor1;
    AbstractBox<?> anchor2;

    double offset = 0d;

    Placement placement = Placement.MID;

    public EdgeLabel(MetaType type, String name) {
        super(type, name);
    }

    @Override
    public void accept(AbstractVisitor visitor) {
        visitor.in(this);
        visitor.out(this);
    }

    public AbstractBox<?> getAnchor1() {
        return anchor1;
    }

    public AbstractBox<?> getAnchor2() {
        return anchor2;
    }

    public double getOffset() {
        return offset;
    }

    public Placement getPlacement() {
        return placement;
    }

    @Override
    public Pt getPos() {
        Pt[] ports = anchor1.getPorts(anchor2);
        switch (placement) {
        case MID:
            Pt mid = ports[0].plus(ports[1].minus(ports[0]).mulBy(0.5d));
            double t = anchor1.tan(anchor2);
            if (t != 0d) {
                mid.incBy(offset / t, offset);
            }
            mid.decBy(getSize().x / 2d, getSize().y / 2d);
            return mid;
        case ANCHOR1:
            Pt p1 = ports[0].incBy(offset * anchor1.cos(anchor2), offset * anchor1.sin(anchor2));
            p1.decBy(-2d, getSize().y / 2d); // getSize().x/2d
            return p1;
        case ANCHOR2:
            Pt p2 = ports[1].incBy(offset * anchor2.cos(anchor1), offset * anchor2.sin(anchor1));
            p2.decBy(-2d, getSize().y / 2d);
            return p2;
        }
        return null;
    }

    public void setAnchor1(AbstractBox<?> anchor1) {
        this.anchor1 = anchor1;
    }

    public void setAnchor2(AbstractBox<?> anchor2) {
        this.anchor2 = anchor2;
    }

    public void setOffset(double offset) {
        this.offset = offset;
    }

    public void setPlacement(Placement placement) {
        this.placement = placement;
    }

    @Override
    public String toString() {
        return getName() + ":" + getType() + "@" + getOffset();
    }

}
