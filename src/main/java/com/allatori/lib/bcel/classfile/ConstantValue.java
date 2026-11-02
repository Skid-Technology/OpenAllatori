package com.allatori.lib.bcel.classfile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public final class ConstantValue extends Attribute {

  public int constantvalue_index;

  @Override
  public void accept(Visitor visitor) {
    visitor.visitConstantValue(this);
  }

  public ConstantValue(int value, int value2, int constantvalue_index, ConstantPool constantPool) {
    super((byte) 1, value, value2, constantPool);
    this.constantvalue_index = constantvalue_index;
  }

  @Override
  public Attribute copy(ConstantPool constantPool) {
    ConstantValue constantValue = (ConstantValue) this.clone();
    constantValue.constant_pool = constantPool;
    return constantValue;
  }

  @Override
  public final void dump(DataOutputStream dataOutputStream) throws IOException {
    super.dump(dataOutputStream);
    dataOutputStream.writeShort(this.constantvalue_index);
  }

  public final void setConstantValueIndex(int constantvalue_index) {
    this.constantvalue_index = constantvalue_index;
  }

  public ConstantValue(
      int value, int value2, DataInputStream dataInputStream, ConstantPool constantPool)
      throws IOException {
    this(value, value2, dataInputStream.readUnsignedShort(), constantPool);
  }

  public final int getConstantValueIndex() {
    return this.constantvalue_index;
  }

  public ConstantValue(ConstantValue constantValue) {
    this(
        constantValue.getName_index(),
        constantValue.getLength(),
        constantValue.getConstantValueIndex(),
        constantValue.getConstantPool());
  }
}
