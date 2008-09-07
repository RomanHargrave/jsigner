package br.com.jsigner.diagram.elements.method;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

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
}
