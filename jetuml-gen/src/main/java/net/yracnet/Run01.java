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
import net.yracnet.spec.Process;

/**
 *
 * @author yracnet
 */
public class Run01 {

  public static void main(String[] arg) throws Exception {
    String src = "/media/yracnet/Disk-2/work/dev/github/jetuml-gen/case/bo.zip";
    System.out.println("--->" + src);
    Context ctx = new Context(src);
    List<SourceEntry> rootList = ctx.searchClass("*", "BeanRest");
    //Generator generator = new ClassGenerator();
    Process generator = new SequenceProcess();
    //rootList.forEach(System.out::println);
    rootList.forEach(item -> generator.process(ctx, item));
  }

}
