/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.yracnet.visitor;

import net.yracnet.impl.ProcessVisitor;
import ca.mcgill.cs.jetuml.diagram.SequenceDiagram;
import ca.mcgill.cs.jetuml.persistence.PersistenceService;
import com.github.javaparser.ast.body.MethodDeclaration;
import java.io.File;
import net.yracnet.data.SourceMethod;
import net.yracnet.data.SourceEntry;
import net.yracnet.spec.Context;

/**
 *
 * @author yracnet
 */
public class SequenceDiagramVisitor extends ProcessVisitor {

  public SequenceDiagramVisitor(Context ctx, SourceEntry src) {
    super(ctx, src);
  }

  @Override
  public void postVisit(MethodDeclaration n, Object arg) {
    System.out.println("=====================================");
    File file = src.getDrawFile("-" + n.getNameAsString() + "-sequence");
    String singnature = n.getSignature() + " : " + n.getTypeAsString();
    System.out.println("CREATE MethodDeclaration > " + singnature + " - " + file);
    SequenceDiagram diagram = new SequenceDiagram();
    SourceMethod method = new SourceMethod(n);
    SequenceCallerVisitor visitor = new SequenceCallerVisitor(ctx, src, method, diagram);
    visitor.visit(src.getCunit(), arg);
    visitor.getCallNode().setOpenBottom(true);
    try {
      System.out.println("SAVE PersistenceService-->" + diagram);
      PersistenceService.save(diagram, file);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
