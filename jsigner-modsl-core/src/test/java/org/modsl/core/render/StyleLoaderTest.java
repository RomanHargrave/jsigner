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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.Font;

import org.junit.Test;
import org.modsl.core.AbstractModSLTest;
import org.modsl.core.TMetaType;

public class StyleLoaderTest extends AbstractModSLTest {

    StyleLoader stl = new StyleLoader();

    public StyleLoaderTest() {
        stl.load("test/props/style", "demo", TMetaType.class);
    }

    @Test
    public void fontName() {
        assertEquals("Tahoma", TMetaType.GRAPH.getStyle().getFontName());
        assertEquals("Tahoma", TMetaType.NODE.getStyle().getFontName());
        assertEquals("Tahoma", TMetaType.EDGE.getStyle().getFontName());
    }

    @Test
    public void fontSize() {
        assertEquals(14, TMetaType.GRAPH.getStyle().getFontSize());
        assertEquals(12, TMetaType.NODE.getStyle().getFontSize());
        assertEquals(12, TMetaType.EDGE.getStyle().getFontSize());
    }

    @Test
    public void fontStyle() {
        assertEquals(Font.PLAIN, TMetaType.GRAPH.getStyle().getFontStyle());
        assertEquals(Font.BOLD | Font.ITALIC, TMetaType.NODE.getStyle().getFontStyle());
        assertEquals(Font.BOLD, TMetaType.EDGE.getStyle().getFontStyle());
        assertTrue(TMetaType.EDGE.getStyle().isUnderline());
    }

    @Test
    public void fontColor() {
        assertEquals(255, TMetaType.GRAPH.getStyle().getFillColor().getAlpha());
        assertEquals(255, TMetaType.GRAPH.getStyle().getFillColor().getRed());
        assertEquals(255, TMetaType.GRAPH.getStyle().getFillColor().getBlue());
        assertEquals(255, TMetaType.GRAPH.getStyle().getFillColor().getGreen());
        assertEquals(200, TMetaType.NODE.getStyle().getFillColor().getAlpha());
        assertEquals(255, TMetaType.NODE.getStyle().getFillColor().getRed());
        assertEquals(255, TMetaType.NODE.getStyle().getFillColor().getBlue());
        assertEquals(255, TMetaType.NODE.getStyle().getFillColor().getGreen());
    }

}
