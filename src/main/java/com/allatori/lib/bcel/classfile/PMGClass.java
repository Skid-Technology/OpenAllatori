package com.allatori.lib.bcel.classfile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public final class PMGClass extends Attribute {

  public int pmg_class_index;

  public int pmg_index;

  public PMGClass(
      int value, int value2, int pmg_index, int pmg_class_index, ConstantPool constantPool) {
    super((byte) 9, value, value2, constantPool);
    this.pmg_index = pmg_index;
    this.pmg_class_index = pmg_class_index;
  }

  @Override
  public void accept(Visitor visitor) {
    System.err.println("Visiting non-standard PMGClass object");
  }

  public final String getText() {
    return ((ConstantUtf8) this.constant_pool.getConstant(this.pmg_index, (byte) 1)).getBytes();
  }

  @Override
  public final void dump(DataOutputStream dataOutputStream) throws IOException {
    super.dump(dataOutputStream);
    dataOutputStream.writeShort(this.pmg_index);
    dataOutputStream.writeShort(this.pmg_class_index);
  }

  public final void setPmg_class_index(int pmg_class_index) {
    this.pmg_class_index = pmg_class_index;
  }

  public final int getPmg_class_index() {
    return this.pmg_class_index;
  }

  public final String getText2() {
    return ((ConstantUtf8) this.constant_pool.getConstant(this.pmg_class_index, (byte) 1))
        .getBytes();
  }

  public PMGClass(int value, int value2, DataInputStream dataInputStream, ConstantPool constantPool)
      throws IOException {
    this(
        value,
        value2,
        dataInputStream.readUnsignedShort(),
        dataInputStream.readUnsignedShort(),
        constantPool);
  }

  public PMGClass(PMGClass pMGClass) {
    this(
        pMGClass.getName_index(),
        pMGClass.getLength(),
        pMGClass.getPmg_index(),
        pMGClass.getPmg_class_index(),
        pMGClass.getConstantPool());
  }

  public final int getPmg_index() {
    return this.pmg_index;
  }

  @Override
  public Attribute copy(ConstantPool constantPool) {
    return (PMGClass) this.clone();
  }

  public final void setPmg_index(int pmg_index) {
    this.pmg_index = pmg_index;
  }
}
