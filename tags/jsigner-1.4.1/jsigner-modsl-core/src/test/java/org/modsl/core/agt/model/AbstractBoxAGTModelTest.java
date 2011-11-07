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
import static java.lang.Math.acos;
import static java.lang.Math.asin;
import static java.lang.Math.atan;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class AbstractBoxAGTModelTest extends AbstractAGTModelTest {

    @Test
    public void angle() {
        assertEquals(PI / 4d, n1.angle(n3), Pt.EPSILON); // 1-3
        assertEquals(PI * 3d / 4d, n2.angle(n4), Pt.EPSILON); // 2-4
        // sign
        assertTrue(n3.angle(n7) > PI); // 3-7
        assertTrue(n3.angle(n7) < (3d / 2d) * PI);
        assertTrue(n4.angle(n6) > 3d / 2d * PI); // 4-6
        assertTrue(n4.angle(n6) < 2d * PI);
    }

    @Test
    public void cos() {
        // values for pi/4 and pi*3/4
        assertEquals(PI / 4d, acos(n1.cos(n3)), Pt.EPSILON); // 1-3
        assertEquals(PI * 3d / 4d, acos(n2.cos(n4)), Pt.EPSILON); // 2-4
        // sign
        assertTrue(n3.cos(n7) < 0); // 3-7
        assertTrue(n4.cos(n6) > 0); // 4-6
    }

    @Test
    public void sin() {
        // values for pi/4 and pi*3/4
        assertEquals(PI / 4d, asin(n1.sin(n3)), Pt.EPSILON); // 1-3
        assertEquals(PI * 3d / 4d, asin(n2.sin(n4)) + PI / 2d, Pt.EPSILON); // 2-4
        // sign
        assertTrue(n3.sin(n7) < 0); // 3-7
        assertTrue(n4.sin(n6) < 0); // 4-6
    }

    @Test
    public void tan() {
        // values for pi/4 and pi*3/4
        assertEquals(PI / 4d, atan(n1.tan(n3)), Pt.EPSILON); // 1-3
        assertEquals(PI * 3d / 4d, atan(n2.tan(n4)) + PI, Pt.EPSILON); // 2-4
        // sign
        assertTrue(n3.tan(n7) > 0); // 3-7
        assertTrue(n4.tan(n6) < 0); // 4-6
    }

    @Test
    public void deltas() {
        assertEquals(new Pt(1095, 20), n1.getCtrDelta(n2));
        assertEquals(10d, n1.getPorts(n2)[0].x, 1d);
        assertEquals(5d, n1.getPorts(n2)[0].y, 1d);
        assertEquals(1000d, n1.getPorts(n2)[1].x, 1d);
        assertEquals(23d, n1.getPorts(n2)[1].y, 1d);
        assertEquals(-990d, n1.getPortDelta(n2).x, 1d);
        assertEquals(-18d, n1.getPortDelta(n2).y, 1d);
    }

}
