package com.allatori.lib.bcel.classfile;

import java.io.DataInputStream;
import java.io.IOException;

public final class ConstantMethodref extends ConstantCP {

  public ConstantMethodref(ConstantMethodref constantMethodref) {
    super(
        (byte) 10, constantMethodref.getClass_index(), constantMethodref.getName_and_type_index());
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visitConstantMethodref(this);
  }

  public ConstantMethodref(DataInputStream dataInputStream) throws IOException {
    super((byte) 10, dataInputStream);
  }

  public ConstantMethodref(int value, int value2) {
    super((byte) 10, value, value2);
  }
}
