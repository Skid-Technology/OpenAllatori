package com.allatori.lib.bcel.classfile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class EnclosingMethod extends Attribute {

  public int classIndex;

  public int methodIndex;

  public final int getMethodIndex() {
    return this.methodIndex;
  }

  @Override
  public final void dump(DataOutputStream dataOutputStream) throws IOException {
    super.dump(dataOutputStream);
    dataOutputStream.writeShort(this.classIndex);
    dataOutputStream.writeShort(this.methodIndex);
  }

  public EnclosingMethod(
      int value, int value2, int classIndex, int methodIndex, ConstantPool constantPool) {
    super((byte) 18, value, value2, constantPool);
    this.classIndex = classIndex;
    this.methodIndex = methodIndex;
  }

  public EnclosingMethod(
      int value, int value2, DataInputStream dataInputStream, ConstantPool constantPool)
      throws IOException {
    this(
        value,
        value2,
        dataInputStream.readUnsignedShort(),
        dataInputStream.readUnsignedShort(),
        constantPool);
  }

  public final void setMethodIndex(int methodIndex) {
    this.methodIndex = methodIndex;
  }

  public final ConstantClass getEnclosingClass() {
    return (ConstantClass) this.constant_pool.getConstant(this.classIndex, (byte) 7);
  }

  public final ConstantNameAndType getEnclosingMethod() {
    return this.methodIndex == 0
        ? null
        : (ConstantNameAndType) this.constant_pool.getConstant(this.methodIndex, (byte) 12);
  }

  public final int getClassIndex() {
    return this.classIndex;
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visitEnclosingMethod(this);
  }

  public final void setClassIndex(int classIndex) {
    this.classIndex = classIndex;
  }

  @Override
  public Attribute copy(ConstantPool constantPool) {
    throw new RuntimeException("Not implemented yet!");
  }
}
