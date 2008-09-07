package br.com.jsigner.diagram.elements.attribute;

import static org.junit.Assert.*;

import java.lang.reflect.Field;

import org.junit.Test;

import br.com.jsigner.interpreter.modsl.AttributeVisitor;

public class AttributeTest {
	
	@SuppressWarnings("unused")
	private String attribute1;
	@SuppressWarnings("unused")
	private Double attribute2;
	
	@Test
	public void attributeTest() throws Exception {
		Field field = this.getClass().getDeclaredField("attribute1");
		Field field2 = this.getClass().getDeclaredField("attribute2");
		Attribute attribute = new Attribute(field);
		Attribute attribute2 = new Attribute(field2);
		
		assertEquals("attribute1", attribute.getName());
		assertEquals("String", attribute.getType());
		
		AttributeVisitor visitor = new AttributeVisitor();
		attribute.accept(visitor);
		attribute2.accept(visitor);
		String result = visitor.getResult();
		
		assertTrue(result.equals("attribute1:String;attribute2:Double;"));
	}
	
}
