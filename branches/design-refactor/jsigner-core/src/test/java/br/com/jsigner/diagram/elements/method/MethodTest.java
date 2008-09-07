package br.com.jsigner.diagram.elements.method;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.Method;

import org.junit.Test;

public class MethodTest {

	@Test
	public void methodTest() {

		Method[] declaredMethods = this.getClass().getDeclaredMethods();
		MethodVisitor visitor = new MethodVisitor();
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
