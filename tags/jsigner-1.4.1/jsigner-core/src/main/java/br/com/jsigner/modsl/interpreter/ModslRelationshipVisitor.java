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

package br.com.jsigner.modsl.interpreter;

import br.com.jsigner.diagram.elements.relationship.Relationship;
import br.com.jsigner.diagram.elements.relationship.multiplicity.Multiplicity;
import br.com.jsigner.interpreter.RelationshipVisitor;

public class ModslRelationshipVisitor implements RelationshipVisitor {
	
	private StringBuilder diagramCode = new StringBuilder();

	public String getResult() {
		String result = diagramCode.toString();
		diagramCode = new StringBuilder();
		return result;
	}

	public void visit(Relationship relationship) {
		Multiplicity multiplicity = relationship.getMultiplicity();
		diagramCode.append("\t\t");
		switch (multiplicity) {
		case OneToOne:
			diagramCode.append("1->1");
			break;
		case OneToMany:
			diagramCode.append("1->*");
			break;
		case ManyToMany:
			diagramCode.append("*->*");
			break;
		}
		diagramCode.append("(").append(relationship.getTargetClassName()).append(");\n ");
	}

}
