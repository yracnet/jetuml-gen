/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.yracnet.data;

import com.yevdo.jwildcard.JWildcard;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author yracnet
 */
public class JetConfig {

  //private SeqRoot root;
  //private final List<SeqRoot> rootList = new ArrayList<>();
  //private final List<SeqCaller> callerList = new ArrayList<>();
  private final List<String> ignore = new ArrayList<>();
  private final List<String> include = new ArrayList<>();

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
  /*
  public SeqRoot getRoot() {
    return root;
  }

  public void setRoot(SeqRoot root) {
    this.root = root;
  }

  public int addRoot(SeqRoot item) {
    rootList.remove(item);
    rootList.add(item);
    return item.getId();
  }

  public void addCaller(SeqCaller caller) {
    callerList.add(caller);
  }

  public List<SeqCaller> getCaller() {
    return callerList;
  }

  public List<SeqCaller> getCallerFrom(SeqRoot root) {
    return callerList.stream().filter(i -> root.equals(i.getFrom())).collect(Collectors.toList());
  }

  public List<SeqRoot> getRootIncomplete() {
    return rootList.stream().filter(x -> !x.isComplete()).collect(Collectors.toList());
  }

  public List<SeqRoot> getRootNotIgnore() {
    return rootList.stream().filter(x -> !x.isIgnore()).collect(Collectors.toList());
  }

  public List<SeqRoot> getAllRoot() {
    return rootList;
  }

  public SeqRoot createRoot(String name) {
    for (SeqRoot item : rootList) {
      if (name.equals(item.getName())) {
        return item;
      }
    }
    return new SeqRoot(name);
  }

  public SeqCaller createCaller(String name) {
    return new SeqCaller(name);
  }
   */
}
