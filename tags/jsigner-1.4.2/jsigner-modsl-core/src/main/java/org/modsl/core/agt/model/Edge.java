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

import static java.lang.Math.abs;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.modsl.core.agt.visitor.AbstractVisitor;

/**
 * Graph edge
 * @author avishnyakov
 */
public class Edge extends AbstractElement<Graph> {

    private static int counter = 0;

    /**
     * Start node
     */
    Node node1;

    /**
     * End node
     */
    Node node2;

    /**
     * Start node name (will be resolved to node1 reference when post-processing
     */
    String node1Name;

    /**
     * End node name (will be resolved to node2 reference when post-processing
     */
    String node2Name;

    /**
     * Are start and end nodes swapped? Needed for the layout algorithms.
     */
    boolean reverted = false;

    /**
     * Bends
     */
    List<Bend> bends = new LinkedList<Bend>();

    /**
     * Labels
     */
    List<EdgeLabel> labels = new LinkedList<EdgeLabel>();

    /**
     * Create new
     * 
     * @param type type
     * @param node1 start node
     * @param node2 end node
     */
    public Edge(MetaType type, Node node1, Node node2) {
        super(type);
        this.index = counter++;
        this.node1 = node1;
        this.node1.addConnectedEdge(this);
        this.node2 = node2;
        this.node2.addConnectedEdge(this);
    }

    public Edge(MetaType type, Node node1, String node2name) {
        super(type);
        this.index = counter++;
        this.node1 = node1;
        this.node1.addConnectedEdge(this);
        this.node2Name = node2name;
    }

    /**
     * Create new
     * 
     * @param type type
     * @param name name
     * @param node1 start node
     * @param node2 end node
     */
    public Edge(MetaType type, String name, Node node1, Node node2) {
        this(type, node1, node2);
        this.index = counter++;
        this.name = name;
    }

    @Override
    public void accept(AbstractVisitor visitor) {
        visitor.in(this);
        for (Bend b : bends) {
            b.accept(visitor);
        }
        for (EdgeLabel l : labels) {
            l.accept(visitor);
        }
        visitor.out(this);
    }

    /**
     * Add new bend
     * 
     * @param bend
     */
    public void add(Bend bend) {
        bends.add(bend);
        bend.setParent(this);
    }

    public void addLabel(EdgeLabel label) {
        labels.add(label);
        label.setParent(this);
        label.setAnchor1(node1);
        label.setAnchor2(node2);
    }

    /**
     * @return angle at node 2
     */
    public double angle1() {
        return getFirstBend().angle(node1);
    }

    /**
     * @return angle at node 2
     */
    public double angle2() {
        return getLastBend().angle(node2);
    }

    /**
     * @param index
     * @return bend
     */
    public Bend getBend(int index) {
        return bends.get(index);
    }

    /**
     * @return all bends
     */
    public List<Bend> getBends() {
        return bends;
    }

    /**
     * @param p1
     * @param p2
     * @return distance (in hops) between nodes or bends this edge connects
     */
    public int getDistance(AbstractBox<?> p1, AbstractBox<?> p2) {
        int p1i = -2, p2i = -2;
        p1i = p1.equals(node1) ? -1 : p1i;
        p2i = p2.equals(node1) ? -1 : p2i;
        int t = bends.indexOf(p1);
        p1i = t > -1 ? t : p1i;
        t = bends.indexOf(p2);
        p2i = t > -1 ? t : p2i;
        p1i = p1.equals(node2) ? bends.size() : p1i;
        p2i = p2.equals(node2) ? bends.size() : p2i;
        return p1i > -2 && p2i > -2 ? abs(p2i - p1i) : Integer.MAX_VALUE;
    }

    /**
     * @return get first bend after the node 1 (will return node 2 if there are
     * no bends)
     */
    public AbstractBox<?> getFirstBend() {
        if (bends.isEmpty()) {
            return node2;
        } else {
            return bends.get(0);
        }
    }

    public List<EdgeLabel> getLabels() {
        return labels;
    }

    public List<EdgeLabel> getLabels(Collection<MetaType> mts) {
        List<EdgeLabel> ls = new LinkedList<EdgeLabel>();
        for (EdgeLabel l : labels) {
            if (mts.contains(l.getType())) {
                ls.add(l);
            }
        }
        return ls;
    }

    public List<EdgeLabel> getLabels(MetaType mt) {
        return getLabels(Arrays.asList(new MetaType[] { mt }));
    }

    /**
     * @return get last bend before the node 2 (will return node 1 if there are
     * no bends)
     */
    public AbstractBox<?> getLastBend() {
        if (bends.isEmpty()) {
            return node1;
        } else {
            return bends.get(bends.size() - 1);
        }
    }

    /**
     * @return length of this edge (staright line between center positions) w/o
     * adjustment for overlay
     */
    public double getLength() {
        return node2.getCtrPos().minus(node1.getCtrPos()).len();
    }

    /**
     * @return start node
     */
    public Node getNode1() {
        return node1;
    }

    /**
     * @return start node name
     */
    public String getNode1Name() {
        return node1Name;
    }

    /**
     * @return start point position at node 1
     */
    public Pt getNode1Port() {
        AbstractBox<?> b = getFirstBend();
        return node1.getPort(node1.sin(b), node1.cos(b), node1.tan(b));
    }

    /**
     * @return end node
     */
    public Node getNode2() {
        return node2;
    }

    /**
     * @return end node name
     */
    public String getNode2Name() {
        return node2Name;
    }

    /**
     * @return end point position at node 2
     */
    public Pt getNode2Port() {
        AbstractBox<?> b = getLastBend();
        return node2.getPort(node2.sin(b), node2.cos(b), node2.tan(b));
    }

    /**
     * @return true is reverted (by layout algorithm)
     */
    public boolean isReverted() {
        return reverted;
    }

    /**
     * @return true if this edge is self referencing
     */
    public boolean isSelfReference() {
        return node1.equals(node2);
    }

    public void revert() {
        Node tn = node1;
        node1 = node2;
        node2 = tn;
        String tnn = node1Name;
        node1Name = node2Name;
        node2Name = tnn;
        Collections.reverse(bends);
    }

    /**
     * Set start node
     * 
     * @param n1 start node
     */
    public void setNode1(Node n1) {
        if (node1 != null && !node1.equals(n1)) {
            node1.removeConnectedEdge(this);
        }
        node1 = n1;
        if (node1 != null) {
            node1.addConnectedEdge(this);
            for (EdgeLabel label : labels) {
                label.setAnchor1(node1);
            }
        }
    }

    /**
     * Set end node
     * 
     * @param n2 end node
     */
    public void setNode2(Node n2) {
        if (node2 != null && !node2.equals(n2)) {
            node2.removeConnectedEdge(this);
        }
        node2 = n2;
        if (node2 != null) {
            node2.addConnectedEdge(this);
            for (EdgeLabel label : labels) {
                label.setAnchor2(node2);
            }
        }
    }

    /**
     * Revert edge's direction (layout)
     * 
     * @param r
     */
    public void setReverted(boolean r) {
        if (reverted != r) {
            revert();
            reverted = r;
        }
    }

    @Override
    public String toString() {
        return super.toString() + "(" + (node1 == null ? "*" + node1Name : node1.getName()) + "-" + (isReverted() ? "^" : "")
                + bends.size() + "-" + (node2 == null ? "*" + node2Name : node2.getName()) + ")";
    }
}