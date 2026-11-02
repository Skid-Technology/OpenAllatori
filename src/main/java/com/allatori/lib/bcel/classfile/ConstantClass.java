package com.allatori.lib.bcel.classfile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public final class ConstantClass extends Constant implements ConstantObject {

  public int name_index;

  public ConstantClass(DataInputStream dataInputStream) throws IOException {
    this(dataInputStream.readUnsignedShort());
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visitConstantClass(this);
  }

  public final void setNameIndex(int name_index) {
    this.name_index = name_index;
  }

  public ConstantClass(int name_index) {
    super((byte) 7);
    this.name_index = name_index;
  }

  public final int getNameIndex() {
    return this.name_index;
  }

  public ConstantClass(ConstantClass constantClass) {
    this(constantClass.getNameIndex());
  }

  @Override
  public Object getConstantValue(ConstantPool constantPool) {
    return ((ConstantUtf8) constantPool.getConstant(this.name_index, (byte) 1)).getBytes();
  }

  public String getBytes(ConstantPool constantPool) {
    return (String) this.getConstantValue(constantPool);
  }

  @Override
  public final void dump(DataOutputStream dataOutputStream) throws IOException {
    dataOutputStream.writeByte(this.tag);
    dataOutputStream.writeShort(this.name_index);
  }
}
