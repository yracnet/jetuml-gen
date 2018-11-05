/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.yracnet.spec;

import com.yevdo.jwildcard.JWildcard;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import net.yracnet.data.SourceEntry;
import net.yracnet.util.ParseUtility;
import net.yracnet.util.ZipUtility;

/**
 *
 * @author yracnet
 */
public class Context {

  private final List<SourceEntry> sourceEntryList;
  private final List<String> ignore = new ArrayList<>();
  private final List<String> include = new ArrayList<>();

  public Context(String path) throws Exception {
    path = verifySource(path);
    File file = new File(path);
    sourceEntryList = ParseUtility.processDirectory(file);
  }

  public static String verifySource(String src) throws IOException {
    File file = new File(src);
    if (file.isFile()) {
      String name = file.getName().replace(".", "-") + "-unzip";
      File unzip = new File(file.getParentFile(), name);
      ZipUtility.unzip(file, unzip);
      src = unzip.getPath();
    }
    return src;
  }

  public void addIgnore(String name) {
    ignore.add(name);
  }

  public boolean isIgnore(String name) {
    return ignore.stream().filter(text -> JWildcard.matches(text, name)).count() > 0;
  }

  public void addInclude(String name) {
    include.add(name);
  }

  public boolean isInclude(String name) {
    return include.stream().filter(text -> JWildcard.matches(text, name)).count() > 0;
  }

  public List<SourceEntry> searchClass(String packageFind, String nameFind) {
    List<SourceEntry> result = new ArrayList<>();
    sourceEntryList.forEach((item) -> {
      boolean isPkg = JWildcard.matches(packageFind, item.getPackageName());
      boolean isName = JWildcard.matches(nameFind, item.getClassName());
      if (isPkg && isName) {
        result.add(item);
      }
    });
    return result;

  }

  public SourceEntry findClass(String pkg, String name) {
    List<SourceEntry> list = searchClass(pkg, name);
    return list.size() > 0 ? list.get(0) : SourceEntry.NONE;
  }

}
