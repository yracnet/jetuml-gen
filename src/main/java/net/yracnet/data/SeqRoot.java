/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.yracnet.data;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author yracnet
 */
public class SeqRoot extends SeqId {

  private String name;
  private String impl;
  private boolean complete = false;
  private Map<String, String> ref = new HashMap<>();

  SeqRoot(String name) {
    this.name = name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public String getImpl() {
    return impl;
  }

  public void setImpl(String impl) {
    this.impl = impl;
  }

  public void addRef(String name, String value) {
    ref.put(name, value);
  }

  public String getRef(String name) {
    return ref.get(name);
  }

  public boolean existRefName(String name) {
    return ref.containsKey(name);
  }

  public boolean isComplete() {
    return complete;
  }

  public void complete() {
    complete = true;
  }

  public String asLabel() {
    String label = name;
    if (impl != null) {
      label = label + " -> " + impl;
    }
    return label;
  }
}
