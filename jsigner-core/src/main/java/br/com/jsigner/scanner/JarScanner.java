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
