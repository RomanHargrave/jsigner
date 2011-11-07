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

import java.io.IOException;

import org.antlr.runtime.RecognitionException;
import org.junit.Test;
import org.modsl.core.ImageCollector;
import org.modsl.core.util.Utils;

public class UMLCollabTranslatorTest extends AbstractUMLTest {

    protected static ImageCollector ic = new ImageCollector("etc/png-out", "uml_collab");

    @Test
    public void process0() throws Exception {
        ic.collect("coll0", translator.translate("collab coll0 { }"));
    }

    @Test
    public void process25() throws Exception {
        ic.collect("coll25", translator.translate("collab coll25 { c1:o1->c2:o2.m2()->c3:o3.m3()->c1:o1.m4(); }"));
    }

    @Test
    public void process35() throws Exception {
        ic.collect("coll35", translator.translate("collab coll35 { o1->o2.m2()->o3.m3(); o1->o4.m4()->o3.m3(); }"));
    }

    @Test
    public void process7() throws Exception {
        ic.collect("coll7", translator
                .translate("collab coll7 { ooo1->ooo2.m2()->ooo3.m3()->ooo4.m4()->ooo5.m5()->ooo6.m6()->ooo7.m7(); }"));
    }

    @Test
    public void process20() throws Exception {
        ic.collect("coll20", translator.translate("collab coll20 { o1->o2.m2(); }"));
    }

    @Test
    public void process30() throws Exception {
        ic.collect("coll30", translator.translate("collab coll30 { o1->o2.m2()->o3.m3(); o4->o3.m3(); }"));
    }

    @Test
    public void process2() throws RecognitionException, IOException {
        ic.collect("coll2", translator.translate("collab coll2 { :o1->:o2.m2(); :o1->:o3.m3(); :o2->:o4.m4(); :o2->:o5.m5();"
                + " :o3->:o6.m6(); :o3->:o7.m7(); :o6->:o7.m8(); :o1->:o6.m9(); :o6->:o2.m10(); :o3->o2.m11(); }"));
    }

    @Test
    public void process3() throws RecognitionException, IOException {
        ic.collect("coll3", translator.translate(Utils.fromFile("samples/uml/collab3.modsl")));
    }

    @Test
    public void process1f() throws RecognitionException, IOException {
        ic.collect("coll1", translator.translate(Utils.fromFile("samples/uml/collab1.modsl")));
    }

}
