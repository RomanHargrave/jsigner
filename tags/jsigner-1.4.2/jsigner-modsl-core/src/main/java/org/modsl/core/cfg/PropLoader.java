/**
 * Copyright 2008 Andrew Vishnyakov <avishn@gmail.com>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package org.modsl.core.cfg;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

/**
 * Base class for all property loaders
 * @author avishnyakov
 */
public class PropLoader {

    Logger log = Logger.getLogger(getClass());

    Map<String, String> props = new HashMap<String, String>();
    List<String> dirs = new ArrayList<String>();

    String name;
    boolean optional;

    /**
     * Create new
     * @param path
     * @param name
     * @param optional if true the loader will ignore the missing configuration files
     */
    public PropLoader(String path, String name, boolean optional) {
        this.name = name;
        this.optional = optional;
        tokenizePath(path);
    }

    /**
     * @param key
     * @return String property
     */
    public String getProp(String key) {
        String v = props.get(key);
        if (v != null) {
            v.trim();
        }
        return v;
    }

    /**
     * @return map of all properties
     */
    public Map<String, String> getProps() {
        return props;
    }

    /**
     * Load property set from given path for specific diagram name
     */
    public void load() {
        for (String dir : dirs) {
            loadProps(dir + "/" + name + ".properties");
        }
        if (!optional && props.size() == 0) {
            throw new ConfigException("Property set is empty for path=" + dirs + ", name=" + name);
        }
    }

    /**
     * Load properties
     * @param name file name
     * @throws IOException
     */
    private void loadProps(String name) {
        try {
            ClassLoader cl = Thread.currentThread().getContextClassLoader();
            InputStream is = cl.getResourceAsStream(name);
            if (is == null) {
                cl = this.getClass().getClassLoader();
                is = cl.getResourceAsStream(name);
            }
            if (is == null) {
                return;
            }
            Properties p = new Properties();
            p.load(is);
            is.close();
            for (Map.Entry<Object, Object> me : p.entrySet()) {
                props.put((String) me.getKey(), (String) me.getValue());
            }
        } catch (IOException ex) {
            log.debug("Got exception when loading properties from " + name, ex);
        }
    }

    /**
     * Will tokenize colon-delimited path into a list of dirs
     * @param path
     */
    private void tokenizePath(String path) {
        StringTokenizer tokenizer = new StringTokenizer(path, ":", false);
        while (tokenizer.hasMoreElements()) {
            dirs.add((String) tokenizer.nextElement());
        }
    }

}
