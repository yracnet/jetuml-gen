/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.yracnet.data;

/**
 *
 * @author yracnet
 */
public abstract class SeqId {

  private static int ID = -1;
  private final int id = ++ID;

  public int getId() {
    return id;
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof SeqId) {
      SeqId x = (SeqId) o;
      return id == x.id;
    }
    return false;
  }
}
