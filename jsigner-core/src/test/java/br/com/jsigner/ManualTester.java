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

package br.com.jsigner;

import java.io.File;
import java.net.MalformedURLException;

import org.junit.Test;

public class ManualTester {
	
	//@Test
	public void execute() throws MalformedURLException {
		File file = new File("/home/rafael/desenvolvimento/projetos/cqa_lims/devel/implementation/ear");
		
		File outputFolder = new File("/home/rafael/diagramas/");
		Jsigner jsigner = new Jsigner();
		//jsigner.design(file, outputFolder);
		
	}
}
