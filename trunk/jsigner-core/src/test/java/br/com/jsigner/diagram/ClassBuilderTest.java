package br.com.jsigner.diagram;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import br.com.jsigner.diagram.ClassesBuilder;

public class ClassBuilderTest {

	@Test
	public void test() {
		
		List<Class<?>> diagramClasses = new ArrayList<Class<?>>();
		diagramClasses.add(RelationshipBuilderTest.CarModel.class);
		diagramClasses.add(RelationshipBuilderTest.CarStore.class);
		diagramClasses.add(RelationshipBuilderTest.Driver.class);
		diagramClasses.add(RelationshipBuilderTest.Engine.class);
		diagramClasses.add(RelationshipBuilderTest.Mirror.class);
		
		String code = ClassesBuilder.generateClassesCode(diagramClasses);
	}
	
	
}
