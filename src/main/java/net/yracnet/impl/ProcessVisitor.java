/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.yracnet.impl;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.visitor.GenericVisitorAdapter;
import net.yracnet.data.SourceEntry;
import net.yracnet.spec.Context;

/**
 *
 * @author yracnet
 */
public class ProcessVisitor extends GenericVisitorAdapter {

  protected final Context ctx;
  protected final SourceEntry src;

  public ProcessVisitor(Context ctx, SourceEntry src) {
    this.ctx = ctx;
    this.src = src;
  }

  @Override
  public Object visit(VariableDeclarator n, Object arg) {
    addReference(n, arg);
    Object result = super.visit(n, arg);
    postVisit(n, arg);
    return result;
  }

  private void addReference(VariableDeclarator n, Object arg) {
    String name = n.getNameAsString();
    String type = n.getTypeAsString();
    boolean isIgnore = ctx.isIgnore(type);
    SourceEntry source = ctx.findClass("*", type);
    if (!isIgnore && source != null) {
      src.addRef(name, type);
      //System.out.println("ADD VariableDeclarator > " + n);
    }
  }

  public void postVisit(VariableDeclarator n, Object arg) {
  }

  @Override
  public Object visit(MethodCallExpr n, Object arg) {
    String value[] = addReference(n, arg);
    Object result = super.visit(n, arg);
    postVisit(n, arg, value[0], value[1]);
    return result;
  }

  public void postVisit(MethodCallExpr n, Object arg, String scope, String name) {
  }

  private String[] addReference(MethodCallExpr n, Object arg) {
    String result[] = new String[]{null, n.getNameAsString()};
    Expression e = n.getScope().isPresent() ? n.getScope().get() : null;
    if (e instanceof NameExpr) {
      NameExpr ne = (NameExpr) e;
      String scope = ne.getNameAsString();
      result[0] = scope;
      boolean isInclude = ctx.isInclude(scope);
      if (isInclude) {
        src.addRef(scope, scope);
      }
    }
    return result;
  }

  @Override
  public Object visit(MethodDeclaration n, Object arg) {
    Object result = super.visit(n, arg);
    postVisit(n, arg);
    return result;
  }

  public void postVisit(MethodDeclaration n, Object arg) {
  }
}
