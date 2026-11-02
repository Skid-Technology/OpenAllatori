package com.allatori.lib.bcel.classfile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public final class NestMembers extends Attribute {

  public int classes;

  public int[] classes2;

  @Override
  public void accept(Visitor visitor) {}

  public NestMembers(
      int value, int value2, DataInputStream dataInputStream, ConstantPool constantPool)
      throws IOException {
    this(value, value2, (int[]) null, constantPool);
    int value3 = 0;
    this.classes = dataInputStream.readUnsignedShort();
    this.classes2 = new int[this.classes];
    for (int index = 0; value3 < this.classes; value3 = index) {
      int[] values = this.classes2;
      int value4 = dataInputStream.readUnsignedShort();
      int value5 = index;
      index++;
      values[value5] = value4;
    }
  }

  public final void setClasses(int[] classes2) {
    this.classes2 = classes2;
    this.classes = classes2 == null ? 0 : classes2.length;
  }

  public final int getNumberClasses() {
    return this.classes;
  }

  @Override
  public final void dump(DataOutputStream dataOutputStream) throws IOException {
    int value = 0;
    super.dump(dataOutputStream);
    dataOutputStream.writeShort(this.classes);
    for (int index = 0; value < this.classes; value = index) {
      int value2 = index;
      int[] values = this.classes2;
      index++;
      dataOutputStream.writeShort(values[value2]);
    }
  }

  public NestMembers(int value, int value2, int[] values, ConstantPool constantPool) {
    super((byte) 23, value, value2, constantPool);
    this.setClasses(this.classes2);
  }

  public final int[] getClasses() {
    return this.classes2;
  }

  @Override
  public Attribute copy(ConstantPool constantPool) {
    NestMembers nestMembers = (NestMembers) this.clone();
    if (this.classes2 != null) {
      nestMembers.classes2 = new int[this.classes2.length];
      System.arraycopy(this.classes2, 0, nestMembers.classes2, 0, this.classes2.length);
    }
    nestMembers.constant_pool = constantPool;
    return nestMembers;
  }

  public NestMembers(NestMembers nestMembers) {
    this(
        nestMembers.getName_index(),
        nestMembers.getLength(),
        nestMembers.getClasses(),
        nestMembers.getConstantPool());
  }
}
