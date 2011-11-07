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

abstract class AbstractLabel<P extends AbstractElement<?>> extends AbstractBox<P> {

    static int counter = 0;

    public AbstractLabel(MetaType type, String name) {
        super(type, name);
        this.index = counter++;
    }

    public Pt getTextPos() {
        return getPos().plus(new Pt(0d, getType().getStyle().getBaseline()));
    }

    public Pt getUnderline1() {
        return new Pt(getTextPos().x, getTextPos().y + 2);
    }

    public Pt getUnderline2() {
        return new Pt(getTextPos().x + getType().getStyle().getStringWidth(name), getTextPos().y + 2);
    }

    @Override
    public Pt getSize() {
        return new Pt(getType().getStyle().getStringWidth(getName()), getType().getStyle().getFontHeight());
    }

}
