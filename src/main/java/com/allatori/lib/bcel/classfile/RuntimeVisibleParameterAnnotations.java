package com.allatori.lib.bcel.classfile;

import java.io.DataInputStream;
import java.io.IOException;

public class RuntimeVisibleParameterAnnotations extends ParameterAnnotations {

  @Override
  public Attribute copy(ConstantPool constantPool) {
    return (Annotations) this.clone();
  }

  public RuntimeVisibleParameterAnnotations(
      int value, int value2, DataInputStream dataInputStream, ConstantPool constantPool)
      throws IOException {
    super((byte) 14, value, value2, dataInputStream, constantPool, true);
  }
}
