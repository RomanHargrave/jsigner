package br.com.jsigner.diagram;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.junit.Test;

import br.com.jsigner.diagram.RelationshipsBuilder;

public class RelationshipBuilderTest {
	List<CarModel> models = new ArrayList<CarModel>();

	@Test
	public void test() {

		List<Class<?>> diagramClasses = new ArrayList<Class<?>>();
		diagramClasses.add(CarModel.class);
		diagramClasses.add(Mirror.class);
		diagramClasses.add(Engine.class);
		diagramClasses.add(CarStore.class);

		String code = RelationshipsBuilder.generateRelationShipsCode(
				CarModel.class, diagramClasses);
		
		System.out.println(code);
	}
	

	public static class CarModel {
		@OneToOne
		private Engine engine;

		private List<Mirror> mirror;

		@ManyToMany
		private List<CarStore> carStore;

		@OneToMany
		public List<Mirror> getMirror() {
			return mirror;
		}

		@OneToOne
		public Driver getDriver() {
			return null;
		}

	}

	public static class Driver {

	}

	public static class Engine {

	}

	public static class Mirror {

	}

	public static class CarStore {

	}

}
