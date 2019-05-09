/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.yracnet.util;

import ca.mcgill.cs.jetuml.diagram.Diagram;
import ca.mcgill.cs.jetuml.diagram.DiagramType;
import ca.mcgill.cs.jetuml.diagram.Node;
import ca.mcgill.cs.jetuml.diagram.nodes.ImplicitParameterNode;
import ca.mcgill.cs.jetuml.geom.Rectangle;
import ca.mcgill.cs.jetuml.views.DiagramView;
import java.util.Objects;
import java.util.concurrent.FutureTask;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 *
 * @author yracnet
 */
public class JetumlUtilty {

  public static ImplicitParameterNode searchOrCreate(String name, Diagram diagram) {
    int count = 0;
    for (Node item : diagram.rootNodes()) {
      if (item instanceof ImplicitParameterNode) {
        count++;
        ImplicitParameterNode nn = (ImplicitParameterNode) item;
        //System.out.println("????--->" + name + " ==>" + nn);
        if (Objects.equals(nn.getName(), name)) {
          //System.out.println("SEARH--->" + name + " ==>" + nn);
          return nn;
        }
      }
    }
    ImplicitParameterNode node = new ImplicitParameterNode();
    node.setName(name);
		node.position().setX(150 * count);
    node.position().setY(20);
    diagram.addRootNode(node);
    return node;
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
