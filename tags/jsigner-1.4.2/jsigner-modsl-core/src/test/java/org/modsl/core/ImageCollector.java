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

package org.modsl.core;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;

import org.modsl.core.util.Utils;

public class ImageCollector {

    static final String IMG_TYPE = "png";
    
    List<String> urls = new LinkedList<String>();

    String name;
    String path;

    public ImageCollector(String path, String name) {
        this.path = path;
        this.name = name;
    }

    public void collect(String graph, BufferedImage image) throws IOException {
        String n = name + "_" + graph + "." + IMG_TYPE;
        ImageIO.write(image, IMG_TYPE, new File(path + "/" + n));
        urls.add(n);
        Utils.toFile(path + "/" + name + ".html", html());
    }

    private String html() {
        StringBuilder sb = new StringBuilder("<html>\n\t<header>\n\t\t<title>");
        sb.append(name).append("</title>\n\t<header>\n\t<body>");
        for (int i = 0; i < urls.size(); i++) {
            String url = urls.get(i);
            sb.append("\n\t<img src=\"").append(url).append("\"/>");
        }
        sb.append("\n\t</body>\n</html>");
        return sb.toString();
    }

}
