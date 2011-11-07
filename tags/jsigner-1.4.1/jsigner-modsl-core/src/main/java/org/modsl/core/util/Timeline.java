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

/**
 * Represents timeline as named points in time
 * @author avishnyakov
 */
public class Timeline {

	protected long createdTimestamp = System.currentTimeMillis();
	protected List<Timestamp> timestamps = new LinkedList<Timestamp>();
	protected long lastTimestamp = createdTimestamp;

	/**
	 * Add new timestamp with current time and given name
	 * @param name
	 */
	public void timestamp(String name) {
		long newLastTimestamp = System.currentTimeMillis();
		timestamps.add(new Timestamp(name, newLastTimestamp - this.lastTimestamp));
		this.lastTimestamp = newLastTimestamp;
	}

	@Override
	public String toString() {
		return timestamps.toString() + ":" + (lastTimestamp - createdTimestamp);
	}

}
