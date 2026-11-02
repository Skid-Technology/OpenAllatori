package com.allatori.lib.bcel.classfile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public final class Deprecated extends Attribute {

  public byte[] bytes;

  @Override
  public final void dump(DataOutputStream dataOutputStream) throws IOException {
    super.dump(dataOutputStream);
    if (this.length > 0) {
      dataOutputStream.write(this.bytes, 0, this.length);
    }
  }

  public Deprecated(Deprecated deprecated) {
    this(
        deprecated.getName_index(),
        deprecated.getLength(),
        deprecated.getBytes(),
        deprecated.getConstantPool());
  }

  public Deprecated(
      int value, int value2, DataInputStream dataInputStream, ConstantPool constantPool)
      throws IOException {
    this(value, value2, (byte[]) null, constantPool);
    if (value2 > 0) {
      this.bytes = new byte[value2];
      dataInputStream.readFully(this.bytes);
      System.err.println("Deprecated attribute with length > 0");
    }
  }

  public final void setBytes(byte[] bytes) {
    this.bytes = bytes;
  }

  public Deprecated(int value, int value2, byte[] bytes, ConstantPool constantPool) {
    super((byte) 8, value, value2, constantPool);
    this.bytes = bytes;
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visitDeprecated(this);
  }

  @Override
  public Attribute copy(ConstantPool constantPool) {
    Deprecated deprecated = (Deprecated) this.clone();
    if (this.bytes != null) {
      deprecated.bytes = new byte[this.bytes.length];
      System.arraycopy(this.bytes, 0, deprecated.bytes, 0, this.bytes.length);
    }
    deprecated.constant_pool = constantPool;
    return deprecated;
  }

  public final byte[] getBytes() {
    return this.bytes;
  }
}
