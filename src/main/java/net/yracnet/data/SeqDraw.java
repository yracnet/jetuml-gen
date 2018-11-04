/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.yracnet.data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author yracnet
 */
public class SeqDraw {

  private SeqRoot root;
  private final List<SeqRoot> rootList = new ArrayList<>();
  private final List<SeqCaller> callerList = new ArrayList<>();

  public SeqRoot getRoot() {
    return root;
  }

  public void setRoot(SeqRoot root) {
    this.root = root;
  }

  public int addRoot(SeqRoot item) {
    rootList.add(item);
    return item.getId();
  }

  public void addCaller(SeqCaller caller) {
    callerList.add(caller);
  }

  public List<SeqRoot> getRootIncomplete() {
    return rootList.stream().filter(x -> !x.isComplete()).collect(Collectors.toList());
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
}
