package com.allatori.util;

import java.util.Hashtable;
import java.util.Set;

public class LookupTable {

  public Hashtable entries = new Hashtable();

  public int getEntries() {
    return this.entries.size();
  }

  public Set getEntries2() {
    return this.entries.keySet();
  }

  public void processText(String text, String text2) {
    this.entries.put(text, text2);
  }

  public boolean matchesText(String text) {
    return this.entries.containsKey(text);
  }

  public void processText(String text) {
    this.entries.remove(text);
  }

  public String getText(String text) {
    return (String) this.entries.get(text);
  }
}
