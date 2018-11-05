/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.yracnet;

import net.yracnet.spec.Context;
import java.util.List;
import net.yracnet.data.SourceEntry;
import net.yracnet.impl.SequenceProcess;

/**
 *
 * @author yracnet
 */
public class Run02 {

  public static void main(String[] args) throws Exception {
    //String src = "/media/yracnet/Disk-2/work/dev/github/jetuml-gen/case/bo.zip";
    String src = "/media/yracnet/Disk-2/work/dev/github/jetuml-gen/case/bo-zip-unzip";
    Context ctx = new Context(src);
    ctx.addIgnore("*Grouper");
    ctx.addIgnore("EntityManager");
    ctx.addIgnore("Query");
    ctx.addInclude("*Mapper");
    ctx.addInclude("HTTPStatic");
    List<SourceEntry> rootList = ctx.searchClass("*", "BeanRest");
    SequenceProcess process = new SequenceProcess();
    rootList.forEach(item -> process.process(ctx, item));
  }
}
