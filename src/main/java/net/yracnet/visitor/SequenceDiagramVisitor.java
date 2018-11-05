/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.yracnet.visitor;

import ca.mcgill.cs.jetuml.diagram.Diagram;
import ca.mcgill.cs.jetuml.diagram.DiagramType;
import net.yracnet.impl.ProcessVisitor;
import ca.mcgill.cs.jetuml.diagram.SequenceDiagram;
import ca.mcgill.cs.jetuml.geom.Rectangle;
import ca.mcgill.cs.jetuml.persistence.PersistenceService;
import ca.mcgill.cs.jetuml.views.DiagramView;
import com.github.javaparser.ast.body.MethodDeclaration;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.FutureTask;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javax.imageio.ImageIO;
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
    File fileJet = src.getNewFile("-" + n.getNameAsString() + "-sequence.jet");
    File filePng = src.getNewFile("-" + n.getNameAsString() + "-sequence.png");
    String singnature = n.getSignature() + " : " + n.getTypeAsString();
    System.out.println("CREATE MethodDeclaration > " + singnature + " - " + fileJet);
    SequenceDiagram diagram = new SequenceDiagram();
    SourceMethod method = new SourceMethod(n);
    SequenceCallerVisitor visitor = new SequenceCallerVisitor(ctx, src, method, diagram);
    visitor.visit(src.getCunit(), arg);
    visitor.getCallNode().setOpenBottom(true);
    try {
      System.out.println("SAVE PersistenceService-->" + diagram);
      PersistenceService.save(diagram, fileJet);
      final Image image = createImage(diagram);
      //BufferedImage imageBase = new BufferedImage((int) image.getWidth(), (int) image.getHeight(), BufferedImage.TYPE_INT_ARGB);
      //BufferedImage imageAwt = SwingFXUtils.fromFXImage(image, imageBase);
      BufferedImage imageAwt = SwingFXUtils.fromFXImage(image, null);
      ImageIO.write(imageAwt, "png", filePng);
      System.out.println("SAVE========>" + filePng);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static final double LINE_WIDTH = 0.6;
  private static final int BUTTON_SIZE = 25;
  private static final int OFFSET = 3;
  private static final int DIAGRAM_PADDING = 4;

  public static Image createImage(Diagram pDiagram) throws Exception {
    assert pDiagram != null;
    DiagramView diagramView = DiagramType.newViewInstanceFor(pDiagram);
    Rectangle bounds = diagramView.getBounds();

    Canvas canvas = new Canvas(bounds.getWidth() + DIAGRAM_PADDING * 2,
            bounds.getHeight() + DIAGRAM_PADDING * 2);
    GraphicsContext context = canvas.getGraphicsContext2D();
    context.setLineWidth(LINE_WIDTH);
    context.setFill(Color.WHITE);
    context.translate(-bounds.getX() + DIAGRAM_PADDING, -bounds.getY() + DIAGRAM_PADDING);
    diagramView.draw(context);

    FutureTask<WritableImage> task = new FutureTask<>(() -> {
      new Scene(new Pane(canvas));
      WritableImage img = new WritableImage(bounds.getWidth() + DIAGRAM_PADDING * 2,
              bounds.getHeight() + DIAGRAM_PADDING * 2);
      canvas.snapshot(null, img);
      return img;
    });
    Platform.runLater(task);
    return task.get();
  }

}
