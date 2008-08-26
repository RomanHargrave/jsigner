package br.com.jsigner;

import java.io.File;
import java.net.MalformedURLException;

import org.junit.Test;

public class ManualTester {
	
	@Test
	public void execute() throws MalformedURLException {
		File file = new File("/home/rafael/desenvolvimento/projetos/cqa_lims/devel/implementation/ear");
		
		File outputFolder = new File("/home/rafael/diagramas/");
		Jsigner.design(file, outputFolder);
	}
}
