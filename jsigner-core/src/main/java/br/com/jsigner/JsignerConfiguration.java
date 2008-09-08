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

import br.com.jsigner.designer.JsignerDesigner;
import br.com.jsigner.diagram.elements.relationship.multiplicity.PersistenceMultiplicityFinder;
import br.com.jsigner.diagram.elements.relationship.multiplicity.RelationshipMultiplicityFinder;
import br.com.jsigner.log.JsignerLog;
import br.com.jsigner.modsl.designer.ModslDesigner;

public abstract class JsignerConfiguration {

	private static RelationshipMultiplicityFinder configuredFinder = new PersistenceMultiplicityFinder();
	private static JsignerLog log = new JsignerLog();

	public static RelationshipMultiplicityFinder getMultiplicityFinder() {
		return configuredFinder;
	}

	public static void setMultiplicityFinder(
			RelationshipMultiplicityFinder multiplicityFinder) {
		configuredFinder = multiplicityFinder;
	}

	public static boolean hidePrivateMethods() {
		return true;
	}

	public static boolean hideSetters() {
		return true;
	}

	public static boolean hideGetters() {
		return true;
	}
	
	public static boolean hideEquals() {
		return true;
	}
	
	public static boolean hideHashcode() {
		return true;
	}
	
	public static JsignerDesigner getJsignerDesigner() {
		return new ModslDesigner();
	}
	
	public static JsignerLog getLog() {
		return log;
	}

}
