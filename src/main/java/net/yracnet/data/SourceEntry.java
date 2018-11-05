/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.yracnet.data;

import com.github.javaparser.ast.CompilationUnit;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author yracnet
 */
public class SourceEntry {

  private final File file;
  private final String packageName;
  private final String className;
  private String label;
  private final CompilationUnit cunit;
  private final Map<String, String> ref = new HashMap<>();

  public SourceEntry(File file, CompilationUnit cunit) {
    this.file = file;
    this.cunit = cunit;
    this.className = file.getName().replace(".java", "");
    this.label = className;
    this.packageName = cunit.getPackageDeclaration().get().getNameAsString();
  }

  public File getFile() {
    return file;
  }

  public File getDrawFile(String sufix) {
    String name = file.getName().replace(".java", sufix + ".jet");
    return new File(file.getParentFile(), name);
  }

  public String getPackageName() {
    return packageName;
  }

  public String getClassName() {
    return className;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public CompilationUnit getCunit() {
    return cunit;
  }

  public void addRef(String name, String value) {
    ref.put(name, value);
  }

  public String getRef(String name) {
    return name ==null? null :  ref.get(name);
  }

  @Override
  public String toString() {
    return "SourceEntry{ packageName=" + packageName + ", mainName=" + className + '}';
  }
  public static final SourceEntry NONE = null;

  public boolean isInterface() {
    return className.endsWith("Serv");
  }
}
