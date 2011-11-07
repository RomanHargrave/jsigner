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

package org.modsl.core.lang.basic;

import java.io.IOException;

import org.antlr.runtime.RecognitionException;
import org.junit.Test;
import org.modsl.core.ImageCollector;
import org.modsl.core.render.StyleLoader;

public class BasicTranslatorTest extends AbstractBasicTest {

    static ImageCollector ic = new ImageCollector("etc/png-out", "basic");

    static {
        StyleLoader stl = new StyleLoader();
        stl.load("cfg/basic:cfg", "basic", BasicMetaType.class);
    }

    @Test
    public void p1() throws RecognitionException, IOException {
        ic.collect("g1", translator.translate("graph g1 (width:400, height:320) "
                + "{ n1->n2; n1->n3->n4->n5; n2->n3; n5->n8->n9; n9->n4; n8-> n4; }"));
    }

    @Test
    public void p2() throws RecognitionException, IOException {
        ic.collect("g2", translator.translate("graph g2 (width:400, height:320)" + " { n1->n2->n3->n4->n5->n6->n7->n8; }"));
    }

    @Test
    public void p3() throws RecognitionException, IOException {
        ic.collect("g3", translator.translate("graph g3 (width:400, height:320) "
                + "{ n1->n2->n3->n4->n5->n6->n7->n8; n5->n1->n3; n2->n4->n6->n8->n5->n2; "
                + "n3->n5->n7; n6->n1->n4->n8; n6->n2->n8->n1->n7; n4->n7->n2; n8->n3->n6; n3->n7; }"));
    }

    @Test
    public void p4() throws RecognitionException, IOException {
        ic.collect("g4", translator.translate("graph g4 (width:500, height:420) "
                + "{ n1->n2; n1->n3; n2->n4; n2->n5; n3->n6; n3->n7; n6->n7; n1->n6; }"));
    }

    @Test
    public void p5() throws RecognitionException, IOException {
        ic.collect("g5", translator.translate("graph g4 (width:500, height:420) "
                + "{ n1->n2->n3->n1; n2->n4->n5->n2; n3->n5->n6->n3; " + "n4->n7->n8->n4; n5->n8->n9->n5; n6->n9->n10->n6; }"));
    }

    @Test
    public void p6int() throws RecognitionException, IOException {
        ic.collect("g6int", translator.translate("graph g6int (width:400, height:320) "
                + "{ À->ÿ; }"));
    }

}
