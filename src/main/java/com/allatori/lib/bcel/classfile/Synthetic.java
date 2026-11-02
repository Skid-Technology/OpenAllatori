package com.allatori.lib.bcel.classfile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public final class Synthetic extends Attribute {

  public byte[] bytes;

  public Synthetic(int value, int value2, byte[] bytes, ConstantPool constantPool) {
    super((byte) 7, value, value2, constantPool);
    this.bytes = bytes;
  }

  public Synthetic(Synthetic synthetic) {
    this(
        synthetic.getName_index(),
        synthetic.getLength(),
        synthetic.getBytes(),
        synthetic.getConstantPool());
  }

  @Override
  public final void dump(DataOutputStream dataOutputStream) throws IOException {
    super.dump(dataOutputStream);
    if (this.length > 0) {
      dataOutputStream.write(this.bytes, 0, this.length);
    }
  }

  public final byte[] getBytes() {
    return this.bytes;
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visitSynthetic(this);
  }

  @Override
  public Attribute copy(ConstantPool constantPool) {
    Synthetic synthetic = (Synthetic) this.clone();
    if (this.bytes != null) {
      synthetic.bytes = new byte[this.bytes.length];
      System.arraycopy(this.bytes, 0, synthetic.bytes, 0, this.bytes.length);
    }
    synthetic.constant_pool = constantPool;
    return synthetic;
  }

  public Synthetic(
      int value, int value2, DataInputStream dataInputStream, ConstantPool constantPool)
      throws IOException {
    this(value, value2, (byte[]) null, constantPool);
    if (value2 > 0) {
      this.bytes = new byte[value2];
      dataInputStream.readFully(this.bytes);
      System.err.println("Synthetic attribute with length > 0");
    }
  }

  public final void setBytes(byte[] bytes) {
    this.bytes = bytes;
  }
}
