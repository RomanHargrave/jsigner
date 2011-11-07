/**
 * Copyright 2008 Andrew Vishnyakov <avishn@gmail.com>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */

package org.modsl.core.agt.visitor;

import org.modsl.core.util.ModSLException;

/**
 * Thrown when tree post-processing fails because of unresolved nodde references
 * @author avishnyakov
 */
public class InvalidNodeNameException extends ModSLException {

	private static final long serialVersionUID = 1L;

	public InvalidNodeNameException() {
		super();
	}

	public InvalidNodeNameException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidNodeNameException(String message) {
		super(message);
	}

	public InvalidNodeNameException(Throwable cause) {
		super(cause);
	}

}
