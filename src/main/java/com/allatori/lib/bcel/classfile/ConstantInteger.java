package com.allatori.lib.bcel.classfile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public final class ConstantInteger extends Constant implements ConstantObject {

  public int bytes;

  public ConstantInteger(DataInputStream dataInputStream) throws IOException {
    this(dataInputStream.readInt());
  }

  @Override
  public final void dump(DataOutputStream dataOutputStream) throws IOException {
    dataOutputStream.writeByte(this.tag);
    dataOutputStream.writeInt(this.bytes);
  }

  public final int getBytes() {
    return this.bytes;
  }

  @Override
  public Object getConstantValue(ConstantPool constantPool) {
    return new Integer(this.bytes);
  }

  public final void setBytes(int bytes) {
    this.bytes = bytes;
  }

  public ConstantInteger(int bytes) {
    super((byte) 3);
    this.bytes = bytes;
  }

  public ConstantInteger(ConstantInteger constantInteger) {
    this(constantInteger.getBytes());
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visitConstantInteger(this);
  }
}
