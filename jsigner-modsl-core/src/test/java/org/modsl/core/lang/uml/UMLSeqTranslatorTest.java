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

import org.junit.Test;
import org.modsl.core.ImageCollector;

public class UMLSeqTranslatorTest extends AbstractUMLTest {

    protected static ImageCollector ic = new ImageCollector("etc/png-out", "uml_seq");

    @Test
    public void process0() throws Exception {
        ic.collect("seq0", translator.translate("sequence diagram seq0 { }"));
    }

    @Test
    public void process25() throws Exception {
        ic.collect("seq25", translator.translate("seq seq25 { c1:o1->c2:o2.m2()->c3:o3.m3()->c1:o1.m4(); }"));
    }

}
