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
import java.util.Set;

import org.junit.Test;

import br.com.jsigner.log.JsignerLog;
import br.com.jsigner.scanner.JarScanner;

public class ManualTester {

	@Test
	public void execute() throws MalformedURLException {
		File file = new File(
				"/home/rafael/desenvolvimento/projetos/cqa_lims/devel/implementation/ear");

		File outputFolder = new File("/home/rafael/diagramas/");
		Jsigner.design(generateURLs(outputFolder, new JsignerLog()),
				outputFolder);
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
}
