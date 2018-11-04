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
import java.util.ArrayList;
import java.util.List;
import net.yracnet.data.SeqCaller;
import net.yracnet.data.SeqRoot;
import net.yracnet.data.JetConfig;

/**
 *
 * @author yracnet
 */
public class SequenceVisitor extends GenericVisitorAdapter {

  private final JetConfig draw;
  private final SeqRoot root;

  SequenceVisitor(JetConfig draw, SeqRoot root) {
    this.draw = draw;
    this.root = root;
  }

  @Override
  public Object visit(ClassOrInterfaceDeclaration n, Object arg) {
    String name = n.getNameAsString();
    //System.out.println("ClassOrInterfaceDeclaration: " + name);
    boolean ignore = draw.isIgnore(name);
    root.complete();
    root.setName(name);
    root.setIgnore(ignore);
    return super.visit(n, arg);
  }

  @Override
  public Object visit(VariableDeclarator n, Object arg) {
    String name = n.getNameAsString();
    String type = n.getTypeAsString();
    root.addRef(name, type);
    //String singnature = n.getTypeAsString() + ":" + n.getNameAsString();
    //System.out.println("VariableDeclarator > " + singnature);
    return super.visit(n, arg);
  }

  @Override
  public Object visit(MethodDeclaration n, Object arg) {
    //String singnature = n.getSignature() + " : " + n.getTypeAsString();
    SeqCaller caller = new SeqCaller(n.getNameAsString());
    caller.setSignature(n.getSignature() + "");
    caller.setResult(n.getTypeAsString());
    //SeqCaller caller = new SeqCaller(n.getSignature() + "", n.getTypeAsString());
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
    //System.out.println("-------------->" + n + " - " + n.getClass());
    Expression e = n.getScope().isPresent() ? n.getScope().get() : null;
    if (e instanceof NameExpr) {
      NameExpr ne = (NameExpr) e;
      service = ne.getNameAsString();
      boolean isInclude = draw.isInclude(service);
      if (isInclude) {
        root.addRef(service, service);
      }
      String type = root.getRef(service);
      if (type != null) {
        System.out.println("---->" + service + "." + method + " : " + type);
        SeqRoot other = draw.createRoot(type);
        //SeqRoot other = new SeqRoot(type);
        SeqCaller caller = new SeqCaller(n.getNameAsString());
        caller.setSignature(n.getNameAsString());
        caller.setResult(service);
        //SeqCaller caller = new SeqCaller(n.getNameAsString(), service);
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
