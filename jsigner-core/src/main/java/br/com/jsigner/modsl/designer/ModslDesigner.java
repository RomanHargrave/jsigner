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

package br.com.jsigner.modsl.designer;

import java.awt.image.BufferedImage;

import org.antlr.runtime.RecognitionException;
import org.modsl.core.lang.uml.UMLMetaType;
import org.modsl.core.lang.uml.UMLTranslator;
import org.modsl.core.render.StyleLoader;

import br.com.jsigner.JsignerConfiguration;
import br.com.jsigner.designer.JsignerDesigner;
import br.com.jsigner.diagram.ClassDiagram;
import br.com.jsigner.interpreter.ClassDiagramVisitor;
import br.com.jsigner.log.JsignerLog;
import br.com.jsigner.modsl.interpreter.ModslClassDiagramVisitor;

public class ModslDesigner extends JsignerDesigner {
	
	private static JsignerLog log = JsignerConfiguration.getLog();

	@Override
	public BufferedImage generateImage(ClassDiagram classDiagram) {
		StyleLoader stl = new StyleLoader();
		stl.load("cfg/uml:cfg", "uml", UMLMetaType.class);

		UMLTranslator translator = new UMLTranslator();

		try {
			ClassDiagramVisitor visitor = new ModslClassDiagramVisitor();
			classDiagram.accept(visitor);
			
			String code = visitor.getResult();
			log.debug(code);
			BufferedImage image = translator.translate(code);
			log.info("Diagram generated: " + classDiagram.getName());
			return image;
		} catch (RecognitionException e) {
			throw new RuntimeException(e);
		} 
	}


}
