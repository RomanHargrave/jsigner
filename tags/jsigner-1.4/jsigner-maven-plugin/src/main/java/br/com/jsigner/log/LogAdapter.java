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

package br.com.jsigner.log;

import org.apache.maven.plugin.logging.Log;

public class LogAdapter implements LogObserver {

	private Log log;

	public LogAdapter(Log log) {
		super();
		this.log = log;
	}

	public void notify(LogEvent event) {
		switch (event.getType()) {
		case DEBUG:
			log.debug(event.getMessage());
			break;
		case ERROR:
			log.error(event.getMessage());
			break;
		case INFO:
			log.info(event.getMessage());
			break;
		case WARN:
			log.warn(event.getMessage());
			break;
		default:
			throw new RuntimeException("Unknow log event!");
		}
	}
}
