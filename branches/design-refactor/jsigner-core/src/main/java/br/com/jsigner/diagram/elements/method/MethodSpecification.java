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

package br.com.jsigner.diagram.elements.method;

import java.lang.reflect.Modifier;

import br.com.jsigner.JsignerConfiguration;

public class MethodSpecification {

	public boolean isMethod(java.lang.reflect.Method method) {

		int mod = method.getModifiers();

		boolean isMethod = true;

		if (JsignerConfiguration.hidePrivateMethods()) {
			isMethod = isMethod && !Modifier.isPrivate(mod);
		}

		if (JsignerConfiguration.hideSetters()) {
			isMethod = isMethod && !method.getName().startsWith("set");
		}

		if (JsignerConfiguration.hideGetters()) {
			isMethod = isMethod && !method.getName().startsWith("get")
					&& !method.getName().startsWith("is");
		}
		
		return isMethod;
	}
}
