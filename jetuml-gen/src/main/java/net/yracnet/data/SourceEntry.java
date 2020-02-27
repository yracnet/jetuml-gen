/**
 * Copyright © ${project.inceptionYear} ${owner} (${email})
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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.yracnet.data;

import com.github.javaparser.ast.CompilationUnit;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author yracnet
 */
public class SourceEntry {
	private final File file;
	private final String packageName;
	private final String className;
	private String label;
	private final CompilationUnit cunit;
	private final Map<String, String> ref = new HashMap<>();

	public SourceEntry(File file, CompilationUnit cunit) {
		this.file = file;
		this.cunit = cunit;
		this.className = file.getName().replace(".java", "");
		this.label = className;
		this.packageName = cunit.getPackageDeclaration().get().getNameAsString();
	}

	public File getFile() {
		return file;
	}

	public File getNewFile(String sufix) {
		String name = file.getName().replace(".java", sufix);
		return new File(file.getParentFile(), name);
	}

	public String getPackageName() {
		return packageName;
	}

	public String getClassName() {
		return className;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public CompilationUnit getCunit() {
		return cunit;
	}

	public void addRef(String name, String value) {
		ref.put(name, value);
	}

	public String getRef(String name) {
		return name == null ? null : ref.get(name);
	}

	@Override
	public String toString() {
		return "SourceEntry{ packageName=" + packageName + ", mainName=" + className + '}';
	}
	public static final SourceEntry NONE = null;

	public boolean isInterface() {
		return className.endsWith("Serv");
	}
}
