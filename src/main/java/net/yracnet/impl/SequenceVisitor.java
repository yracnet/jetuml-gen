/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.yracnet.impl;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.visitor.GenericVisitorAdapter;
import net.yracnet.data.SeqCaller;
import net.yracnet.data.SeqRoot;
import net.yracnet.data.SeqDraw;

/**
 *
 * @author yracnet
 */
public class SequenceVisitor extends GenericVisitorAdapter {

  private final SeqDraw draw;
  private final SeqRoot root;

  SequenceVisitor(SeqDraw draw, SeqRoot root) {
    this.draw = draw;
    this.root = root;
  }

  @Override
  public Object visit(ClassOrInterfaceDeclaration n, Object arg) {
    root.setName(n.getNameAsString());
    root.complete();
    System.out.println("ClassOrInterfaceDeclaration: " + n.getNameAsString());
    return super.visit(n, arg);
  }

  @Override
  public Object visit(VariableDeclarator n, Object arg) {
    root.addRef(n.getNameAsString(), n.getTypeAsString());
    //String singnature = n.getTypeAsString() + ":" + n.getNameAsString();
    //System.out.println("VariableDeclarator > " + singnature);
    return super.visit(n, arg);
  }

  @Override
  public Object visit(MethodDeclaration n, Object arg) {
    //String singnature = n.getSignature() + " : " + n.getTypeAsString();
    SeqCaller caller = new SeqCaller(n.getSignature() + "", n.getTypeAsString());
    //caller.setFrom(root);
    caller.setTo(root);
    draw.addCaller(caller);
    //System.out.println("MethodDeclaration > " + singnature);
    return super.visit(n, arg);
  }

  @Override
  public Object visit(MethodCallExpr n, Object arg) {
    String service = "?";
    String method = n.getNameAsString();
    String result = "?";
    if (!n.getScope().isPresent()) {
      return super.visit(n, arg);
    }
    Expression e = n.getScope().get();
    if (e instanceof NameExpr) {
      NameExpr ne = (NameExpr) e;
      service = ne.getNameAsString();
      String type = root.getRef(service);
      if (type != null) {
        System.out.println("---->" + service + "." + method + " : " + type);
        SeqRoot other = draw.createRoot(type);
        //SeqRoot other = new SeqRoot(type);
        SeqCaller caller = new SeqCaller(n.getNameAsString(), service);
        caller.setFrom(root);
        caller.setTo(other);
        draw.addCaller(caller);
        draw.addRoot(other);
      }
    }
    //System.out.println("MethodCallExpr > " + service + " - " + method + " : " + result);
    return super.visit(n, arg);
  }

}
