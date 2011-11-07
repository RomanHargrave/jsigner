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

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.modsl.antlr.uml.UMLLexer;
import org.modsl.antlr.uml.UMLParser;
import org.modsl.core.agt.layout.SelfRefBenderVisitor;
import org.modsl.core.agt.layout.fr.FRLayoutVisitor;
import org.modsl.core.agt.layout.sugiyama.SugiyamaLayoutVisitor;
import org.modsl.core.agt.model.Graph;
import org.modsl.core.agt.model.ReqLayout;
import org.modsl.core.agt.render.EdgeLabelRenderVisitor;
import org.modsl.core.agt.render.GraphRenderVisitor;
import org.modsl.core.agt.render.NodeLabelRenderVisitor;
import org.modsl.core.agt.render.NodeRenderVisitor;
import org.modsl.core.lang.AbstractTranslator;
import org.modsl.core.lang.uml.layout.cls.ClassEdgeLabelLayoutVisitor;
import org.modsl.core.lang.uml.layout.cls.ClassNodeLayoutVisitor;
import org.modsl.core.lang.uml.layout.cls.ClassRevertGenEdgeLayoutVisitor;
import org.modsl.core.lang.uml.layout.collab.CollabEdgeLabelLayoutVisitor;
import org.modsl.core.lang.uml.layout.collab.CollabNodeLabelLayoutVisitor;
import org.modsl.core.lang.uml.layout.seq.SeqEdgeLabelLayoutVisitor;
import org.modsl.core.lang.uml.layout.seq.SeqLayoutVisitor;
import org.modsl.core.lang.uml.render.ClassAggregateArrowEdgeRenderVisitor;
import org.modsl.core.lang.uml.render.ClassExtendsArrowEdgeRenderVisitor;
import org.modsl.core.lang.uml.render.ClassImplementsArrowEdgeRenderVisitor;
import org.modsl.core.lang.uml.render.ClassNodeRenderVisitor;
import org.modsl.core.lang.uml.render.CollabArrowEdgeRenderVisitor;

public class UMLTranslator extends AbstractTranslator {

    protected Graph layout(Graph graph) {

        if (UMLMetaType.CLASS_GRAPH.equals(graph.getType())) {

            graph.accept(new ClassNodeLayoutVisitor().type(UMLMetaType.CLASS_CLASS_NODE));
            graph.accept(new ClassNodeLayoutVisitor().type(UMLMetaType.CLASS_ABSTRACT_CLASS_NODE));
            graph.accept(new ClassNodeLayoutVisitor().type(UMLMetaType.CLASS_INTERFACE_NODE));

            if (ReqLayout.free.equals(graph.getReqLayout())) {
                graph.accept(new FRLayoutVisitor().type(UMLMetaType.CLASS_GRAPH));
            } else {
                graph.accept(new SelfRefBenderVisitor().xOffset(
                        1.8d * UMLMetaType.CLASS_AGGREGATION_EDGE.getStyle().getArrowLength()).type(null));
                graph.accept(new ClassRevertGenEdgeLayoutVisitor().type(UMLMetaType.CLASS_IMPLEMENTS_EDGE));
                graph.accept(new ClassRevertGenEdgeLayoutVisitor().type(UMLMetaType.CLASS_EXTENDS_EDGE));
                graph.accept(new SugiyamaLayoutVisitor().type(UMLMetaType.CLASS_GRAPH));
                graph.accept(new ClassRevertGenEdgeLayoutVisitor().type(UMLMetaType.CLASS_IMPLEMENTS_EDGE));
                graph.accept(new ClassRevertGenEdgeLayoutVisitor().type(UMLMetaType.CLASS_EXTENDS_EDGE));
            }

            graph.accept(new ClassEdgeLabelLayoutVisitor().type(UMLMetaType.CLASS_AGGREGATION_EDGE));

        } else if (UMLMetaType.COLLAB_GRAPH.equals(graph.getType())) {

            graph.accept(new CollabNodeLabelLayoutVisitor().type(UMLMetaType.COLLAB_NODE));
            if (ReqLayout.free.equals(graph.getReqLayout())) {
                graph.accept(new FRLayoutVisitor().type(UMLMetaType.COLLAB_GRAPH));
            } else {
                graph.accept(new SugiyamaLayoutVisitor().type(UMLMetaType.COLLAB_GRAPH));
            }
            graph.accept(new CollabEdgeLabelLayoutVisitor().type(UMLMetaType.COLLAB_GRAPH));

        } else if (UMLMetaType.SEQ_GRAPH.equals(graph.getType())) {

            graph.accept(new CollabNodeLabelLayoutVisitor().type(UMLMetaType.SEQ_NODE));
            graph.accept(new SeqLayoutVisitor().type(UMLMetaType.SEQ_GRAPH));
            graph.accept(new SeqEdgeLabelLayoutVisitor().type(UMLMetaType.SEQ_GRAPH));

        }

        graph.rescale();

        return graph;

    }

