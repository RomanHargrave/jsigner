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

	/**
	 * Methods configuration
	 */
	private static boolean hidePrivateMethods;
	private static boolean hideSetters;
	private static boolean hideGetters;
	private static boolean hideEquals;
	private static boolean hideHashcode;
	
	/**
	 * Attributes configuration
	 */
	private static boolean hideSerialVersion;
	

	public static boolean hideSerialVersion() {
		return hideSerialVersion;
	}

	public static void setHideSerialVersion(boolean hideSerialVersion) {
		log.debug("Setting hideSerialVersion to :" + hideEquals);
		JsignerConfiguration.hideSerialVersion = hideSerialVersion;
	}

	public static boolean hidePrivateMethods() {
		return hidePrivateMethods;
	}

	public static void setHidePrivateMethods(boolean hidePrivateMethods) {
		log.debug("Setting hideSerialVersion to :" + hidePrivateMethods);
		JsignerConfiguration.hidePrivateMethods = hidePrivateMethods;
	}

	public static boolean hideSetters() {
		return hideSetters;
	}

	public static void setHideSetters(boolean hideSetters) {
		log.debug("Setting hideSerialVersion to :" + hideSetters);
		JsignerConfiguration.hideSetters = hideSetters;
	}

	public static boolean hideGetters() {
		return hideGetters;
	}

	public static void setHideGetters(boolean hideGetters) {
		log.debug("Setting hideSerialVersion to :" + hideGetters);
		JsignerConfiguration.hideGetters = hideGetters;
	}

	public static boolean hideEquals() {
		return hideEquals;
	}

	public static void setHideEquals(boolean hideEquals) {
		log.debug("Setting hideEquals to :" + hideEquals);
		JsignerConfiguration.hideEquals = hideEquals;
	}

	public static boolean hideHashcode() {
		return hideHashcode;
	}

	public static void setHideHashcode(boolean hideHashcode) {
		log.debug("Setting hideSerialVersion to :" + hideHashcode);
		JsignerConfiguration.hideHashcode = hideHashcode;
	}


	public static RelationshipMultiplicityFinder getMultiplicityFinder() {
		return configuredFinder;
	}

	public static void setMultiplicityFinder(
			RelationshipMultiplicityFinder multiplicityFinder) {
		configuredFinder = multiplicityFinder;
	}

	public static JsignerDesigner getJsignerDesigner() {
		return new ModslDesigner();
	}

	public static JsignerLog getLog() {
		return log;
	}

}
