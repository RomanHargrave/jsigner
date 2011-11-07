/**
 * Copyright 2008 Andrew Vishnyakov <avishn@gmail.com>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */

package org.modsl.core.util;

import java.util.LinkedList;
import java.util.List;

public class ThreadLocalContainer {

    private static ThreadLocal<ThreadLocalContainer> threadLocal = new ThreadLocal<ThreadLocalContainer>();

    List<String> logMessages = new LinkedList<String>();

    public static void set(ThreadLocalContainer tlc) {
        threadLocal.set(tlc);
    }

    public static ThreadLocalContainer get() {
        ThreadLocalContainer tlc = (ThreadLocalContainer) threadLocal.get();
        if (tlc == null) {
            tlc = new ThreadLocalContainer();
            set(tlc);
        }
        return tlc;
    }

    public void addLogMessage(String msg) {
        logMessages.add(msg);
    }

    public List<String> extractLogMessages() {
        List<String> temp = logMessages;
        logMessages = new LinkedList<String>();
        return temp;
    }

}
