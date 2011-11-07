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

package org.modsl.core.render;

import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.awt.GradientPaint;

import org.junit.Test;
import org.modsl.core.agt.model.Pt;

public class GradientTest {

    @Test
    public void testGradientPaint() {
        Gradient gr = new Gradient(new int[] { 0, 0, 5, 6, 7, 8, 100, 100, 55, 56, 57, 58 });
        GradientPaint grp = gr.getGradientPaint(new Pt(100, 150), new Pt(200, 250));
        assertEquals(100d, grp.getPoint1().getX(), 1d);
        assertEquals(150d, grp.getPoint1().getY(), 1d);
        assertEquals(new Color(5, 6, 7, 8), grp.getColor1());
        assertEquals(300d, grp.getPoint2().getX(), 1d);
        assertEquals(400d, grp.getPoint2().getY(), 1d);
        assertEquals(new Color(55, 56, 57, 58), grp.getColor2());
    }

}
