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

package br.com.jsigner.scanner;

import java.io.File;
import java.net.MalformedURLException;
import java.util.HashSet;
import java.util.Set;

import sun.misc.JarFilter;

public class JarScanner {
	private Set<File> jarURLs = new HashSet<File>();

	public Set<File> scan(File rootFolder) {
		try {
			this.scanPathsFromRoot(rootFolder);
			return jarURLs;
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}
	
	private void scanPathsFromRoot(File root) throws MalformedURLException {
		File[] files = root.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				this.scanPathsFromRoot(file);
			} else if (isJar(root, file)) {
				jarURLs.add(file);
			}
		}
	}

	private boolean isJar(File root, File file) {
		JarFilter jarFilter = new JarFilter();
		return jarFilter.accept(root, file.getName());
	}
	
	
}
