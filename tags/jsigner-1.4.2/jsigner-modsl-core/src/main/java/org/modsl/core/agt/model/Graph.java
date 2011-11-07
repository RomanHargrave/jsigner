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

package org.modsl.core.agt.model;

import static java.lang.Math.max;
import static java.lang.Math.min;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.modsl.core.agt.visitor.AbstractVisitor;
import org.modsl.core.render.Style;

public class Graph extends AbstractBox<Graph> {

    private static int counter = 0;

    /**
     * List of children nodes
     */
    List<Node> nodes = new LinkedList<Node>();

    /**
     * Labels
     */
    List<GraphLabel> labels = new LinkedList<GraphLabel>();

    /**
     * Map of children nodes {name->node}
     */
    Map<String, Node> nodeMap = new HashMap<String, Node>();

    /**
     * List of children edges
     */
    List<Edge> edges = new LinkedList<Edge>();

    /**
     * This element's requested size
     */
    Pt reqSize = new Pt();

    /**
     * Top padding
     */
    double topPadding = 0d;

    /**
     * Bottom padding
     */
    double bottomPadding;

    /**
     * Left padding
     */
    double leftPadding;

    /**
     * Right padding
     */
    double rightPadding;

    /**
     * Log message container
     */
    List<String> logMessages;

    /**
     * Requested layout type
     */
    ReqLayout reqLayout;

    public Graph(MetaType type) {
        super(type);
        this.index = counter++;
        resetPaddings();
    }

    public Graph(MetaType type, String name) {
        super(type, name);
        resetPaddings();
        this.index = counter++;
    }

    @Override
    public void accept(AbstractVisitor visitor) {
        visitor.in(this);
        for (Edge e : edges) {
            e.accept(visitor);
        }
        for (Node n : nodes) {
            n.accept(visitor);
        }
        for (GraphLabel l : getLabels()) {
            l.accept(visitor);
        }
        visitor.out(this);
    }

    /**
     * Add child edge
     * @param child
     */
    public void add(Edge child) {
        child.parent = this;
        edges.add(child);
    }

    /**
     * Add child node
     * @param child
     */
    public void add(Node child) {
        child.parent = this;
        nodes.add(child);
        nodeMap.put(child.getName(), child);
    }

    public void addProcAttr(String key, String value) {
        ProcAttrKey pa = ProcAttrKey.fromString(key);
        switch (pa) {
        case width:
            reqSize.x = Integer.parseInt(value);
            break;
        case height:
            reqSize.y = Integer.parseInt(value);
            break;
        case layout:
            reqLayout = ReqLayout.fromString(value);
            break;
        }
    }

    /**
     * @return node's area in pixels^2
     */
    public double getArea() {
        return getSize().x * getSize().y;
    }

    public double getBottomPadding() {
        return bottomPadding;
    }

    /**
     * @param index
     * @return edge by index
     */
    public Edge getEdge(int index) {
        return edges.get(index);
    }

    /**
     * @return all edge labels
     */
    public List<EdgeLabel> getEdgeLabels() {
        List<EdgeLabel> l = new LinkedList<EdgeLabel>();
        for (Edge e : edges) {
            l.addAll(e.getLabels());
        }
        return l;
    }

    /**
     * @return children edge list
     */
    public List<Edge> getEdges() {
        return edges;
    }

    private Pt getExtraPadding() {
        return new Pt(leftPadding + rightPadding + 1, topPadding + bottomPadding + 1);
    }

    public List<GraphLabel> getLabels() {
        return labels;
    }

    public double getLeftPadding() {
        return leftPadding;
    }

    public List<String> getLogMessages() {
        return logMessages;
    }

    /**
     * @param index
     * @return node by index
     */
    public Node getNode(int index) {
        return nodes.get(index);
    }

    /**
     * @param key
     * @return node by it's name
     */
    public Node getNode(String key) {
        return nodeMap.get(key);
    }

    /**
     * @return children node list
     */
    public List<Node> getNodes() {
        return nodes;
    }

    /**
     * @return size requested by the client
     */
    public Pt getReqSize() {
        return reqSize;
    }

    public double getRightPadding() {
        return rightPadding;
    }

    /**
     * @return sum of all edge lengths
     */
    public double getSumChildEdgeLengths() {
        double len = 0d;
        for (Edge e : edges) {
            len += e.getLength();
        }
        return len;
    }

    public double getTopPadding() {
        return topPadding;
    }

    /**
     * @return max position
     */
    public Pt maxPos() {
        MinMaxVisitor mmv = new MinMaxVisitor(new Pt(-Double.MAX_VALUE, -Double.MAX_VALUE)) {
            @Override
            void apply(AbstractBox<?> b) {
                p.x = max(p.x, b.getPos().x);
                p.y = max(p.y, b.getPos().y);
            }
        };
        accept(mmv);
        return mmv.p;
    }

