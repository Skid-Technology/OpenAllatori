package com.allatori.lib.bcel.util;

public class TypeParserThreadLocal2 extends ThreadLocal {

  @Override
  public Object initialValue() {
    return new Integer(0);
  }
}
