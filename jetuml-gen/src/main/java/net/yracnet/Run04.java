/**
 * Copyright © ${project.inceptionYear} ${owner} (${email})
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
		String srcBase = "/work/dev/bcb-01/R08/app-mrp/mrp-portal";
		String srcArray[] = {srcBase + "/mrp-portal-rest/src/main/java", srcBase + "/mrp-portal-logic/src/main/java", srcBase + "/mrp-portal-serv/src/main/java",};
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
		// ctx.addIgnore("Content");
		// ctx.addIgnore("ArchiveType");
		// ctx.addIgnore("Error");
		// ctx.addIgnore("Value");
		// ctx.addIgnore("ValueUrl");
		// ctx.addIgnore("Value");
		// ctx.addIgnore("ValueArchive");
		// ctx.addIgnore("ValueCredential");
		// ctx.addIgnore("ValueCertificate");
		// ctx.addInclude("*Mapper");
		// ctx.addInclude("*Serv");
		// ctx.addInclude("*Ftr");
		// ctx.addInclude("HTTPStatic");
		List<SourceEntry> rootList = ctx.searchClass("*", "*Rest");
		Writter write = new WritteReport(new File(srcBase + "/report1.html"));
		SequenceProcess process = new SequenceProcess(write);
		process.start();
		for (SourceEntry item : rootList) {
			process.process(ctx, item);
		}
		// rootList.forEach(item -> process.process(ctx, item));
		process.close();
	}
}
