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
package net.yracnet.util;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import net.yracnet.data.SourceEntry;

/**
 *
 * @author yracnet
 */
public class ParseUtility {
	static FileFilter JAVA_FILTER = (File file) -> {
		return file.isDirectory() || file.getName().endsWith(".java");
	};

	public static List<SourceEntry> processDirectory(List<File> rootList) {
		List<SourceEntry> result = new ArrayList<>();
		rootList.forEach(root -> {
			for (File item : root.listFiles(JAVA_FILTER)) {
				processDirectory(item, result);
			}
		});
		return result;
	}

	public static void processDirectory(File item, List<SourceEntry> list) {
		if (item.isDirectory()) {
			for (File subitem : item.listFiles(JAVA_FILTER)) {
				processDirectory(subitem, list);
			}
		} else {
			try {
				CompilationUnit cu = JavaParser.parse(item);
				SourceEntry entry = new SourceEntry(item, cu);
				list.add(entry);
				// System.out.println("--->" + item + " - " + entry);
			} catch (FileNotFoundException e) {
				System.out.println("Error: " + e.getMessage());
			}
		}
	}
}
