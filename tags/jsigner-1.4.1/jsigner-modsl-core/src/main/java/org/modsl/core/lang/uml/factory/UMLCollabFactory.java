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

package org.modsl.core.lang.uml.factory;

import org.modsl.core.lang.uml.UMLMetaType;

/**
 * @author AVishnyakov
 */
public class UMLCollabFactory extends AbstractUMLSeqCollabFactory {

    @Override
    UMLMetaType getEdgeLabelType() {
        return UMLMetaType.COLLAB_EDGE_LABEL;
    }

    @Override
    UMLMetaType getEdgeType() {
        return UMLMetaType.COLLAB_EDGE;
    }

    @Override
    UMLMetaType getGraphType() {
        return UMLMetaType.COLLAB_GRAPH;
    }

    @Override
    UMLMetaType getNodeLabelType() {
        return UMLMetaType.COLLAB_NODE_LABEL;
    }

    @Override
    UMLMetaType getNodeType() {
        return UMLMetaType.COLLAB_NODE;
    }
    
}