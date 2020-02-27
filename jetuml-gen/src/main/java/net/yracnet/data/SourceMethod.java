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

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.yevdo.jwildcard.JWildcard;

/**
 *
 * @author yracnet
 */
public class SourceMethod {
	private final String type;
	private final String name;
	private final int size;
	private final String arg;
	private final String signature;
	private String label;

	public SourceMethod(MethodDeclaration n) {
		type = n.getTypeAsString();
		name = n.getNameAsString();
		size = n.getParameters().size();
		String t = "";
		for (Parameter p : n.getParameters()) {
			t = t + "|" + p.getTypeAsString();
		}
		arg = size == 0 ? "|" : t;
		signature = "[" + size + "]" + type + ":" + name + arg;
	}

	public SourceMethod(MethodCallExpr n) {
		type = "*";
		name = n.getNameAsString();
		size = n.getArguments().size();
		String t = "|*";
		arg = t;
		signature = "[" + size + "]" + type + ":" + name + arg;
	}

	public boolean compare(SourceMethod m) {
		boolean isEquals = JWildcard.matches(m.signature, signature);
		System.out.println("compare--->" + m.signature + " --- " + signature + " - " + isEquals);
		return isEquals;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
}