    public Graph parse(String str) throws RecognitionException {

        ANTLRStringStream input = new ANTLRStringStream(str);
        UMLLexer lexer = new UMLLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        UMLParser parser = new UMLParser(tokens);

        parser.diagram();

        return parser.graph;
    }

    protected BufferedImage render(Graph graph) {

        BufferedImage img = new BufferedImage((int) graph.getSize().x, (int) graph.getSize().y, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) img.getGraphics();
        int w = img.getWidth();
        int h = img.getHeight();

        if (UMLMetaType.CLASS_GRAPH.equals(graph.getType())) {

            graph.accept(new GraphRenderVisitor().graphics(g, w, h).type(UMLMetaType.CLASS_GRAPH));

            graph.accept(new ClassExtendsArrowEdgeRenderVisitor().graphics(g, w, h).type(UMLMetaType.CLASS_EXTENDS_EDGE));
            graph.accept(new ClassImplementsArrowEdgeRenderVisitor().graphics(g, w, h).type(UMLMetaType.CLASS_IMPLEMENTS_EDGE));
            graph.accept(new ClassAggregateArrowEdgeRenderVisitor().graphics(g, w, h).type(UMLMetaType.CLASS_AGGREGATION_EDGE));

            graph.accept(new ClassNodeRenderVisitor().graphics(g, w, h).type(UMLMetaType.CLASS_CLASS_NODE));
            graph.accept(new ClassNodeRenderVisitor().graphics(g, w, h).type(UMLMetaType.CLASS_ABSTRACT_CLASS_NODE));
            graph.accept(new ClassNodeRenderVisitor().graphics(g, w, h).type(UMLMetaType.CLASS_INTERFACE_NODE));

            graph.accept(new NodeLabelRenderVisitor().graphics(g, w, h).type(UMLMetaType.CLASS_CLASS_NODE_LABEL));
            graph.accept(new NodeLabelRenderVisitor().graphics(g, w, h).type(UMLMetaType.CLASS_ABSTRACT_CLASS_NODE_LABEL));
            graph.accept(new NodeLabelRenderVisitor().graphics(g, w, h).type(UMLMetaType.CLASS_INTERFACE_NODE_LABEL));

            graph.accept(new NodeLabelRenderVisitor().graphics(g, w, h).type(UMLMetaType.CLASS_VAR_NODE_LABEL));
            graph.accept(new NodeLabelRenderVisitor().graphics(g, w, h).type(UMLMetaType.CLASS_STATIC_VAR_NODE_LABEL));
            graph.accept(new NodeLabelRenderVisitor().graphics(g, w, h).type(UMLMetaType.CLASS_METHOD_NODE_LABEL));
            graph.accept(new NodeLabelRenderVisitor().graphics(g, w, h).type(UMLMetaType.CLASS_ABSTRACT_METHOD_NODE_LABEL));
            graph.accept(new NodeLabelRenderVisitor().graphics(g, w, h).type(UMLMetaType.CLASS_STATIC_METHOD_NODE_LABEL));

            graph.accept(new EdgeLabelRenderVisitor().graphics(g, w, h).type(UMLMetaType.CLASS_MULTIPLICITY_FROM_EDGE_LABEL));
            graph.accept(new EdgeLabelRenderVisitor().graphics(g, w, h).type(UMLMetaType.CLASS_MULTIPLICITY_TO_EDGE_LABEL));

        } else if (UMLMetaType.COLLAB_GRAPH.equals(graph.getType())) {

            graph.accept(new GraphRenderVisitor().graphics(g, w, h).type(UMLMetaType.COLLAB_GRAPH));
            graph.accept(new CollabArrowEdgeRenderVisitor().graphics(g, w, h).type(UMLMetaType.COLLAB_EDGE));
            graph.accept(new NodeRenderVisitor().graphics(g, w, h).type(UMLMetaType.COLLAB_NODE));
            graph.accept(new NodeLabelRenderVisitor().graphics(g, w, h).type(UMLMetaType.COLLAB_NODE_LABEL));
            graph.accept(new EdgeLabelRenderVisitor().graphics(g, w, h).type(UMLMetaType.COLLAB_EDGE_LABEL));

        } else if (UMLMetaType.SEQ_GRAPH.equals(graph.getType())) {

            graph.accept(new GraphRenderVisitor().graphics(g, w, h).type(UMLMetaType.SEQ_GRAPH));
            graph.accept(new NodeRenderVisitor().graphics(g, w, h).type(UMLMetaType.SEQ_NODE));
            graph.accept(new NodeLabelRenderVisitor().graphics(g, w, h).type(UMLMetaType.SEQ_NODE_LABEL));

        }

        img.getGraphics().dispose();

        return img;

    }

}
