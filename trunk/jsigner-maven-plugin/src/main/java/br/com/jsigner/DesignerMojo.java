package br.com.jsigner;

import java.io.File;
import java.net.MalformedURLException;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;

/**
 * @goal draw
 */
public class DesignerMojo extends AbstractMojo {

	/**
	 * @parameter default-value="target/"
	 */
	private File jar;

	public void execute() throws MojoExecutionException, MojoFailureException {
		Log log = getLog();
		log.info("Executing Jsigner maven plugin on" + jar.getAbsolutePath());	
		try {
			if (jar.exists()) {
				Jsigner.design(jar);
				log.info("diagram draw finished ");
			} else {
				log.info("path not found: " + jar.getAbsolutePath());
				log.info("Aborting execution!");
				throw new MojoFailureException("path not found: " + jar.getAbsolutePath());
			}
		} catch (MalformedURLException e) {
			log.error(e);
			throw new MojoExecutionException(e.getMessage());
		}
	}

}
