/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.yracnet.visitor;

import net.yracnet.util.JetumlUtilty;
import ca.mcgill.cs.jetuml.diagram.SequenceDiagram;
import ca.mcgill.cs.jetuml.diagram.edges.CallEdge;
import ca.mcgill.cs.jetuml.diagram.edges.ReturnEdge;
import ca.mcgill.cs.jetuml.diagram.nodes.CallNode;
import ca.mcgill.cs.jetuml.diagram.nodes.ImplicitParameterNode;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import net.yracnet.data.SourceEntry;
import net.yracnet.data.SourceMethod;
import net.yracnet.impl.ProcessVisitor;
import net.yracnet.spec.Context;

/**
 *
 * @author yracnet
 */
public class SequenceCallerVisitor extends ProcessVisitor {

  private final SequenceDiagram diagram;
  private final SourceMethod method;
  private final ImplicitParameterNode node;
  private final CallNode callNode;
  private String result = "???";

  public SequenceCallerVisitor(Context ctx, SourceEntry src, SourceMethod method, SequenceDiagram diagram) {
    super(ctx, src);
    this.diagram = diagram;
    this.method = method;
    this.node = (ImplicitParameterNode) JetumlUtilty.searchOrCreate(src.getClassName(), diagram);
    this.callNode = new CallNode();
    this.node.addChild(callNode);
  }

  public ImplicitParameterNode getImplicitParameterNode() {
    return node;
  }

  public CallNode getCallNode() {
    return callNode;
  }

  public String getResult() {
    return result;
  }

  @Override
  public Object visit(ClassOrInterfaceDeclaration n, Object arg) {
    String name = n.getNameAsString();
    node.setName(name);
    System.out.println("**************ClassOrInterfaceDeclaration: " + name + " - " + node);
    return super.visit(n, arg);
  }

  @Override
  public Object visit(MethodDeclaration n, Object arg) {
    SourceMethod other = new SourceMethod(n);
    boolean isMethod = other.compare(method);
    Object value = null;
    if (isMethod) {
      value = super.visit(n, arg);
			method.setLabel(n.getSignature().asString());
      result = n.getTypeAsString();
    }
    return value;
  }

  @Override
  public void postVisit(MethodCallExpr n, Object arg, String scope, String method) {

    String type = src.getRef(scope);
    System.out.println("===============" + scope + " - " + method);
    if (type != null) {
      type = type.replace("Serv", "Impl");
      SourceEntry src2 = ctx.findClass("*", type);
      if (src2 != null) {
        CallEdge callEdge = new CallEdge();
        callEdge.setMiddleLabel(method);
        ReturnEdge returnEdge = new ReturnEdge();
        returnEdge.setMiddleLabel("???");

        diagram.addEdge(callEdge);
        diagram.addEdge(returnEdge);

        System.out.println("* MethodCallExpr > " + scope + " - " + method  + "--" + type + " - " + src2);
        SourceMethod method2 = new SourceMethod(n);
        SequenceCallerVisitor visitor = new SequenceCallerVisitor(ctx, src2, method2, diagram);
        visitor.visit(src2.getCunit(), arg);
        CallNode callNodeTo = visitor.getCallNode();
				//callEdge.setMiddleLabel(method);
				callEdge.setMiddleLabel(method2.getLabel());
        returnEdge.setMiddleLabel(visitor.getResult());
        callEdge.connect(callNode, callNodeTo, diagram);
        returnEdge.connect(callNodeTo, callNode, diagram);
      }
    }
  }

}
