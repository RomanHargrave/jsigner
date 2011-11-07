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

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;

public class ThreadLocalAppender extends AppenderSkeleton {

    protected void append(LoggingEvent event) {
        ThreadLocalContainer.get().addLogMessage(layout.format(event));
    }

    public boolean requiresLayout() {
        return true;
    }

    public void close() {
        ThreadLocalContainer.get().logMessages.clear();
    }

    @Override
    public void activateOptions() {
        super.activateOptions();
        ThreadLocalContainer.get().logMessages.clear();
    }

}
