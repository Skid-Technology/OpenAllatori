package com.allatori.lib.bcel.classfile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public final class ConstantDouble extends Constant implements ConstantObject {

  public double bytes;

  @Override
  public void accept(Visitor visitor) {
    visitor.visitConstantDouble(this);
  }

  public ConstantDouble(double bytes) {
    super((byte) 6);
    this.bytes = bytes;
  }

  public ConstantDouble(DataInputStream dataInputStream) throws IOException {
    this(dataInputStream.readDouble());
  }

  @Override
  public final void dump(DataOutputStream dataOutputStream) throws IOException {
    dataOutputStream.writeByte(this.tag);
    dataOutputStream.writeDouble(this.bytes);
  }

  public final double getBytes() {
    return this.bytes;
  }

  public ConstantDouble(ConstantDouble constantDouble) {
    this(constantDouble.getBytes());
  }

  @Override
  public Object getConstantValue(ConstantPool constantPool) {
    return new Double(this.bytes);
  }

  public final void setBytes(double bytes) {
    this.bytes = bytes;
  }
}
