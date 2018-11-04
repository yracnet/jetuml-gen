/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.yracnet.impl;

import ca.mcgill.cs.jetuml.diagram.SequenceDiagram;
import ca.mcgill.cs.jetuml.persistence.PersistenceService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import net.yracnet.data.SeqCaller;
import net.yracnet.data.SeqRoot;
import net.yracnet.data.JetConfig;
import net.yracnet.data.SourceEntry;
import net.yracnet.spec.Context;
import net.yracnet.spec.Generator;

/**
 *
 * @author yracnet
 */
public class SequenceGenerator implements Generator {

  @Override
  public void start(Context ctx, SourceEntry src) {
    JetConfig draw = new JetConfig();
    draw.addIgnore("*Grouper");
    draw.addIgnore("EntityManager");
    draw.addIgnore("Query");
    draw.addInclude("*Mapper");
    
    SeqRoot root = draw.createRoot(src.getClassName());
    draw.setRoot(root);
    List<SeqRoot> rootList = new ArrayList<>();
    rootList.add(root);
    process(ctx, draw, rootList);
    List<SeqCaller> callerRoot = draw.getCallerFrom(root);

    for (SeqCaller caller : callerRoot) {
      File file = src.getDrawFile("-" + caller.getName() + "-sequence");
      SequenceDiagram diagram = new SequenceDiagram();
      
      try {
        PersistenceService.save(diagram, file);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

  }

  private void draw(JetConfig draw, SeqCaller caller, File file) {
    JsonObject root = new JsonObject();
    root.addProperty("diagram", "SequenceDiagram");
    root.addProperty("version", "2.2");
    JsonArray nodes = createNodes(draw);
    root.add("nodes", nodes);
    JsonArray edges = createEdges(draw, caller);
    root.add("edges", edges);
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    String json = gson.toJson(root);
    System.out.println("--->" + json);
    System.out.println("--->" + file);
    try {
      FileWriter write = new FileWriter(file);
      write.write(root.toString());
      write.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private JsonArray createNodes(JetConfig draw) {
    int index = 0;
    JsonArray array = new JsonArray();
    array.add(createNode(draw.getRoot()));
    for (SeqRoot item : draw.getRootNotIgnore()) {
      array.add(createNode(item));
    }
    return array;
  }

  private JsonObject createNode(SeqRoot root) {
    JsonObject obj = new JsonObject();
    obj.addProperty("id", root.getId());
    obj.addProperty("x", 10 + 150 * root.getId());
    obj.addProperty("y", 30);
    obj.addProperty("type", "ImplicitParameterNode");
    obj.addProperty("name", root.asLabel());
    obj.add("children", new JsonArray());
    return obj;
  }

  private void process(Context ctx, JetConfig draw, List<SeqRoot> rootList) {
    if (!rootList.isEmpty()) {
      for (SeqRoot item : rootList) {
        //System.out.println("process--->" + item.getName());
        SourceEntry src = ctx.findClass("*", item.getName());
        if (src == null) {
          item.complete();
          continue;
        }
        if (src.isInterface()) {
          String name = item.getName().replace("Serv", "Impl");
          src = ctx.findClass("*", name);
          item.setImpl(name);
        }
        //System.out.println(item.getName() + "--->" + src + " --" + src.isInterface());
        SequenceVisitor visitor = new SequenceVisitor(draw, item);
        visitor.visit(src.getCunit(), null);
      }
      rootList = draw.getRootIncomplete();
      process(ctx, draw, rootList);
    }
  }

  private JsonArray createEdges(JetConfig draw, SeqCaller caller) {
    JsonArray array = new JsonArray();

    return array;
  }

}
