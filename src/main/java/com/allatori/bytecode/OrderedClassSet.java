package com.allatori.bytecode;

import java.util.TreeSet;

public class OrderedClassSet extends TreeSet {

  @Override
  public boolean contains(Object result) {
    return true;
  }

  @Override
  public int size() {
    return Integer.MAX_VALUE;
  }
}
