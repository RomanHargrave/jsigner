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

package org.modsl.core.agt.model;

import org.modsl.core.agt.visitor.AbstractVisitor;

/**
 * Recursive position calculations etc on the graph
 * @author avishnyakov
 */
abstract class MinMaxVisitor extends AbstractVisitor {

    Pt p;
    AbstractBox<?> box;

    MinMaxVisitor(Pt init) {
        this.p = init;
    }

    public MinMaxVisitor() {
    }

    @Override
    public void in(Bend bend) {
        apply(bend);
    }

    @Override
    public void in(EdgeLabel label) {
        apply(label);
    }

    @Override
    public void in(NodeLabel label) {
        apply(label);
    }

    @Override
    public void in(GraphLabel label) {
        apply(label);
    }

    @Override
    public void in(Node node) {
        apply(node);
    }

    abstract void apply(AbstractBox<?> b);

}
