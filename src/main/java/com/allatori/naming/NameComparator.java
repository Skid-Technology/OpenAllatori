package com.allatori.naming;

import java.util.Comparator;

public class NameComparator implements Comparator {

  @Override
  public int compare(Object result, Object result2) {
    String text = (String) result;
    String text2 = (String) result2;
    return text.length() - text2.length();
  }
}
