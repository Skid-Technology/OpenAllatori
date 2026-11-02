package com.allatori.lib.bcel.classfile;

import java.io.DataOutputStream;
import java.io.IOException;

public class EnumElementValue extends ElementValue {

  public int valueIdx;

  public int typeIdx;

  public String getText2() {
    return ((ConstantUtf8) this.cpool.getConstant(this.typeIdx, (byte) 1)).getBytes();
  }

  public EnumElementValue(int value, int typeIdx, int valueIdx, ConstantPool constantPool) {
    super(value, constantPool);
    if (value != 101) {
      throw new RuntimeException("Only element values of type enum can be built with this ctor");
    }
    this.typeIdx = typeIdx;
    this.valueIdx = valueIdx;
  }

  public int getValueIdx() {
    return this.valueIdx;
  }

  public int getTypeIdx() {
    return this.typeIdx;
  }

  @Override
  public void dump(DataOutputStream dataOutputStream) throws IOException {
    dataOutputStream.writeByte(this.type);
    dataOutputStream.writeShort(this.typeIdx);
    dataOutputStream.writeShort(this.valueIdx);
  }

  public String getText3() {
    return ((ConstantUtf8) this.cpool.getConstant(this.valueIdx, (byte) 1)).getBytes();
  }

  @Override
  public String getText() {
    return ((ConstantUtf8) this.cpool.getConstant(this.valueIdx, (byte) 1)).getBytes();
  }
}
