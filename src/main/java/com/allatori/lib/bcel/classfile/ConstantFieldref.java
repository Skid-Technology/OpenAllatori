package com.allatori.lib.bcel.classfile;

import java.io.DataInputStream;
import java.io.IOException;

public final class ConstantFieldref extends ConstantCP {

  public ConstantFieldref(int value, int value2) {
    super((byte) 9, value, value2);
  }

  public ConstantFieldref(ConstantFieldref constantFieldref) {
    super((byte) 9, constantFieldref.getClass_index(), constantFieldref.getName_and_type_index());
  }

  public ConstantFieldref(DataInputStream dataInputStream) throws IOException {
    super((byte) 9, dataInputStream);
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visitConstantFieldref(this);
  }
}
