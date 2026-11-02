package com.allatori.lib.bcel.classfile;

import java.io.DataInputStream;
import java.io.IOException;

public final class ConstantInterfaceMethodref extends ConstantCP {

  public ConstantInterfaceMethodref(DataInputStream dataInputStream) throws IOException {
    super((byte) 11, dataInputStream);
  }

  public ConstantInterfaceMethodref(ConstantInterfaceMethodref constantInterfaceMethodref) {
    super(
        (byte) 11,
        constantInterfaceMethodref.getClass_index(),
        constantInterfaceMethodref.getName_and_type_index());
  }

  public ConstantInterfaceMethodref(int value, int value2) {
    super((byte) 11, value, value2);
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visitConstantInterfaceMethodref(this);
  }
}
