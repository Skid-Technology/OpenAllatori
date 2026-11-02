package com.allatori.lib.bcel.classfile;

import java.io.DataOutputStream;
import java.io.IOException;

public class ClassElementValue extends ElementValue {

  public int idx;

  public String getText2() {
    return ((ConstantUtf8) this.cpool.getConstant(this.idx, (byte) 1)).getBytes();
  }

  public ClassElementValue(int value, int idx, ConstantPool constantPool) {
    super(value, constantPool);
    this.idx = idx;
  }

  @Override
  public void dump(DataOutputStream dataOutputStream) throws IOException {
    dataOutputStream.writeByte(this.type);
    dataOutputStream.writeShort(this.idx);
  }

  public int getIndex() {
    return this.idx;
  }

  @Override
  public String getText() {
    return ((ConstantUtf8) this.cpool.getConstant(this.idx, (byte) 1)).getBytes();
  }
}
