package com.allatori.lib.bcel.classfile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public final class ConstantLong extends Constant implements ConstantObject {

  public long bytes;

  @Override
  public void accept(Visitor visitor) {
    visitor.visitConstantLong(this);
  }

  public final long getBytes() {
    return this.bytes;
  }

  public final void setBytes(long bytes) {
    this.bytes = bytes;
  }

  public ConstantLong(long bytes) {
    super((byte) 5);
    this.bytes = bytes;
  }

  public ConstantLong(DataInputStream dataInputStream) throws IOException {
    this(dataInputStream.readLong());
  }

  @Override
  public Object getConstantValue(ConstantPool constantPool) {
    return new Long(this.bytes);
  }

  public ConstantLong(ConstantLong constantLong) {
    this(constantLong.getBytes());
  }

  @Override
  public final void dump(DataOutputStream dataOutputStream) throws IOException {
    dataOutputStream.writeByte(this.tag);
    dataOutputStream.writeLong(this.bytes);
  }
}
