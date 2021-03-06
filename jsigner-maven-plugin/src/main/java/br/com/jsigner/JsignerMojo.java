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

import java.io.File;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

import br.com.jsigner.diagram.elements.relationship.multiplicity.CollectionMultiplicityFinder;
import br.com.jsigner.diagram.elements.relationship.multiplicity.PersistenceMultiplicityFinder;
import br.com.jsigner.log.JsignerLog;
import br.com.jsigner.log.LogAdapter;

/**
 * @goal design
 * @aggregator
 */
public class JsignerMojo extends AbstractMojo {

	/**
	 * @parameter
	 */
	private File jarsFolder;

	/**
	 * @parameter
	 */
	private File outputFolder;

	/**
	 * @parameter default-value="true"
	 */
	private boolean discoverMultiplicityByPersistenceAnnotations;

	/**
	 * @parameter default-value="true"
	 */
	private boolean hidePrivateMethods;

	/**
	 * @parameter default-value="true"
	 */
	private boolean hideSetters;

	/**
	 * @parameter default-value="true"
	 */
	private boolean hideGetters;

	/**
	 * @parameter default-value="true"
	 */
	private boolean hideEquals;

	/**
	 * @parameter default-value="true"
	 */
	private boolean hideHashcode;

	/**
	 * @parameter default-value="true"
	 */
	private boolean hideSerialVersion;

	public void execute() throws MojoExecutionException, MojoFailureException {
		checkPreConditions();
		configurePlugin();

//		JsignerLog log = this.prepareLog();
//
//		try {
//			log.info("Executing Jsigner maven plugin on"
//					+ jarsFolder.getAbsolutePath());
//
//			if (jarsFolder.exists()) {
//				
//				Jsigner jsigner = new Jsigner();
//				jsigner.design(jarsFolder, outputFolder);
//				log.info("diagrams created at: "
//						+ outputFolder.getAbsolutePath());
//			} else {
//				log
//						.error("jarsFolder"
//								+ jarsFolder.toString()
//								+ " doesn't exists! Specify it in the plugin configuration!");
//				throw new MojoExecutionException("invalid jarsFolder");
//			}
//		} catch (MalformedURLException e) {
//			throw new MojoExecutionException(e.getMessage(), e);
//		} catch (RuntimeException e) {
//			throw new MojoExecutionException(e.getMessage(), e);
//		}
	}

	private void configurePlugin() {
		// Methods configuration

		JsignerConfiguration.setHideEquals(hideEquals);
		JsignerConfiguration.setHideGetters(hideGetters);
		JsignerConfiguration.setHideHashcode(hideHashcode);
		JsignerConfiguration.setHidePrivateMethods(hidePrivateMethods);
		JsignerConfiguration.setHideSetters(hideSetters);

		// Attribute configuration
		JsignerConfiguration.setHideSerialVersion(hideSerialVersion);

		// relationship configuration
		if (discoverMultiplicityByPersistenceAnnotations) {
			JsignerConfiguration.getLog().debug(
					"Setting discoverMultiplicityByPersistenceAnnotations to:"
							+ discoverMultiplicityByPersistenceAnnotations);
			JsignerConfiguration
					.setMultiplicityFinder(new PersistenceMultiplicityFinder());
		} else {
			JsignerConfiguration
					.setMultiplicityFinder(new CollectionMultiplicityFinder());
		}
	}

	private void checkPreConditions() throws MojoFailureException {
		JsignerLog log = JsignerConfiguration.getLog();

		if (jarsFolder == null) {
			throw new MojoFailureException(
					"Variable outputFolder must be set, please verify the plugin configuration.");
		}
		if (!jarsFolder.exists()) {
			throw new MojoFailureException(
					"jarsFolder '"
							+ jarsFolder
							+ "' doesn't exists! Aborting execution, please verify the plugin configuration.");
		}

		if (outputFolder == null) {
			throw new MojoFailureException(
					"Variable jars folder must be set, please verify the plugin configuration.");
		}
		if (!outputFolder.exists()) {
			log.info("outputFolder doesn't exists, trying to create it.");
			boolean created = outputFolder.mkdir();
			if (created) {
				log.info("OutputFolder created!");
			} else {
				throw new RuntimeException("Can't create outputFolder!");
			}
		}
		if (!outputFolder.isDirectory()) {
			outputFolder.delete();
			throw new MojoFailureException(
					"Variable outputFolder must be a Directory!");
		}
	}

	private JsignerLog prepareLog() {
		LogAdapter logAdapter = new LogAdapter(getLog());
		JsignerLog jsignerLog = JsignerConfiguration.getLog();
		jsignerLog.registerObserver(logAdapter);

		return jsignerLog;
	}
}
