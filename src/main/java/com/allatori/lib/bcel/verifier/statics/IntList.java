package com.allatori.lib.bcel.verifier.statics;

import java.util.ArrayList;
import java.util.List;

public class IntList {

  public List theList = new ArrayList();

  public void add(int value) {
    this.theList.add(new Integer(value));
  }

  public boolean contains(int value) {
    Integer[] values = new Integer[this.theList.size()];
    this.theList.toArray(values);
    int value2 = 0;
    for (int value3 = value2; value3 < values.length; value3 = ++value2) {
      if (value == values[value2]) {
        return true;
      }
    }
    return false;
  }
}
