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

import static java.lang.Math.PI;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class EdgeAGTModelTest extends AbstractAGTModelTest {

    @Test
    public void angle() {
        assertEquals(PI / 4d, graph.getEdge(0).angle2(), Pt.EPSILON); // 1-3
        assertEquals(PI * 3d / 4d, graph.getEdge(1).angle2(), Pt.EPSILON); // 2-4
        // sign
        assertTrue(graph.getEdge(2).angle2() > PI); // 3-7
        assertTrue(graph.getEdge(2).angle2() < (3d / 2d) * PI);
        assertTrue(graph.getEdge(3).angle2() > 3d / 2d * PI); // 4-6
        assertTrue(graph.getEdge(3).angle2() < 2d * PI);
    }
    
}
