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
package net.yracnet.visitor;

import net.yracnet.impl.ProcessVisitor;
import ca.mcgill.cs.jetuml.diagram.SequenceDiagram;
import ca.mcgill.cs.jetuml.diagram.edges.CallEdge;
import ca.mcgill.cs.jetuml.diagram.edges.ReturnEdge;
import ca.mcgill.cs.jetuml.diagram.nodes.CallNode;
import ca.mcgill.cs.jetuml.diagram.nodes.ImplicitParameterNode;
import com.github.javaparser.ast.body.MethodDeclaration;
import java.io.File;
import net.yracnet.data.SourceMethod;
import net.yracnet.data.SourceEntry;
import net.yracnet.spec.Context;
import net.yracnet.spec.Writter;

/**
 *
 * @author yracnet
 */
public class SequenceDiagramVisitor extends ProcessVisitor {
	private Writter out;

	public SequenceDiagramVisitor(Context ctx, SourceEntry src, Writter out) {
		super(ctx, src);
		this.out = out;
	}

	@Override
	public void postVisit(MethodDeclaration n, Object arg) {
		System.out.println("=====================================");
		System.out.println("CREATE MethodDeclaration > " + n.getSignature());
		SequenceDiagram diagram = new SequenceDiagram();
		ImplicitParameterNode callNodeBrowser = new ImplicitParameterNode();
		callNodeBrowser.setName("Browser/Controller");
		diagram.addRootNode(callNodeBrowser);
		CallNode callNodeFrom = new CallNode();
		callNodeBrowser.addChild(callNodeFrom);
		CallEdge callEdge = new CallEdge();
		callEdge.setMiddleLabel("???");
		diagram.addEdge(callEdge);
		ReturnEdge returnEdge = new ReturnEdge();
		returnEdge.setMiddleLabel("void");
		diagram.addEdge(returnEdge);
		SourceMethod method = new SourceMethod(n);
		SequenceCallerVisitor visitor = new SequenceCallerVisitor(ctx, src, method, diagram);
		visitor.visit(src.getCunit(), arg);
		// visitor.getCallNode().setOpenBottom(true);
		CallNode callNodeTo = visitor.getCallNode();
		callEdge.setMiddleLabel(method.getLabel());
		returnEdge.setMiddleLabel(visitor.getResult());
		callEdge.connect(callNodeFrom, callNodeTo, diagram);
		returnEdge.connect(callNodeTo, callNodeFrom, diagram);
		// File fileJet = src.getNewFile("-" + n.getNameAsString() + "-sequence.jet");
		// out.writeJet(fileJet, diagram);
		File filePng = src.getNewFile("-" + n.getNameAsString() + "-sequence.png");
		filePng.delete();
		out.writeImage(filePng, diagram);
	}
}
