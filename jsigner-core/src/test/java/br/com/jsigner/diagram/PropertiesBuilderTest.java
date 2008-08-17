package br.com.jsigner.diagram;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import br.com.jsigner.diagram.ClassDiagram;
import br.com.jsigner.diagram.PropertiesBuilder;

public class PropertiesBuilderTest {
	
	private String string1;
	protected int int1;
	Boolean boolean1;
	static String string2;
	private PropertiesBuilderTest propertyBuilderTest;
	private ClassDiagram classDiagram;
	
	@Test
	public void test() {
		ArrayList<Class<?>> diagramClasses = new ArrayList<Class<?>>();
		diagramClasses.add(ClassDiagram.class);
		String properties = PropertiesBuilder.generatePropertiesCode(this.getClass(), diagramClasses);
		
		assertFalse(properties.contains("ClassDiagram"));
		assertTrue(properties.contains("string1:String;"));
		assertTrue(properties.contains("int1:int;"));
		assertTrue(properties.contains("boolean1:Boolean;"));
		assertTrue(properties.contains("string2:String;"));
		assertTrue(properties.contains("propertyBuilderTest:PropertiesBuilderTest;"));
	}
}
