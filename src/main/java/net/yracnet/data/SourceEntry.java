/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.yracnet.data;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import java.io.File;
import java.util.Optional;

/**
 *
 * @author yracnet
 */
public class SourceEntry {

  final File file;
  final String packageName;
  final String className;
  final CompilationUnit cunit;

  public SourceEntry(File file, CompilationUnit cunit) {
    this.file = file;
    this.cunit = cunit;
    this.className = file.getName().replace(".java", "");
    //cunit.getInterfaceByName(className).get() != null;
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

  public CompilationUnit getCunit() {
    return cunit;
  }

  @Override
  public String toString() {
    return "SourceEntry{ packageName=" + packageName + ", mainName=" + className + '}';
  }
//
//  private TypeDeclaration typeDeclaration;
//  private MethodDeclaration methodDeclaration;
//
//  public TypeDeclaration getTypeDeclaration() {
//    return typeDeclaration;
//  }
//
//  public void setTypeDeclaration(TypeDeclaration typeDeclaration) {
//    this.typeDeclaration = typeDeclaration;
//  }
//
//  public FieldDeclaration getTypeDeclarationFieldByName(String fieldName) {
//    if (typeDeclaration != null) {
//      Optional<FieldDeclaration> value = typeDeclaration.getFieldByName(fieldName);
//      if (value.isPresent()) {
//        return value.get();
//      }
//    }
//    return null;
//  }
//
//  public MethodDeclaration getMethodDeclaration() {
//    return methodDeclaration;
//  }
//
//  public void setMethodDeclaration(MethodDeclaration methodDeclaration) {
//    this.methodDeclaration = methodDeclaration;
//  }
  public static final SourceEntry NONE = null;
  //public final SourceEntry NONE = new SourceEntry(){
  //};

  public boolean isInterface() {
    return className.endsWith("Serv");
  }
}
