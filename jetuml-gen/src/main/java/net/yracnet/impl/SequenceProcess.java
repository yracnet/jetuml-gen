/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.yracnet.impl;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import net.yracnet.visitor.SequenceDiagramVisitor;
import net.yracnet.data.SourceEntry;
import net.yracnet.spec.Context;
import net.yracnet.spec.Process;

/**
 *
 * @author yracnet
 */
public class SequenceProcess implements Process {

  public static class FXStarter extends Application {

    @Override
    public void init() {
    }

    @Override
    public void start(Stage primaryStage) {
    }
  }

  public void startFx() {
    try {
      System.setProperty("apple.laf.useScreenMenuBar", "true");
      new Thread(() -> Application.launch(FXStarter.class)).start();
    } finally {
    }
  }

  public void closeFx() {
    try {
      Platform.exit();
    } finally {
    }
  }

  @Override
  public void process(Context ctx, SourceEntry src) {
    SequenceDiagramVisitor diagramVisitor = new SequenceDiagramVisitor(ctx, src);
    diagramVisitor.visit(src.getCunit(), null);
  }
}
