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
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.scannotation.AnnotationDB;

import br.com.jsigner.annotations.Domain;
import br.com.jsigner.diagram.ClassDiagram;
import br.com.jsigner.diagram.DiagramBuilder;
import br.com.jsigner.drawwer.JsignerDesigner;
import br.com.jsigner.scanner.JarScanner;

public final class Jsigner {

	public static void design(File f, File outputFolder)
			throws MalformedURLException {
		URL[] urls = generateURLs(f);

		AnnotationDB db = new AnnotationDB();
		try {
			db.setScanClassAnnotations(true);
			db.setScanFieldAnnotations(false);
			db.setScanMethodAnnotations(false);
			db.setScanParameterAnnotations(false);

			db.scanArchives(urls);
			Set<String> classes = db.getAnnotationIndex().get(
					Domain.class.getName());

			List<Class<?>> diagramClasses = new ArrayList<Class<?>>();
			Iterator<String> iterator = classes.iterator();
			while (iterator.hasNext()) {
				String nextClazz = iterator.next();
				System.out.println("loading class: " + nextClazz);
				URLClassLoader classLoader = URLClassLoader.newInstance(urls,
						Thread.currentThread().getContextClassLoader());
				Class<?> clazz = classLoader.loadClass(nextClazz);
				diagramClasses.add(clazz);
			}
			DiagramBuilder builder = new DiagramBuilder();
			builder.build(diagramClasses);

			Collection<ClassDiagram> diagrams = builder.getDiagrams();
			
			JsignerDesigner designer = JsignerConfiguration.getJsignerDrawwer();
			for (ClassDiagram classDiagram : diagrams) {
				designer.execute(classDiagram, outputFolder);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
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
			System.out.println("adding jar for scanning: " + urls[count]);
			count++;
		}

		return urls;
	}

	private static void removeDuplicatedJars(Set<File> jars) {
		Set<String> jarNames = new HashSet<String>();
		for (File file : jars) {
			if (jarNames.contains(file.getName())) {
				System.out.println("Skiping duplicated jar:" + file.getName());
				jars.remove(file);
			}
		}
	}

}
