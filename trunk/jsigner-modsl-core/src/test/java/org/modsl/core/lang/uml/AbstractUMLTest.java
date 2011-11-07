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

package org.modsl.core.lang.uml;

import org.modsl.core.AbstractModSLTest;
import org.modsl.core.render.StyleLoader;

public abstract class AbstractUMLTest extends AbstractModSLTest {

    protected static UMLTranslator translator = new UMLTranslator();
    
    static {
        StyleLoader stl = new StyleLoader();
        stl.load("cfg/uml:cfg", "uml", UMLMetaType.class);
    }

}