package br.com.jsigner;

import java.awt.image.BufferedImage;
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

import javax.imageio.ImageIO;

import org.antlr.runtime.RecognitionException;
import org.modsl.core.lang.uml.UMLMetaType;
import org.modsl.core.lang.uml.UMLTranslator;
import org.modsl.core.render.StyleLoader;
import org.scannotation.AnnotationDB;

import br.com.agile.jsigner.Domain;
import br.com.jsigner.diagram.ClassDiagram;
import br.com.jsigner.diagram.DiagramBuilder;
import br.com.jsigner.scanner.JarScanner;

public class Jsigner {

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
				System.out.println("reading diagram class: " + nextClazz);
				URLClassLoader classLoader = URLClassLoader.newInstance(urls,
						Thread.currentThread().getContextClassLoader());
				Class<?> clazz = classLoader.loadClass(nextClazz);
				diagramClasses.add(clazz);
			}

			DiagramBuilder builder = new DiagramBuilder();
			builder.build(diagramClasses);

			Collection<ClassDiagram> diagrams = builder.getDiagrams();
			for (ClassDiagram classDiagram : diagrams) {
				buildImage(classDiagram, outputFolder);
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

	private static void buildImage(ClassDiagram diagram, File outputFolder) {
		StyleLoader stl = new StyleLoader();
		stl.load("cfg/uml:cfg", "uml", UMLMetaType.class);

		UMLTranslator translator = new UMLTranslator();

		try {
			String diagramCode = diagram.generateDiagramCode();
			BufferedImage image = translator.translate(diagramCode);

			String classDiagramPath = outputFolder.getAbsolutePath()
					+ File.separator + diagram.getName() + "ClassDiagram.png";
			File file = new File(classDiagramPath);
			System.out.println(classDiagramPath);
			ImageIO.write(image, "png", file);
		} catch (RecognitionException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
