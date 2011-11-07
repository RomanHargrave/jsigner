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

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.maven.plugin.logging.Log;

import br.com.jsigner.plugin.cpscanner.ClassScannerListener;

public class DomainScannerListener extends ClassScannerListener {

    private final Map<String, List<Class<?>>> diagrams = new HashMap<String, List<Class<?>>>();

    private Log log;

    public void setLog(Log log) {
        this.log = log;
    }

    @Override
    public void classFound(URL base, Class<?> clazz) {
        try {
            Class domainClass = Thread.currentThread().getContextClassLoader().loadClass(
                    "br.com.jsigner.annotations.Domain");

            Object annotation = clazz.getAnnotation(domainClass);
            if (annotation == null) {
                return;
            }

            String[] names = (String[]) domainClass.getDeclaredMethod("value").invoke(annotation);

            log.info("Adding class: " + clazz.getName() + ": " + Arrays.toString(names));
            for (String name : names) {
                List<Class<?>> list = diagrams.get(name);
                if (list == null) {
                    list = new ArrayList<Class<?>>();
                    diagrams.put(name, list);
                }
                list.add(clazz);
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e);
        } catch (SecurityException e) {
            throw new IllegalArgumentException(e);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException(e);
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException(e);
        } catch (InvocationTargetException e) {
            throw new IllegalArgumentException(e);
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public Map<String, List<Class<?>>> getDiagrams() {
        return diagrams;
    }

    @Override
    protected void classNotFound(URL base, URL resource, Throwable e) {
        log.debug("Class not found: " + resource);

    }

}
