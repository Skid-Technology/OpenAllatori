package com.allatori.lib.bcel.generic;

public class InstructionFactory$MethodObject {

  public String name;

  public int access;

  public String class_name;

  public Type[] arg_types;

  public Type result_type;

  public InstructionFactory$MethodObject(
      String class_name, String name, Type type, Type[] types, int access) {
    super();
    this.class_name = class_name;
    this.name = name;
    this.result_type = type;
    this.arg_types = types;
    this.access = access;
  }
}
