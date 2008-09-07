package br.com.jsigner.diagram.elements.method;

import java.lang.reflect.Modifier;

import br.com.jsigner.JsignerConfiguration;

public class MethodSpecification {

	public boolean isMethod(java.lang.reflect.Method method) {

		int mod = method.getModifiers();

		boolean isMethod = true;

		if (JsignerConfiguration.hidePrivateMethods()) {
			isMethod = isMethod && !Modifier.isPrivate(mod);
		}

		if (JsignerConfiguration.hideSetters()) {
			isMethod = isMethod && !method.getName().startsWith("set");
		}

		if (JsignerConfiguration.hideGetters()) {
			isMethod = isMethod && !method.getName().startsWith("get")
					&& !method.getName().startsWith("is");
		}
		
		return isMethod;
	}
}
