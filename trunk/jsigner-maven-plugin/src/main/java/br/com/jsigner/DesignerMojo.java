package br.com.jsigner;

import java.io.File;
import java.net.MalformedURLException;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;

/**
 * @goal design
 */
public class DesignerMojo extends AbstractMojo {

	/**
	 * @parameter default-value="target/"
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
				log.info("path not found: " + jarsFolder.getAbsolutePath());
				log.info("Aborting execution!");
				throw new MojoFailureException("path not found: "
						+ jarsFolder.getAbsolutePath());
			}
		} catch (MalformedURLException e) {
			throw new MojoExecutionException(e.getMessage(), e);
		} catch (RuntimeException e) {
			throw new MojoExecutionException(e.getMessage(), e);
		}
	}

	private void checkPreConditions() throws MojoFailureException {
		if (outputFolder == null) {
			throw new MojoFailureException("Variable outputFolder must be set!");
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
