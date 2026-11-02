package com.allatori.lib.bcel.classfile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public final class ConstantNameAndType extends Constant {

  public int signature_index;

  public int name_index;

  public final void setName_index(int name_index) {
    this.name_index = name_index;
  }

  public final int getName_index() {
    return this.name_index;
  }

  public ConstantNameAndType(DataInputStream dataInputStream) throws IOException {
    this(dataInputStream.readUnsignedShort(), dataInputStream.readUnsignedShort());
  }

  @Override
  public final void dump(DataOutputStream dataOutputStream) throws IOException {
    dataOutputStream.writeByte(this.tag);
    dataOutputStream.writeShort(this.name_index);
    dataOutputStream.writeShort(this.signature_index);
  }

  public final int getSignature_index() {
    return this.signature_index;
  }

  public ConstantNameAndType(ConstantNameAndType constantNameAndType) {
    this(constantNameAndType.getName_index(), constantNameAndType.getSignature_index());
  }

  public final String getText(ConstantPool constantPool) {
    return constantPool.getText(this.getName_index(), (byte) 1);
  }

  public ConstantNameAndType(int name_index, int signature_index) {
    super((byte) 12);
    this.name_index = name_index;
    this.signature_index = signature_index;
  }

  public final String getText2(ConstantPool constantPool) {
    return constantPool.getText(this.getSignature_index(), (byte) 1);
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visitConstantNameAndType(this);
  }

  public final void setSignature_index(int signature_index) {
    this.signature_index = signature_index;
  }
}
