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
import java.util.ArrayList;
import java.util.List;

import br.com.jsigner.interpreter.MethodVisitor;

public class Method {

	private java.lang.reflect.Method wrappedMethod;
	private int mod;
	private List<String> parameters = new ArrayList<String>();

	public Method(java.lang.reflect.Method wrappedMethod) {
		super();
		this.wrappedMethod = wrappedMethod;
		this.mod = wrappedMethod.getModifiers();

		Class<?>[] parameterTypes = wrappedMethod.getParameterTypes();
		for (Class<?> clazz : parameterTypes) {
			parameters.add(clazz.getSimpleName());
		}
	}
	
	public String getName() {
		return wrappedMethod.getName();
	}

	public String getReturnType() {
		return wrappedMethod.getReturnType().getSimpleName();
	}

	public boolean isAbstract() {
		return Modifier.isAbstract(mod);
	}

	public boolean isStatic() {
		return Modifier.isStatic(mod);
	}

	public boolean isProtected() {
		return Modifier.isProtected(mod);
	}

	public boolean isPublic() {
		return Modifier.isPublic(mod);
	}

	public boolean isPrivate() {
		return Modifier.isPrivate(mod);
	}

	public List<String> getParameters() {
		return parameters;
	}

	public void accept(MethodVisitor methodVisitor) {
		methodVisitor.visit(this);
	}
}
