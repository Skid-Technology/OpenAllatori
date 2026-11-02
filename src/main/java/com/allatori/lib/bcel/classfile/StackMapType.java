package com.allatori.lib.bcel.classfile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public final class StackMapType implements Cloneable {

  public int index;

  public ConstantPool constant_pool;

  public byte type;

  public final ConstantPool getConstantPool() {
    return this.constant_pool;
  }

  public final void setConstantPool(ConstantPool constantPool) {
    this.constant_pool = constantPool;
  }

  public byte getType() {
    return this.type;
  }

  public void setIndex(int index) {
    this.index = index;
  }

  public StackMapType(DataInputStream dataInputStream, ConstantPool constantPool)
      throws IOException {
    this(dataInputStream.readByte(), -1, constantPool);
    if (this.hasIndex()) {
      this.setIndex(dataInputStream.readShort());
    }
    this.setConstantPool(constantPool);
  }

  public final boolean hasIndex() {
    return this.type == 7 || this.type == 8;
  }

  public final void dump(DataOutputStream dataOutputStream) throws IOException {
    dataOutputStream.writeByte(this.type);
    if (this.hasIndex()) {
      dataOutputStream.writeShort(this.getIndex());
    }
  }

  public StackMapType(byte value, int value2, ConstantPool constantPool) {
    super();
    this.index = -1;
    this.setType(value);
    this.setIndex(value2);
    this.setConstantPool(constantPool);
  }

  public void setType(byte type) {
    if (type >= 0 && type <= 8) {
      this.type = type;
    } else {
      throw new RuntimeException(("Illegal type for StackMapType: " + type));
    }
  }

  public int getIndex() {
    return this.index;
  }

  public String printIndex() {
    if (this.type == 7) {
      return this.index < 0
          ? ", class=<unknown>"
          : (", class=" + this.constant_pool.getText(this.index, (byte) 7));
    } else {
      return this.type == 8 ? (", offset=" + this.index) : "";
    }
  }

  public StackMapType copy() {
    try {
      return (StackMapType) this.clone();
    } catch (CloneNotSupportedException cloneNotSupportedException) {
      return null;
    }
  }
}
