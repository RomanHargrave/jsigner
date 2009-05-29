package br.com.jsigner.plugin;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.Thread.UncaughtExceptionHandler;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.maven.plugin.logging.Log;

import br.com.jsigner.Jsigner;
import br.com.jsigner.plugin.cpscanner.ClasspathScanner;

public class JsignerRunner implements Runnable, UncaughtExceptionHandler {

    private final Thread thread;

    private final Log log;

    private Throwable throwable;

    private List<File> projects;

    private File dest;

    public JsignerRunner(Log log, ClassLoader classloader, List<File> projects, File dest) {
        this.log = log;
        this.projects = projects;
        this.dest = dest;
        dest.mkdirs();
        thread = new Thread(this, "MycontainerRunner");
        thread.setContextClassLoader(classloader);

    }

    public void start() {
        thread.start();
    }

    public void join() throws InterruptedException {
        thread.join();
    }

    public void run() {
        try {
            log.info("Generating diagrams...");
            ClasspathScanner scanner = new ClasspathScanner();
            DomainScannerListener listener = new DomainScannerListener();
            listener.setLog(log);
            scanner.addListener(listener);
            for (File project : projects) {
                URL url = project.toURI().toURL();
                log.info("Scanning: " + url);
                scanner.scan(url);
            }

            Jsigner jsigner = new Jsigner();

            Map<String, List<Class<?>>> diagrams = listener.getDiagrams();
            for (Map.Entry<String, List<Class<?>>> entry : diagrams.entrySet()) {
                String name = entry.getKey();
                List<Class<?>> classes = entry.getValue();
                BufferedImage image = jsigner.design(name, classes);
                File file = new File(dest, name + ".png");
                ImageIO.write(image, "png", file);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void uncaughtException(Thread t, Throwable e) {
        this.throwable = e;
    }

}
