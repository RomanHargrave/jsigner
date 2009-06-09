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

public class LogEvent {

	private Event event;
	private String message;
	
	public static enum Event {
		INFO, DEBUG, WARN, ERROR
	}

	public LogEvent(Event event, String message) {
		super();
		this.event = event;
		this.message = message;
	}

	public Event getType() {
		return event;
	}

	public String getMessage() {
		return message;
	}
}
