/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.yracnet.util;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import net.yracnet.data.SourceEntry;

/**
 *
 * @author yracnet
 */
public class ParseUtility {

  static FileFilter JAVA_FILTER = (File file) -> {
    return file.isDirectory() || file.getName().endsWith(".java");
  };

  public static List<SourceEntry> processDirectory(File root) {
    List<SourceEntry> result = new ArrayList<>();
    for (File item : root.listFiles(JAVA_FILTER)) {
      processDirectory(item, result);
    }
    return result;
  }

  public static void processDirectory(File item, List<SourceEntry> list) {
    if (item.isDirectory()) {
      for (File subitem : item.listFiles(JAVA_FILTER)) {
        processDirectory(subitem, list);
      }
    } else {
      
      try {
        CompilationUnit cu = JavaParser.parse(item);
        SourceEntry entry = new SourceEntry(item, cu);
        list.add(entry);
        //System.out.println("--->" + item + " - " + entry);
      } catch (FileNotFoundException e) {
        System.out.println("Error: " + e.getMessage());
      }
    }

  }

}
