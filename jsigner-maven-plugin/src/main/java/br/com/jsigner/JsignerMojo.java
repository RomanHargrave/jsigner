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
import java.net.MalformedURLException;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;

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

	public void execute() throws MojoExecutionException, MojoFailureException {
		Log log = getLog();
		checkPreConditions();

		try {
			log.info("Executing Jsigner maven plugin on"
					+ jarsFolder.getAbsolutePath());

			if (jarsFolder.exists()) {
				Jsigner.design(jarsFolder, outputFolder);
				log.info("diagrams created at: "
						+ outputFolder.getAbsolutePath());
			} else {
				System.out.println("jarsFolder" + jarsFolder.toString() + " doesn't exists, ignoring it...");
			}
		} catch (MalformedURLException e) {
			throw new MojoExecutionException(e.getMessage(), e);
		} catch (RuntimeException e) {
			throw new MojoExecutionException(e.getMessage(), e);
		}
	}

	private void checkPreConditions() throws MojoFailureException {
		if (jarsFolder == null) {
			throw new MojoFailureException("Variable outputFolder must be set, please verify the plugin configuration.");
		}
		if (!jarsFolder.exists()) {
			throw new MojoFailureException("jarsFolder '"+ jarsFolder  +"' doesn't exists! Aborting execution, please verify the plugin configuration.");
		}

		if (outputFolder == null) {
			throw new MojoFailureException("Variable jars folder must be set, please verify the plugin configuration.");
		}
		if (!outputFolder.exists()) {
			System.out
					.println("outputFolder doesn't exists, trying to create it.");
			boolean created = outputFolder.mkdir();
			if (created) {
				System.out.println("OutputFolder created!");
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

}