    /**
     * Bottom right corner's (x, y)
     * @return max (x, y)
     */
    public Pt maxPt() {
        MinMaxVisitor mmv = new MinMaxVisitor(new Pt(-Double.MAX_VALUE, -Double.MAX_VALUE)) {
            @Override
            void apply(AbstractBox<?> b) {
                p.x = max(p.x, b.getPos().x + b.getSize().x);
                p.y = max(p.y, b.getPos().y + b.getSize().y);
            }
        };
        accept(mmv);
        return mmv.p;
    }

    /**
     * @return node with max x (the rightmost one, including it's size)
     */
    public AbstractBox<?> maxXBox() {
        MinMaxVisitor mmv = new MinMaxVisitor() {
            @Override
            void apply(AbstractBox<?> b) {
                box = box == null ? b : (box.getPos().x + box.getSize().x < b.getPos().x + b.getSize().x ? b : box);
            }
        };
        accept(mmv);
        return mmv.box;
    }

    /**
     * @return node with max y (the lowest one, including it's size)
     */
    public AbstractBox<?> maxYBox() {
        MinMaxVisitor mmv = new MinMaxVisitor() {
            @Override
            void apply(AbstractBox<?> b) {
                box = box == null ? b : (box.getPos().y + box.getSize().y < b.getPos().y + b.getSize().y ? b : box);
            }
        };
        accept(mmv);
        return mmv.box;
    }

    public Pt minPos() {
        return minPt();
    }

    /**
     * Top left corner's (x, y). Can be different from 0, 0 depending on the
     * nodes' positions
     * @return min (x, y)
     */
    public Pt minPt() {
        MinMaxVisitor mmv = new MinMaxVisitor(new Pt(Double.MAX_VALUE, Double.MAX_VALUE)) {
            @Override
            void apply(AbstractBox<?> b) {
                p.x = min(p.x, b.getPos().x);
                p.y = min(p.y, b.getPos().y);
            }
        };
        accept(mmv);
        return mmv.p;
    }

    /**
     * Shift (x, y) on all vertexes to bring min(x, y) to (0, 0)
     */
    public void normalize() {
        MinMaxVisitor mmv = new MinMaxVisitor(minPt()) {
            @Override
            void apply(AbstractBox<?> b) {
                b.getPos().decBy(p);
            }
        };
        accept(mmv);
    }

    public void randomize(Random random) {
        for (Node n : nodes) {
            n.getPos().randomize(random, reqSize);
        }
    }

    /**
     * Recalculates and sets size of this (non-normalized) graph to true size of
     * the non-normalized graph
     */
    public void recalcSize() {
        if (nodes.isEmpty()) {
            size = new Pt(1d, 1d);
        } else {
            size = maxPt().decBy(minPt());
        }
    }

    /**
     * Rescale/normalize diagram to it's current content, add paddings
     * @param newSize new size
     */
    public void rescale() {
        recalcSize();
        rescale(getSize().plus(getExtraPadding()));
    }

    /**
     * Rescale diagram to the given size
     * @param newSize new size
     */
    public void rescale(Pt newSize) {

        if (nodes.isEmpty()) {
            return;
        }

        normalize();
        recalcSize();

        AbstractBox<?> maxXBox = maxXBox();
        AbstractBox<?> maxYBox = maxYBox();

        Pt maxXYSize = new Pt(maxXBox.getSize().x, maxYBox.getSize().y);

        final Pt newSizeExt = newSize.minus(maxXYSize).decBy(getExtraPadding()).max(1d, 1d);
        final Pt sizeExt = getSize().minus(maxXYSize).max(1d, 1d);
        final Pt topLeft = new Pt(leftPadding, topPadding);

        MinMaxVisitor mmv = new MinMaxVisitor() {
            @Override
            void apply(AbstractBox<?> b) {
                b.getPos().mulBy(newSizeExt).divBy(sizeExt).incBy(topLeft);
            }
        };
        accept(mmv);

        size = new Pt(newSize);

    }

    /**
     * Resets paddings to the values dictated by font transform object
     * (essentially based on the font size)
     */
    private void resetPaddings() {
        Style s = type.getStyle();
        if (s != null) {
            leftPadding = s.getLeftPadding();
            rightPadding = s.getRightPadding();
            topPadding = s.getTopPadding();
            bottomPadding = s.getBottomPadding() + 12; // (c) line
        }
    }

    public void setLogMessages(List<String> logMessages) {
        this.logMessages = logMessages;
    }

    public void setReqSize(double x, double y) {
        this.reqSize.x = x;
        this.reqSize.y = y;
    }

    public ReqLayout getReqLayout() {
        return reqLayout;
    }

}
