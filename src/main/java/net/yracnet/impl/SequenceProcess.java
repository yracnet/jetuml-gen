/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.yracnet.impl;

import net.yracnet.visitor.SequenceDiagramVisitor;
import net.yracnet.data.SourceEntry;
import net.yracnet.spec.Context;
import net.yracnet.spec.Process;

/**
 *
 * @author yracnet
 */
public class SequenceProcess implements Process {

  @Override
  public void process(Context ctx, SourceEntry src) {
    SequenceDiagramVisitor diagramVisitor = new SequenceDiagramVisitor(ctx, src);
    diagramVisitor.visit(src.getCunit(), null);
  }
}
