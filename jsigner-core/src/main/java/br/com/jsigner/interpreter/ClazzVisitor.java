/**
 * Copyright (C) 2008 Rafael Farias Silva <rafaferry@gmail.com>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *         http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package br.com.jsigner.interpreter;

import br.com.jsigner.diagram.elements.Clazz;

public interface ClazzVisitor extends Visitor<Clazz, String> {

	public abstract AttributeVisitor getAttributeVisitor();

	public abstract RelationshipVisitor getRelationshipVisitor();

	public abstract MethodVisitor getMethodVisitor();

}