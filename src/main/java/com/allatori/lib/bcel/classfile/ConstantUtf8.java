package com.allatori.lib.bcel.classfile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public final class ConstantUtf8 extends Constant {

  public String bytes;

  @Override
  public void accept(Visitor visitor) {
    visitor.visitConstantUtf8(this);
  }

  public ConstantUtf8(ConstantUtf8 constantUtf8) {
    this(constantUtf8.getBytes());
  }

  public final void setBytes(String bytes) {
    this.bytes = bytes;
  }

  public ConstantUtf8(DataInputStream dataInputStream) throws IOException {
    super((byte) 1);
    this.bytes = dataInputStream.readUTF();
  }

  public final String getBytes() {
    return this.bytes;
  }

  @Override
  public final void dump(DataOutputStream dataOutputStream) throws IOException {
    dataOutputStream.writeByte(this.tag);
    dataOutputStream.writeUTF(this.bytes);
  }

  public ConstantUtf8(String bytes) {
    super((byte) 1);
    if (bytes == null) {
      throw new IllegalArgumentException("bytes must not be null!");
    }
    this.bytes = bytes;
  }
}
