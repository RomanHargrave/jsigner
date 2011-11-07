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

import java.util.ArrayList;
import java.util.List;

import br.com.jsigner.log.LogEvent.Event;

public class JsignerLog {

	private List<LogObserver> observers = new ArrayList<LogObserver>();

	public void registerObserver(LogObserver observer) {
		this.observers.add(observer);
	}

	public void info(String info) {
		for (LogObserver observer : observers) {
			observer.notify(new LogEvent(Event.INFO, info));
		}
	}

	public void error(String error) {
		for (LogObserver observer : observers) {
			observer.notify(new LogEvent(Event.ERROR, error));
		}
	}

	public void debug(String debug) {
		for (LogObserver observer : observers) {
			observer.notify(new LogEvent(Event.DEBUG, debug));
		}
	}

	public void warn(String warn) {
		for (LogObserver observer : observers) {
			observer.notify(new LogEvent(Event.WARN, warn));
		}
	}
}
