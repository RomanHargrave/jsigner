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

import java.awt.Color;
import java.awt.GradientPaint;

import org.modsl.core.agt.model.Pt;

/**
 * Gradient fill
 * @author avishnyakov
 */
public class Gradient {

    Color startColor, endColor;
    Pt start, end;

    public Gradient(int[] parameters) {
        if (parameters.length == 10) { // no alpha
            start = new Pt(parameters[0], parameters[1]);
            startColor = new Color(parameters[2], parameters[3], parameters[4]);
            end = new Pt(parameters[5], parameters[6]);
            endColor = new Color(parameters[7], parameters[8], parameters[9]);
        } else { // both have alpha
            start = new Pt(parameters[0], parameters[1]);
            startColor = new Color(parameters[2], parameters[3], parameters[4], parameters[5]);
            end = new Pt(parameters[6], parameters[7]);
            endColor = new Color(parameters[8], parameters[9], parameters[10], parameters[11]);
        }
    }

    /**
     * Return gradient paint calculated from (start, end) percentages (0-100%) for the given box of (pos, size)
     * @param pos
     * @param size
     * @return
     */
    public GradientPaint getGradientPaint(Pt pos, Pt size) {
        int x1 = (int) (pos.x + start.x / 100d * size.x);
        int y1 = (int) (pos.y + start.y / 100d * size.y);
        int x2 = (int) (pos.x + end.x / 100d * size.x);
        int y2 = (int) (pos.y + end.y / 100d * size.y);
        return new GradientPaint(x1, y1, startColor, x2, y2, endColor);
    }

}
