package br.com.jsigner.classpath;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.scannotation.AnnotationDB;

import br.com.jsigner.JsignerConfiguration;
import br.com.jsigner.annotations.Domain;
import br.com.jsigner.log.JsignerLog;
import br.com.jsigner.scanner.JarScanner;

public class ClasspathBuilder {

	private static JsignerLog log = JsignerConfiguration.getLog();

	public void buildClasspath(File directoryToScan) {
		URL[] urls;
		try {
			urls = generateURLs(directoryToScan);

			AnnotationDB db = new AnnotationDB();
			db.setScanClassAnnotations(true);
			db.setScanFieldAnnotations(false);
			db.setScanMethodAnnotations(false);
			db.setScanParameterAnnotations(false);

			db.scanArchives(urls);
			Set<String> classes = db.getAnnotationIndex().get(
					Domain.class.getName());

			List<Class<?>> diagramClasses = new ArrayList<Class<?>>();

			if (classes == null) {
				log.error("No @Domain annotations found, aborting execution!");
				throw new RuntimeException(
						"No @Domain annotations found, aborting execution!");
			}
			Iterator<String> iterator = classes.iterator();

			log.info("Starting loading classes");
			while (iterator.hasNext()) {
				String nextClazz = iterator.next();
				log.debug("loading class: " + nextClazz);
				URLClassLoader classLoader = URLClassLoader.newInstance(urls,
						Thread.currentThread().getContextClassLoader());
				Class<?> clazz = classLoader.loadClass(nextClazz);
				diagramClasses.add(clazz);
			}

			log.info(diagramClasses.size() + " Classes loaded");

			if (diagramClasses.size() == 0) {
				throw new RuntimeException(
						"Aborting execution, 0 classes found!");
			}
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	private static URL[] generateURLs(File f) throws MalformedURLException {
		JarScanner scanner = new JarScanner();
		Set<File> jars = scanner.scan(f);
		removeDuplicatedJars(jars);

		URL[] urls = new URL[jars.size()];
		int count = 0;
		for (File file : jars) {
			urls[count] = file.toURI().toURL();
			log.debug("adding jar for scanning: " + urls[count]);
			count++;
		}

		return urls;
	}

	private static void removeDuplicatedJars(Set<File> jars) {
		Set<String> jarNames = new HashSet<String>();
		for (File file : jars) {
			if (jarNames.contains(file.getName())) {
				log.debug("Skiping duplicated jar:" + file.getName());
				jars.remove(file);
			}
		}
	}

}
