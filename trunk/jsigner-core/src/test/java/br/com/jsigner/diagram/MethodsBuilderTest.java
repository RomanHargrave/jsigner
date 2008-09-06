/**
 * Copyright (C) 2008 Jsigner <rafaferry@gmail.com>
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

package br.com.jsigner.diagram;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class MethodsBuilderTest {

	@Test
	public void test() {
		String code = MethodsBuilder.generateMethodsCode(this.getClass());
		assertFalse(code.contains("- privateStringMethod():String"));
		assertTrue(code.contains("# protectedVoidMethod():void;"));
		assertTrue(code
				.contains("+ publicFinalStringMethod(String, MethodsBuilder):String;"));
		assertTrue(code
				.contains("# static protectedStaticIntMethod():int;"));

		code = MethodsBuilder.generateMethodsCode(AbstractTestClass.class);
		assertTrue(code
				.contains("+ abstract publicAbstractMethodBuilderTestMethod():MethodsBuilderTest;"));

	}

	@SuppressWarnings("unused")
	private String privateStringMethod() {
		return null;
	}

	protected void protectedVoidMethod() {
	}

	public final String publicFinalStringMethod(String mimi, MethodsBuilder m) {
		return null;
	}

	protected static int protectedStaticIntMethod() {
		return 0;
	}

	public abstract static class AbstractTestClass {

		public abstract MethodsBuilderTest publicAbstractMethodBuilderTestMethod();
	}

}
