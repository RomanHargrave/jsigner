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
package br.com.jsigner.plugin.cpscanner;

import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.List;

public class ClasspathScanner implements ScannerPlugin {

	private final List<ScannerPlugin> plugins = new ArrayList<ScannerPlugin>();

	private final List<ScannerListener> listeners = new ArrayList<ScannerListener>();

	public ClasspathScanner() {
		plugins.add(new ZipClasspathPlugin());
		plugins.add(new DirectoryClasspathPlugin());
	}

	public void addListener(ScannerListener listener) {
		this.listeners.add(listener);
	}

	public void scan(Class<?> clazz) {
		ProtectionDomain pd = clazz.getProtectionDomain();
		CodeSource cs = pd.getCodeSource();
		scan(cs.getLocation());
	}

	public void scan(URL url) {
		scan(url, url);
	}

	public void scan(URL base, URL url) {

		List<URL> files = listFiles(url);
		
		if (files == null) {
			return;
		}
		
		for (URL file : files) {
			fireEvent(base, file);
		}

		List<URL> dirs = listDirectories(url);
		for (URL dir : dirs) {
			scan(base, dir);
		}

	}

	private void fireEvent(URL base, URL file) {
		for (ScannerListener listener : listeners) {
			listener.resourceFound(base, file);
		}
	}

	public List<URL> listDirectories(URL directory) {
		for (ScannerPlugin plugin : plugins) {
			List<URL> ret = plugin.listDirectories(directory);
			if (ret != null) {
				return ret;
			}
		}

		throw new RuntimeException("ScannerPlugin not found: " + directory);
	}

	public List<URL> listFiles(URL directory) {
		for (ScannerPlugin plugin : plugins) {
			List<URL> ret = plugin.listFiles(directory);
			if (ret != null) {
				return ret;
			}
		}

		return null;
		// throw new RuntimeException("ScannerPlugin not found: " + directory);
	}

	public void scan(URL[] urls) {
		for (URL url : urls) {
			scan(url);
		}
	}

}
