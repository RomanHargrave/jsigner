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

package br.com.jsigner.drawwer;

import java.io.File;

import br.com.jsigner.diagram.ClassDiagram;
import br.com.jsigner.diagram.elements.relationship.RelationshipRepository;

public abstract class JsignerDesigner {

	public void execute(ClassDiagram classDiagram, File outputFolder) {
		RelationshipRepository.init();
		this.design(classDiagram, outputFolder);
	}

	public abstract void design(ClassDiagram classDiagram, File outputFolder);
}
