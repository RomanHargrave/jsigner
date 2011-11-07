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

public class UMLClassLoopTranslatorTest extends AbstractUMLTest {

    protected static ImageCollector ic = new ImageCollector("etc/png-out", "uml_loop");

    @Test
    public void processSelfRef2() throws Exception {
        ic.collect("self_ref2", translator.translate("class diagram A { class A { 1->1(B); } class B { 1->1(A); } }"));
    }

    @Test
    public void processSelfRef2b() throws Exception {
        ic.collect("self_ref2b", translator.translate("class diagram A { class A { 1->1(B); 1->*(B); } class B { 1->1(A); } }"));
    }

    @Test
    public void processSelfRef3() throws Exception {
        ic.collect("self_ref3", translator
                .translate("class diagram A { class A { 1->1(B); } class B { 1->1(C); } class C { 1->1(A); } }"));
    }

    @Test
    public void processSelfRef4() throws Exception {
        ic
                .collect(
                        "self_ref4",
                        translator
                                .translate("class diagram A { class A { 1->1(B); } class B { 1->1(C); } class C { 1->1(D); } class D { 1->1(A); } }"));
    }

}
