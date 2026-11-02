package com.allatori.lib.bcel.classfile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public final class NestHost extends Attribute {

  public int hostClassIndex;

  @Override
  public final void dump(DataOutputStream dataOutputStream) throws IOException {
    super.dump(dataOutputStream);
    dataOutputStream.writeShort(this.hostClassIndex);
  }

  public final void setHostClassIndex(int hostClassIndex) {
    this.hostClassIndex = hostClassIndex;
  }

  public final int getHostClassIndex() {
    return this.hostClassIndex;
  }

  @Override
  public Attribute copy(ConstantPool constantPool) {
    return (NestHost) this.clone();
  }

  public final ConstantClass getConstantClass() {
    return (ConstantClass) this.constant_pool.getConstant(this.hostClassIndex, (byte) 7);
  }

  public NestHost(int value, int value2, int hostClassIndex, ConstantPool constantPool) {
    super((byte) 22, value, value2, constantPool);
    this.hostClassIndex = hostClassIndex;
  }

  @Override
  public void accept(Visitor visitor) {}

  public NestHost(int value, int value2, DataInputStream dataInputStream, ConstantPool constantPool)
      throws IOException {
    this(value, value2, dataInputStream.readUnsignedShort(), constantPool);
  }

  public NestHost(NestHost nestHost) {
    this(
        nestHost.getName_index(),
        nestHost.getLength(),
        nestHost.getHostClassIndex(),
        nestHost.getConstantPool());
  }
}
