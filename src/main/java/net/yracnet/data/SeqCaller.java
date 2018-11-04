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
public class SeqCaller {

  private SeqRoot from;
  private SeqRoot to;
  private String method;
  private String result;
  private String note;

  public SeqCaller() {
  }

  public SeqCaller(String method, String result) {
    this.method = method;
    this.result = result;
  }

  public SeqRoot getFrom() {
    return from;
  }

  public void setFrom(SeqRoot from) {
    this.from = from;
  }

  public SeqRoot getTo() {
    return to;
  }

  public void setTo(SeqRoot to) {
    this.to = to;
  }

  public String getMethod() {
    return method;
  }

  public void setMethod(String method) {
    this.method = method;
  }

  public String getResult() {
    return result;
  }

  public void setResult(String result) {
    this.result = result;
  }

  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }

}
