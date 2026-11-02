package com.allatori.lib.bcel.classfile;

import java.io.DataInput;
import java.io.DataOutputStream;
import java.io.IOException;

public final class ConstantMethodType extends Constant {

  public int descriptor_index;

  public ConstantMethodType(int descriptor_index) {
    super((byte) 16);
    this.descriptor_index = descriptor_index;
  }

  public ConstantMethodType(DataInput dataInput) throws IOException {
    this(dataInput.readUnsignedShort());
  }

  @Override
  public void accept(Visitor visitor) {}

  public ConstantMethodType(ConstantMethodType constantMethodType) {
    this(constantMethodType.getDescriptorIndex());
  }

  @Override
  public final void dump(DataOutputStream dataOutputStream) throws IOException {
    dataOutputStream.writeByte(this.tag);
    dataOutputStream.writeShort(this.descriptor_index);
  }

  public void setDescriptorIndex(int descriptor_index) {
    this.descriptor_index = descriptor_index;
  }

  public int getDescriptorIndex() {
    return this.descriptor_index;
  }
}
