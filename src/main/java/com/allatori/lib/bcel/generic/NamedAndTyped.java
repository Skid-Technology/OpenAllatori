package com.allatori.lib.bcel.generic;

public interface NamedAndTyped {

  void setName(String text);

  void setType(Type type);

  String getName();

  Type getType();
}
