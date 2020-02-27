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

import ca.mcgill.cs.jetuml.diagram.Diagram;
import ca.mcgill.cs.jetuml.diagram.SequenceDiagram;
import ca.mcgill.cs.jetuml.diagram.edges.CallEdge;
import ca.mcgill.cs.jetuml.diagram.edges.ReturnEdge;
import ca.mcgill.cs.jetuml.diagram.nodes.CallNode;
import ca.mcgill.cs.jetuml.diagram.nodes.ImplicitParameterNode;
import ca.mcgill.cs.jetuml.persistence.PersistenceService;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javax.imageio.ImageIO;
import net.yracnet.spec.Writter;
import net.yracnet.util.JetumlUtilty;

/**
 *
 * @author wyujra
 */
public class WritteReport implements Writter {
	class ItemFile {
		String title;
		File file;
		File image;
		List<ItemFile> child = new ArrayList<>();
	}

	private ItemFile search(List<ItemFile> child, String key) {
		for (ItemFile item : child) {
			if (key.equals(item.title)) {
				return item;
			}
		}
		ItemFile item = new ItemFile();
		item.title = key;
		child.add(item);
		return item;
	}

	private void cacheFile(File file) {
		String fileName = file.getName();
		String keyName[] = fileName.replace(".png", "").replace(".jet", "").split("-");
		if (fileName.contains("-info")) {
			return;
		}
		// ItemFile itemRest = search(root.child, "REST: " + keyName[0]);
		ItemFile itemRest = search(root.child, "Servicio \"" + keyName[0] + "\"");
		// ItemFile itemServ = search(itemRest.child, "Servicio &lt;" + keyName[0] + "." + keyName[1] + "&gt;");
		ItemFile itemServ = search(itemRest.child, "Metodo \"" + keyName[1] + "\"");
		if (fileName.endsWith(".png")) {
			itemServ.image = file;
		} else {
			itemServ.file = file;
		}
	}
	private final File file;
	private ItemFile root = new ItemFile();

	public WritteReport(File file) {
		this.file = file;
		this.root.title = "Diagrama de Secuencias";
	}

	@Override
	public void writeJet(File file, Diagram diagram) {
		sequenceDiagramSort(diagram);
		try {
			System.out.println("SAVE writeJet-->" + diagram);
			PersistenceService.save(diagram, file);
			cacheFile(file);
			System.out.println("SAVE========>" + file);
		} catch (IOException e) {
			System.out.println("ERROR writeJet-->" + e.getMessage());
		}
	}

	@Override
	public void writeImage(File file, Diagram diagram) {
		sequenceDiagramSort(diagram);
		try {
			System.out.println("SAVE writeImage-->" + diagram);
			Image image = JetumlUtilty.createImage(diagram);
			BufferedImage imageAwt = SwingFXUtils.fromFXImage(image, null);
			ImageIO.write(imageAwt, "png", file);
			cacheFile(file);
			System.out.println("SAVE========>" + file);
		} catch (Exception e) {
			System.out.println("ERROR writeImage-->" + e.getMessage());
		}
	}

	@Override
	public void writeResume() {
		try {
			System.out.println("SAVE RESUME========>" + file);
			PrintWriter html = new PrintWriter(file);
			// html.println("<h1>" + root.title + "</h1>");
			for (ItemFile item : root.child) {
				processWrite(html, item, 2);
			}
			html.flush();
			html.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private static void processWrite(PrintWriter html, ItemFile root, int level) {
		String title = root.title;
		html.println("<h" + level + ">" + title + "</h" + level + ">");
		if (root.image != null) {
			int w = 660;
			int h = 200;
			try {
				BufferedImage bimg = ImageIO.read(root.image);
				h = bimg.getHeight() * w / bimg.getWidth();
			} catch (IOException e) {
			}
			html.println("<img  style='width:" + w + "px; height:" + h + "px'  src='" + root.image.getPath() + "' alt='" + title + "' />");
		}
		for (ItemFile item : root.child) {
			processWrite(html, item, level + 1);
		}
	}

	private static <T> List<T> filterArray(Iterable array, Class<T> type) {
		List<T> result = new ArrayList<>();
		for (Object o : array) {
			if (type.isAssignableFrom(o.getClass())) {
				result.add((T) o);
			}
		}
		return result;
	}

	private static void sequenceDiagramSort(Diagram diagram) {
		// System.out.println("---->" + diagram);
		if (diagram instanceof SequenceDiagram) {
			List<ImplicitParameterNode> nodeList = filterArray(diagram.rootNodes(), ImplicitParameterNode.class);
			// System.out.println("---->" + nodeList);
			ImplicitParameterNode[] nodeArray = nodeList.toArray(new ImplicitParameterNode[0]);
			for (int i = 1; i < nodeArray.length; i++) {
				ImplicitParameterNode nodeFrom = nodeArray[i - 1];
				ImplicitParameterNode nodeTo = nodeArray[i];
				int callLength = 0;
				int returnLength = 0;
				List<CallEdge> callEdgeList = filterArray(diagram.edges(), CallEdge.class);
				for (CallEdge edge : callEdgeList) {
					CallNode s = (CallNode) edge.getStart();
					CallNode e = (CallNode) edge.getEnd();
					boolean isFrom = s.getParent() == nodeFrom;
					boolean isEnd = e.getParent() == nodeTo;
					if (isFrom && isEnd) {
						System.out.println("caller:--->" + edge.getMiddleLabel());
						callLength = edge.getMiddleLabel() == null ? 0 : edge.getMiddleLabel().length();
					}
				}
				List<ReturnEdge> returnEdgeList = filterArray(diagram.edges(), ReturnEdge.class);
				for (ReturnEdge edge : returnEdgeList) {
					CallNode s = (CallNode) edge.getStart();
					CallNode e = (CallNode) edge.getEnd();
					boolean isFrom = s.getParent() == nodeTo;
					boolean isEnd = e.getParent() == nodeFrom;
					if (isFrom && isEnd) {
						System.out.println("return:--->" + edge.getMiddleLabel());
						returnLength = edge.getMiddleLabel().length();
					}
				}
				int length = returnLength > callLength ? returnLength : callLength;
				int lastX = nodeFrom.position().getX();
				if (length == 0) {
					nodeTo.position().setX(lastX + 140);
				} else {
					int newX = lastX + 8 * length + 20;
					nodeTo.position().setX(newX);
				}
			}
		}
	}
}
