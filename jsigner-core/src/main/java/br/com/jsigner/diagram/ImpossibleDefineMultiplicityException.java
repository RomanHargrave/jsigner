package br.com.jsigner.diagram;

import java.lang.reflect.Field;

public class ImpossibleDefineMultiplicityException extends RuntimeException {

	private static final long serialVersionUID = -5685081505713145406L;

	public ImpossibleDefineMultiplicityException(Class<?> clazz, Field field) {
		super("Impossible to define multiplicity for the relationship:"
				+ clazz.getSimpleName() + " -> " + field.getName());
	}
}
