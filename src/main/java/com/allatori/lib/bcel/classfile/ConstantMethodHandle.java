package com.allatori.lib.bcel.classfile;

import java.io.DataInput;
import java.io.DataOutputStream;
import java.io.IOException;

public final class ConstantMethodHandle extends Constant {

  public int reference_kind;

  public int reference_index;

  @Override
  public final void dump(DataOutputStream dataOutputStream) throws IOException {
    dataOutputStream.writeByte(this.tag);
    dataOutputStream.writeByte(this.reference_kind);
    dataOutputStream.writeShort(this.reference_index);
  }

  public ConstantMethodHandle(int reference_kind, int reference_index) {
    super((byte) 15);
    this.reference_kind = reference_kind;
    this.reference_index = reference_index;
  }

  public ConstantMethodHandle(ConstantMethodHandle constantMethodHandle) {
    this(constantMethodHandle.getReference_kind(), constantMethodHandle.getReference_index());
  }

  @Override
  public void accept(Visitor visitor) {}

  public void setReference_kind(int reference_kind) {
    this.reference_kind = reference_kind;
  }

  public int getReference_kind() {
    return this.reference_kind;
  }

  public void setReference_index(int reference_index) {
    this.reference_index = reference_index;
  }

  public int getReference_index() {
    return this.reference_index;
  }

  public ConstantMethodHandle(DataInput dataInput) throws IOException {
    this(dataInput.readUnsignedByte(), dataInput.readUnsignedShort());
  }
}
