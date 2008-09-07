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

import static org.junit.Assert.assertTrue;

import java.lang.reflect.Method;

import org.junit.Test;

import br.com.jsigner.interpreter.MethodVisitor;
import br.com.jsigner.modsl.interpreter.ModslMethodVisitor;

public class MethodTest {

	@Test
	public void methodTest() {

		Method[] declaredMethods = this.getClass().getDeclaredMethods();
		ModslMethodVisitor visitor = new ModslMethodVisitor();
		for (Method method : declaredMethods) {
			br.com.jsigner.diagram.elements.method.Method m = new br.com.jsigner.diagram.elements.method.Method(
					method);
			visitor.visit(m);
		}
		
		String code = visitor.getResult();

		assertTrue(code.contains("- privateStringMethod():String"));
		assertTrue(code.contains("# protectedVoidMethod():void;"));
		assertTrue(code
				.contains("+ publicFinalStringMethod(String, MethodVisitor):String;"));
		assertTrue(code.contains("# static protectedStaticIntMethod():int;"));

	}

	@SuppressWarnings("unused")
	private String privateStringMethod() {
		return null;
	}

	protected void protectedVoidMethod() {
	}

	public final String publicFinalStringMethod(String mimi, MethodVisitor m) {
		return null;
	}

	protected static int protectedStaticIntMethod() {
		return 0;
	}

	public abstract static class AbstractTestClass {

		public abstract MethodTest publicAbstractMethodBuilderTestMethod();
	}

}
