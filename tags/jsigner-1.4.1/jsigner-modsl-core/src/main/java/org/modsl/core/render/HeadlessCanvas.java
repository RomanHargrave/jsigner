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

import java.awt.Canvas;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import org.apache.log4j.Logger;

/**
 * This class is used to get access to font metrics in headless (servlet)
 * environment
 * 
 * @author avishnyakov
 */
public class HeadlessCanvas extends Canvas {

    static final long serialVersionUID = 1L;
    static HeadlessCanvas singleton = new HeadlessCanvas();

    static {
        System.setProperty("java.awt.headless", "true");
    }

    public static synchronized FontMetrics getMetrics(String name, int size, int style) {
        singleton.name = name;
        singleton.size = size;
        singleton.style = style;
        singleton.paint(null);
        return singleton.fontMetrics;
    }

    Logger log = Logger.getLogger(getClass());
    
    String name;
    int size;
    int style;

    FontMetrics fontMetrics;

    public void paint(Graphics g) {
        fontMetrics = getFontMetrics(new Font(name, style, size));
        if (log.isDebugEnabled()) {
            log.debug(this.fontMetrics);
        }
    }

}
