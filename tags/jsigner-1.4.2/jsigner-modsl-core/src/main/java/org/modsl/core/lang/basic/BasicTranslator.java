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

package org.modsl.core.lang.basic;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.modsl.antlr.basic.BasicLexer;
import org.modsl.antlr.basic.BasicParser;
import org.modsl.core.agt.layout.SimpleNodeLabelLayoutVisitor;
import org.modsl.core.agt.layout.fr.FRLayoutVisitor;
import org.modsl.core.agt.model.Graph;
import org.modsl.core.agt.render.EdgeRenderVisitor;
import org.modsl.core.agt.render.GraphRenderVisitor;
import org.modsl.core.agt.render.NodeLabelRenderVisitor;
import org.modsl.core.agt.render.NodeRenderVisitor;
import org.modsl.core.lang.AbstractTranslator;

public class BasicTranslator extends AbstractTranslator {

    protected Graph layout(Graph graph) {
        
        graph.accept(new SimpleNodeLabelLayoutVisitor().type(BasicMetaType.NODE));
        graph.accept(new FRLayoutVisitor().type(BasicMetaType.GRAPH));
        
        graph.rescale();
        
        return graph;
        
    }

    public Graph parse(String str) throws RecognitionException {

        ANTLRStringStream input = new ANTLRStringStream(str);
        BasicLexer lexer = new BasicLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        BasicParser parser = new BasicParser(tokens);

        parser.graph();

        return parser.graph;
    }

    protected BufferedImage render(Graph graph) {

        BufferedImage img = new BufferedImage((int) graph.getSize().x, (int) graph.getSize().y, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) img.getGraphics();
        int w = img.getWidth();
        int h = img.getHeight();

        graph.accept(new GraphRenderVisitor().graphics(g, w, h).type(BasicMetaType.GRAPH));
        graph.accept(new EdgeRenderVisitor().graphics(g, w, h).type(BasicMetaType.EDGE));
        graph.accept(new NodeRenderVisitor().graphics(g, w, h).type(BasicMetaType.NODE));
        graph.accept(new NodeLabelRenderVisitor().graphics(g, w, h).type(BasicMetaType.NODE_LABEL));

        img.getGraphics().dispose();

        return img;

    }

}
