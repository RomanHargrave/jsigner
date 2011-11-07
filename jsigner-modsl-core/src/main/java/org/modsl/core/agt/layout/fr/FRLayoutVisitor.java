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

package org.modsl.core.agt.layout.fr;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.lang.Math.sqrt;

import java.util.Random;

import org.apache.log4j.Logger;
import org.modsl.core.agt.layout.AbstractLayoutVisitor;
import org.modsl.core.agt.model.AbstractBox;
import org.modsl.core.agt.model.Edge;
import org.modsl.core.agt.model.Graph;
import org.modsl.core.agt.model.Node;
import org.modsl.core.agt.model.Pt;

/**
 * Modified Fruchterman-Rheingold layout algorithm
 * @author avishnyakov
 */
public class FRLayoutVisitor extends AbstractLayoutVisitor {

    // defaults
    // maxIterations = 200
    // tempMultiplier = 0.05 ? 0.03
    // attractionMultiplier = 0.75
    // repulsionMultiplier = 0.75
    // repulsionMultiplier = 0.4

    static final int MAX_ITER = 500;
    static final double TEMP_MULTI = 0.1d;
    static final double ATTR_MULTI = 0.5d;
    static final double REP_MULTI = 0.75d;

    Logger log = Logger.getLogger(this.getClass());

    double temp, kForce, kAttraction, kRepulsion;
    double minWeight = Double.MAX_VALUE, maxWeight = -Double.MAX_VALUE;

    Random random;
    Graph graph;
    Pt req;

    @Override
    public void apply(Graph graph) {

        this.graph = graph;
        this.req = getOrEsimateGraphReqSize();

        random = new Random(graph.getName().hashCode());
        graph.randomize(random);

        //calcWeights();

        temp = max((req.x + req.y) * TEMP_MULTI, Pt.EPSILON);
        kForce = max(sqrt(req.x * req.y / graph.getNodes().size()), Pt.EPSILON);
        kAttraction = ATTR_MULTI * kForce;
        kRepulsion = REP_MULTI * kForce;

        for (int iterCurrent = 0; iterCurrent < MAX_ITER; iterCurrent++) {
            zeroDisp();
            repulsion();
            attraction();
            moveVertexes();
            reduceTemperature(iterCurrent, MAX_ITER);
        }

    }

    void attraction() {
        for (Edge e : graph.getEdges()) {
            Pt delta = getCtrDelta(e.getNode1(), e.getNode2());
            double dl = delta.lenSafe();
            Pt f = delta.div(dl).mult(attractionForce(dl));
            e.getNode1().getDisp().decBy(f);
            e.getNode2().getDisp().incBy(f);
            // log.debug("# atr " + e.getNode1() + " = -" + f + ", " +
            // e.getNode2() + " = " + f);
        }
    }

    double attractionForce(double dist) {
        return dist * dist / kAttraction;
    }

    void calcWeights() {
        for (Node n : graph.getNodes()) {
            minWeight = min(minWeight, n.getWeight());
            maxWeight = max(maxWeight, n.getWeight());
        }
    }

    Pt getCtrDelta(AbstractBox<?> n1, AbstractBox<?> n2) {
        Pt delta = n1.getCtrPos().minus(n2.getCtrPos());
        if (delta.len() < Pt.EPSILON) {
            delta.randomize(random, new Pt(1d, 1d));
        }
        return delta;
    }

    Pt getOrEsimateGraphReqSize() {
        if (graph.getReqSize().equals(new Pt(0d, 0d))) {
            double area = 0d;
            double gr = (1d + sqrt(5d)) / 2d;
            for (Node n : graph.getNodes()) {
                area += (n.getSize().x + 30d) * (n.getSize().y + 20d);
            }
            double h = sqrt(area * 2d * Math.log(graph.getEdges().size() + 3) / gr) + 1d;
            double w = gr * h + 1d;
            graph.setReqSize(w, h);
        }
        return graph.getReqSize();
    }

    Pt getPortDelta(AbstractBox<?> n1, AbstractBox<?> n2) {
        Pt delta = n1.getPortDelta(n2);
        if (delta.len() < Pt.EPSILON) {
            delta.randomize(random, new Pt(1d, 1d));
        }
        return delta;
    }

    void moveVertexes() {
        for (Node n : graph.getNodes()) {
            Pt delta = n.getDisp();
            double dl = delta.lenSafe();
            Pt f = delta.div(dl).mult(min(dl, temp));
            n.getPos().incBy(f);
            // log.debug("# !mv " + delta + " " + dl + " " + n + " = " + f);
            n.getPos().x = min(req.x - n.getSize().x, max(0d, n.getPos().x));
            n.getPos().y = min(req.y - n.getSize().y, max(0d, n.getPos().y));
        }
    }

    void reduceTemperature(int iterCurrent, int iterMax) {
        temp *= (1d - (double) iterCurrent / iterMax);
    }

    void repulsion() {
        for (Node n1 : graph.getNodes()) {
            for (Node n2 : graph.getNodes()) {
                if (n1 != n2) {
                    Pt delta = getPortDelta(n1, n2);
                    double dl = delta.lenSafe();
                    Pt f = delta.div(dl).mult(repulsionForce(dl));
                    n1.getDisp().incBy(f);
                    // log.debug("# rep " + n1 + " = " + f);
                }
            }
        }
    }

    double repulsionForce(double dist) {
        return kRepulsion * kRepulsion / dist;
    }

    void zeroDisp() {
        for (Node n1 : graph.getNodes()) {
            n1.getDisp().zero();
        }
    }

}
