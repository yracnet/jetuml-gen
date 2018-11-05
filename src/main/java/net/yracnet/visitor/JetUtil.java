/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.yracnet.visitor;

import ca.mcgill.cs.jetuml.diagram.Diagram;
import ca.mcgill.cs.jetuml.diagram.Node;
import ca.mcgill.cs.jetuml.diagram.nodes.ImplicitParameterNode;
import java.util.Objects;

/**
 *
 * @author yracnet
 */
public class JetUtil {

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
    node.position().setX(200 * count);
    node.position().setY(20);
    diagram.addRootNode(node);
    return node;
  }

}
