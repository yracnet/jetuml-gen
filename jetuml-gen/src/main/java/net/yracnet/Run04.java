/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.yracnet;

import java.io.File;
import net.yracnet.spec.Context;
import java.util.List;
import net.yracnet.data.SourceEntry;
import net.yracnet.impl.SequenceProcess;
import net.yracnet.impl.WritteReport;
import net.yracnet.spec.Writter;

/**
 *
 * @author yracnet
 */
public class Run04 {

    public static void main(String[] args) throws Exception {
        String srcBase = "/work/dev/CORE-00/param/param-manager";
        String srcArray[] = {
            srcBase + "/param-manager-impl/src/main/java",
            srcBase + "/param-manager-serv/src/main/java",
            srcBase + "/param-manager-view/src/main/java",
        };
        Context ctx = new Context(srcArray);
        ctx.addIgnore("*Grouper");
        ctx.addIgnore("EntityManager");
        ctx.addIgnore("ParamComplete*");
        ctx.addIgnore("ParamLov*");
        ctx.addIgnore("ParamSimple*");
        ctx.addIgnore("ParamSingle*");
        ctx.addIgnore("ParamDina*");
        ctx.addIgnore("Item*");
        ctx.addIgnore("*Wrap");
        ctx.addIgnore("Query");
        //ctx.addIgnore("Content");
        //ctx.addIgnore("ArchiveType");
        //ctx.addIgnore("Error");
        //ctx.addIgnore("Value");
        //ctx.addIgnore("ValueUrl");
        //ctx.addIgnore("Value");
        //ctx.addIgnore("ValueArchive");
        //ctx.addIgnore("ValueCredential");
        //ctx.addIgnore("ValueCertificate");

        ctx.addInclude("*Mapper");
        //ctx.addInclude("*Serv");
        //ctx.addInclude("*Ftr");
        //ctx.addInclude("HTTPStatic");
        List<SourceEntry> rootList = ctx.searchClass("*", "*Rest");
        Writter write = new WritteReport(new File(srcBase + "/report1.html"));
        SequenceProcess process = new SequenceProcess(write);
        process.start();
        for (SourceEntry item : rootList) {
            process.process(ctx, item);
        }
        //rootList.forEach(item -> process.process(ctx, item));
        process.close();
    }
}
