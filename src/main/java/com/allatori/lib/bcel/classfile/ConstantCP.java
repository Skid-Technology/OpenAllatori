package com.allatori.lib.bcel.classfile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public abstract class ConstantCP extends Constant {

  public int class_index;

  public int name_and_type_index;

  public String getClass(ConstantPool constantPool) {
    return constantPool.getText(this.class_index, (byte) 7);
  }

  public final void setClass_index(int class_index) {
    this.class_index = class_index;
  }

  public ConstantCP(byte value, int class_index, int name_and_type_index) {
    super(value);
    this.class_index = class_index;
    this.name_and_type_index = name_and_type_index;
  }

  public final int getName_and_type_index() {
    return this.name_and_type_index;
  }

  public ConstantCP(byte value, DataInputStream dataInputStream) throws IOException {
    this(value, dataInputStream.readUnsignedShort(), dataInputStream.readUnsignedShort());
  }

  public ConstantCP(ConstantCP constantCP) {
    this(constantCP.getTag(), constantCP.getClass_index(), constantCP.getName_and_type_index());
  }

  public final int getClass_index() {
    return this.class_index;
  }

  @Override
  public final void dump(DataOutputStream dataOutputStream) throws IOException {
    dataOutputStream.writeByte(this.tag);
    dataOutputStream.writeShort(this.class_index);
    dataOutputStream.writeShort(this.name_and_type_index);
  }

  public final void setName_and_type_index(int name_and_type_index) {
    this.name_and_type_index = name_and_type_index;
  }
}
