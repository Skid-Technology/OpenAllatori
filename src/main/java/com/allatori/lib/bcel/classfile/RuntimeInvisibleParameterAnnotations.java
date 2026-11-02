package com.allatori.lib.bcel.classfile;

import java.io.DataInputStream;
import java.io.IOException;

public class RuntimeInvisibleParameterAnnotations extends ParameterAnnotations {

  public RuntimeInvisibleParameterAnnotations(
      int value, int value2, DataInputStream dataInputStream, ConstantPool constantPool)
      throws IOException {
    super((byte) 15, value, value2, dataInputStream, constantPool, false);
  }

  @Override
  public Attribute copy(ConstantPool constantPool) {
    return (Annotations) this.clone();
  }
}
