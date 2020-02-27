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
package net.yracnet.impl;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import net.yracnet.visitor.SequenceDiagramVisitor;
import net.yracnet.data.SourceEntry;
import net.yracnet.spec.Context;
import net.yracnet.spec.Process;
import net.yracnet.spec.Writter;

/**
 *
 * @author yracnet
 */
public class SequenceProcess implements Process {
	private Writter out;

	public SequenceProcess(Writter out) {
		this.out = out;
	}

	@Override
	public void start() {
		try {
			System.setProperty("apple.laf.useScreenMenuBar", "true");
			new Thread(() -> Application.launch(FXStarter.class)).start();
		} finally {
		}
	}

	@Override
	public void process(Context ctx, SourceEntry src) {
		SequenceDiagramVisitor diagramVisitor = new SequenceDiagramVisitor(ctx, src, out);
		diagramVisitor.visit(src.getCunit(), null);
		out.writeResume();
	}

	@Override
	public void close() {
		try {
			Platform.exit();
		} finally {
		}
	}
	public static class FXStarter extends Application {
		@Override
		public void init() {
		}

		@Override
		public void start(Stage primaryStage) {
		}
	}
}
