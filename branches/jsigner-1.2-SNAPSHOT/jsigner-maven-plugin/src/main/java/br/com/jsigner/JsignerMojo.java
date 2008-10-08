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
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;

import br.com.jsigner.diagram.elements.relationship.multiplicity.CollectionMultiplicityFinder;
import br.com.jsigner.diagram.elements.relationship.multiplicity.PersistenceMultiplicityFinder;
import br.com.jsigner.log.JsignerLog;
import br.com.jsigner.log.LogAdapter;
import br.com.jsigner.scanner.JarScanner;

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

	/**
	 * @parameter expression="${project}"
	 * @required
	 * @readonly
	 */
	private MavenProject project;

	public void execute() throws MojoExecutionException, MojoFailureException {
		checkPreConditions();
		configurePlugin();

		JsignerLog log = this.prepareLog();

		try {
			URL[] urls;
			if (jarsFolder != null) {
				log.info("Executing Jsigner maven plugin on "
						+ jarsFolder.getAbsolutePath());
				urls = generateURLs(jarsFolder, log);
			} else {
				log.info("Executing Jsigner maven plugin on classpath");
				urls = loadURLsFromCLasspath(project, log);
			}

			Jsigner.design(urls, outputFolder);
			log.info("diagrams created at: " + outputFolder.getAbsolutePath());
		} catch (MalformedURLException e) {
			throw new MojoExecutionException(e.getMessage(), e);
		} catch (RuntimeException e) {
			throw new MojoExecutionException(e.getMessage(), e);
		}
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

		if (!jarsFolder.exists()) {
			throw new MojoFailureException(
					"jarsFolder '"
							+ jarsFolder
							+ "' doesn't exists! Aborting execution, please verify the plugin configuration.");
		}

		if (outputFolder == null) {
			throw new MojoFailureException(
					"Variable outputFolder must be set, please verify the plugin configuration.");
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

	private URL[] generateURLs(File f, JsignerLog log)
			throws MalformedURLException {
		JarScanner scanner = new JarScanner();
		Set<File> jars = scanner.scan(f);
		removeDuplicatedJars(jars, log);

		URL[] urls = new URL[jars.size()];
		int count = 0;
		for (File file : jars) {
			urls[count] = file.toURI().toURL();
			log.debug("adding jar for scanning: " + urls[count]);
			count++;
		}

		return urls;
	}

	private void removeDuplicatedJars(Set<File> jars, JsignerLog log) {
		Set<String> jarNames = new HashSet<String>();
		for (File file : jars) {
			if (jarNames.contains(file.getName())) {
				log.debug("Skiping duplicated jar:" + file.getName());
				jars.remove(file);
			}
		}
	}

	@SuppressWarnings("unchecked")
	private URL[] loadURLsFromCLasspath(MavenProject project, JsignerLog log)
			throws MalformedURLException {
		Set<File> fileSet = new HashSet<File>();

		Iterator i = project.getArtifacts().iterator();
		while (i.hasNext()) {
			Artifact artifact = (Artifact) i.next();
			fileSet.add(artifact.getFile());
		}

		removeDuplicatedJars(fileSet, log);

		URL[] urls = new URL[fileSet.size()];
		int count = 0;
		for (File file : fileSet) {
			urls[count] = file.toURI().toURL();
			log.debug("adding jar for scanning: " + urls[count]);
			count++;
		}

		return urls;
	}
}
