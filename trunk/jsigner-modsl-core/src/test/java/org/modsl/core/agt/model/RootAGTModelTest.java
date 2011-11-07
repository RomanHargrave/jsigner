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

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class RootAGTModelTest extends AbstractAGTModelTest {

    @Test
    public void recalcSize() {
        graph.recalcSize();
        assertEquals(1200d, graph.getSize().x, Pt.EPSILON);
        assertEquals(1200d, graph.getSize().y, Pt.EPSILON);
    }

    @Test
    public void getMaxPt() {
        assertEquals(1200d, graph.maxPt().x, Pt.EPSILON);
        assertEquals(1200d, graph.maxPt().y, Pt.EPSILON);
    }

    @Test
    public void getMinPt() {
        assertEquals(0d, graph.minPt().x, Pt.EPSILON);
        assertEquals(0d, graph.minPt().y, Pt.EPSILON);
    }

    @Test
    public void rescale() {
        graph.rescale(new Pt(1000d, 1000d));
        assertEquals(1000d, graph.getSize().x, Pt.EPSILON);
        assertEquals(1000d, graph.getSize().y, Pt.EPSILON);
        assertEquals(800d, n2.getPos().x, 7d);
        assertEquals(graph.getTopPadding(), n2.getPos().y, 2d);
        assertEquals(0d, n4.getPos().x, 6d);
        assertEquals(800d-graph.getBottomPadding(), n4.getPos().y, 3d);
        assertEquals(400d, n5.getPos().x, 5d);
        assertEquals(880d-graph.getBottomPadding(), n5.getPos().y, 3d);
        assertEquals(1000000d, graph.getArea(), 1d);
    }

    @Test
    public void getArea() {
        graph.recalcSize();
        assertEquals(1200d * 1200d, graph.getArea(), Pt.EPSILON);
    }

}
