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

package br.com.jsigner.diagram.elements.attribute;

import static org.junit.Assert.*;

import java.lang.reflect.Field;

import org.junit.Test;

import br.com.jsigner.modsl.interpreter.ModslAttributeVisitor;

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
		
		ModslAttributeVisitor visitor = new ModslAttributeVisitor();
		attribute.accept(visitor);
		attribute2.accept(visitor);
		String result = visitor.getResult();
		
		assertTrue(result.equals("attribute1:String;attribute2:Double;"));
	}
	
}
