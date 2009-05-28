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

package br.com.jsigner;

import java.util.List;

import br.com.jsigner.designer.JsignerDesigner;
import br.com.jsigner.diagram.ClassDiagram;

public final class Jsigner {

	public void design(String diagramName, List<Class<?>> diagramClasses) {

		JsignerDesigner designer = JsignerConfiguration.getJsignerDesigner();
		ClassDiagram diagram = new ClassDiagram(diagramName, diagramClasses);
		designer.generateDiagramImage(diagram);
	}
}
