package com.allatori.lib.bcel.classfile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public final class ConstantString extends Constant implements ConstantObject {

  public int string_index;

  public String getBytes(ConstantPool constantPool) {
    return (String) this.getConstantValue(constantPool);
  }

  public ConstantString(DataInputStream dataInputStream) throws IOException {
    this(dataInputStream.readUnsignedShort());
  }

  public ConstantString(int string_index) {
    super((byte) 8);
    this.string_index = string_index;
  }

  public ConstantString(ConstantString constantString) {
    this(constantString.getStringIndex());
  }

  @Override
  public final void dump(DataOutputStream dataOutputStream) throws IOException {
    dataOutputStream.writeByte(this.tag);
    dataOutputStream.writeShort(this.string_index);
  }

  public final int getStringIndex() {
    return this.string_index;
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visitConstantString(this);
  }

  public final void setStringIndex(int string_index) {
    this.string_index = string_index;
  }

  @Override
  public Object getConstantValue(ConstantPool constantPool) {
    return ((ConstantUtf8) constantPool.getConstant(this.string_index, (byte) 1)).getBytes();
  }
}
