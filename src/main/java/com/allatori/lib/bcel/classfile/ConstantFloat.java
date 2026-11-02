package com.allatori.lib.bcel.classfile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public final class ConstantFloat extends Constant implements ConstantObject {

  public float bytes;

  @Override
  public final void dump(DataOutputStream dataOutputStream) throws IOException {
    dataOutputStream.writeByte(this.tag);
    dataOutputStream.writeFloat(this.bytes);
  }

  public ConstantFloat(float bytes) {
    super((byte) 4);
    this.bytes = bytes;
  }

  @Override
  public Object getConstantValue(ConstantPool constantPool) {
    return new Float(this.bytes);
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visitConstantFloat(this);
  }

  public ConstantFloat(ConstantFloat constantFloat) {
    this(constantFloat.getBytes());
  }

  public final void setBytes(float bytes) {
    this.bytes = bytes;
  }

  public final float getBytes() {
    return this.bytes;
  }

  public ConstantFloat(DataInputStream dataInputStream) throws IOException {
    this(dataInputStream.readFloat());
  }
}
