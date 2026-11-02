package com.allatori.lib.bcel.classfile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public final class Unknown extends Attribute {

  public byte[] bytes;

  public static Map unknown_attributes = new HashMap();

  public String name;

  public Unknown(int value, int value2, byte[] bytes, ConstantPool constantPool) {
    super((byte) -1, value, value2, constantPool);
    this.bytes = bytes;
    this.name = ((ConstantUtf8) constantPool.getConstant(value, (byte) 1)).getBytes();
    unknown_attributes.put(this.name, this);
  }

  public Unknown(int value, int value2, DataInputStream dataInputStream, ConstantPool constantPool)
      throws IOException {
    this(value, value2, (byte[]) null, constantPool);
    if (value2 > 0) {
      this.bytes = new byte[value2];
      dataInputStream.readFully(this.bytes);
    }
  }

  @Override
  public final String toString() {
    return this.name;
  }

  public final byte[] getBytes() {
    return this.bytes;
  }

  public static Unknown[] getUnknownAttributes() {
    Unknown[] unknowns = new Unknown[unknown_attributes.size()];
    Iterator iterator2 = unknown_attributes.values().iterator();
    int value = 0;
    for (Iterator iterator3 = iterator2; iterator3.hasNext(); iterator3 = iterator2) {
      Unknown unknown = (Unknown) iterator2.next();
      int value2 = value;
      value++;
      unknowns[value2] = unknown;
    }
    unknown_attributes.clear();
    return unknowns;
  }

  public Unknown(Unknown unknown) {
    this(
        unknown.getName_index(),
        unknown.getLength(),
        unknown.getBytes(),
        unknown.getConstantPool());
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visitUnknown(this);
  }

  @Override
  public final void dump(DataOutputStream dataOutputStream) throws IOException {
    super.dump(dataOutputStream);
    if (this.length > 0) {
      dataOutputStream.write(this.bytes, 0, this.length);
    }
  }

  public final void setBytes(byte[] bytes) {
    this.bytes = bytes;
  }

  @Override
  public Attribute copy(ConstantPool constantPool) {
    Unknown unknown = (Unknown) this.clone();
    if (this.bytes != null) {
      unknown.bytes = new byte[this.bytes.length];
      System.arraycopy(this.bytes, 0, unknown.bytes, 0, this.bytes.length);
    }
    unknown.constant_pool = constantPool;
    return unknown;
  }
}
