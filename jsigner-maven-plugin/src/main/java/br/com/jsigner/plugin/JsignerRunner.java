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

    private File outputFolder;

    public JsignerRunner(Log log, ClassLoader classloader, List<File> projects, File destination) {
        this.log = log;
        this.projects = projects;
        this.outputFolder = destination;
        destination.mkdirs();
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
                File file = new File(outputFolder, name + ".png");
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
