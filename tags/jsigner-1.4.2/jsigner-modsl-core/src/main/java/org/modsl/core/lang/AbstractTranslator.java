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

package org.modsl.core.lang;

import java.awt.image.BufferedImage;

import org.antlr.runtime.RecognitionException;
import org.apache.log4j.Logger;
import org.modsl.core.agt.model.Graph;

public abstract class AbstractTranslator {
    
    protected Logger log = Logger.getLogger(getClass());

    public BufferedImage translate(String str) throws RecognitionException {
        Graph graph = parse(str);
        layout(graph);
        return render(graph);
    }

    protected abstract Graph layout(Graph graph);

    public abstract Graph parse(String str) throws RecognitionException;

    protected abstract BufferedImage render(Graph graph);

}