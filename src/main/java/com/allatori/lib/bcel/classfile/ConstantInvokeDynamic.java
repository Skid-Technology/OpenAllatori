package com.allatori.lib.bcel.classfile;

import java.io.DataInput;
import java.io.DataOutputStream;
import java.io.IOException;

public final class ConstantInvokeDynamic extends Constant {

  public int count;

  public int count2;

  public ConstantInvokeDynamic(int count, int count2) {
    super((byte) 18);
    this.count = count;
    this.count2 = count2;
  }

  public int getBootstrapMethodAttrIndex() {
    return this.count;
  }

  @Override
  public final void dump(DataOutputStream dataOutputStream) throws IOException {
    dataOutputStream.writeByte(this.tag);
    dataOutputStream.writeShort(this.count);
    dataOutputStream.writeShort(this.count2);
  }

  public void setCount(int count) {
    this.count = count;
  }

  public void setCount2(int count2) {
    this.count2 = count2;
  }

  public ConstantInvokeDynamic(ConstantInvokeDynamic constantInvokeDynamic) {
    this(
        constantInvokeDynamic.getBootstrapMethodAttrIndex(),
        constantInvokeDynamic.getBootstrapMethodAttrIndex2());
  }

  public ConstantInvokeDynamic(DataInput dataInput) throws IOException {
    this(dataInput.readUnsignedShort(), dataInput.readUnsignedShort());
  }

  public int getBootstrapMethodAttrIndex2() {
    return this.count2;
  }

  @Override
  public void accept(Visitor visitor) {}
}
