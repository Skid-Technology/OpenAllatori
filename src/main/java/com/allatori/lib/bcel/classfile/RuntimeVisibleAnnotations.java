package com.allatori.lib.bcel.classfile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class RuntimeVisibleAnnotations extends Annotations {

  @Override
  public Attribute copy(ConstantPool constantPool) {
    return (Annotations) this.clone();
  }

  @Override
  public final void dump(DataOutputStream dataOutputStream) throws IOException {
    super.dump(dataOutputStream);
    this.writeAnnotations(dataOutputStream);
  }

  public RuntimeVisibleAnnotations(
      int value, int value2, DataInputStream dataInputStream, ConstantPool constantPool)
      throws IOException {
    super((byte) 12, value, value2, dataInputStream, constantPool, true);
  }
}
