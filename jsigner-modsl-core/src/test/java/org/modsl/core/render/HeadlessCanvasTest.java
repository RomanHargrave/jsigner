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

package org.modsl.core.render;

import static org.junit.Assert.assertTrue;

import java.awt.Font;

import org.junit.Test;
import org.modsl.core.AbstractModSLTest;

public class HeadlessCanvasTest extends AbstractModSLTest {

    @Test
    public void fontMetrics() {
        assertTrue(HeadlessCanvas.getMetrics("Serif", 12, Font.PLAIN).stringWidth("test") > 17);
        assertTrue(HeadlessCanvas.getMetrics("Serif", 12, Font.BOLD).stringWidth("test") > 18);
    }
    
}