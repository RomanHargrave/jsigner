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
