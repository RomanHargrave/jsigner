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

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.modsl.core.agt.visitor.AbstractVisitor;

/**
 * Abstract graph element
 * @author avishnyakov
 */
public abstract class AbstractElement<P extends AbstractElement<?>> {

    Logger log = Logger.getLogger(getClass());

    /**
     * Name of this object
     */
    String name;

    /**
     * Parent node
     */
    P parent;

    /**
     * Type
     */
    MetaType type;

    /**
     * Index
     */
    int index = -1;

    /**
     * Meta type map for convenient access in the templates
     */
    protected Map<String, Object> metaTypeMap;

    /**
     * Create new
     */
    public AbstractElement() {
        //
    }

    /**
     * Create new
     * @param type
     */
    public AbstractElement(MetaType type) {
        super();
        this.type = type;
    }

    /**
     * Create new
     * @param type
     * @param name
     */
    public AbstractElement(MetaType type, String name) {
        this(type);
        this.name = name;
    }

    /**
     * Guaranteed to be called on all elements of the graph, traversing depth
     * first, edges before nodes before bends before labels when elements of the
     * graph already created.
     */
    public abstract void accept(AbstractVisitor visitor);

    /**
     * @return index
     */
    public int getIndex() {
        return index;
    }

    /**
     * Convenience method for string template access to the meta-type map
     * @return meta type map
     */
    public Map<String, Object> getMeta() {
        if (metaTypeMap == null) {
            Object[] mta = type.getClass().getEnumConstants();
            metaTypeMap = new HashMap<String, Object>(mta.length);
            for (Object mt : mta) {
                metaTypeMap.put(mt.toString(), mt);
            }
        }
        return metaTypeMap;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @return parent
     */
    public P getParent() {
        return parent;
    }

    /**
     * @return meta type
     */
    public MetaType getType() {
        return type;
    }

    /**
     * @return is this element virtual? default is false
     */
    public boolean isVirtual() {
        return false;
    }

    /**
     * Set index
     * @param index
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * Set name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set parent
     * @param parent
     */
    public void setParent(P parent) {
        this.parent = parent;
    }
    
    /**
     * Set meta type
     * @param type
     */
    public void setType(MetaType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return name + ":" + type;
    }


}