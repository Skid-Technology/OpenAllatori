package com.allatori.lib.bcel.classfile;

import com.allatori.lib.bcel.Constants;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;

public final class LocalVariable implements Constants, Cloneable, Node, Serializable {

  public int length;

  public ConstantPool constant_pool;

  public int index;

  public int name_index;

  public int start_pc;

  public int signature_index;

  public final int getSignature_index() {
    return this.signature_index;
  }

  public final void setLength(int length) {
    this.length = length;
  }

  public LocalVariable copy() {
    try {
      return (LocalVariable) this.clone();
    } catch (CloneNotSupportedException cloneNotSupportedException) {
      return null;
    }
  }

  public final int getIndex() {
    return this.index;
  }

  public final void setSignature_index(int signature_index) {
    this.signature_index = signature_index;
  }

  public final String getText() {
    return ((ConstantUtf8) this.constant_pool.getConstant(this.name_index, (byte) 1)).getBytes();
  }

  public final int getStart_pc() {
    return this.start_pc;
  }

  public final void setIndex(int index) {
    this.index = index;
  }

  public final ConstantPool getConstantPool() {
    return this.constant_pool;
  }

  public final void dump(DataOutputStream dataOutputStream) throws IOException {
    dataOutputStream.writeShort(this.start_pc);
    dataOutputStream.writeShort(this.length);
    dataOutputStream.writeShort(this.name_index);
    dataOutputStream.writeShort(this.signature_index);
    dataOutputStream.writeShort(this.index);
  }

  public LocalVariable(LocalVariable localVariable) {
    this(
        localVariable.getStart_pc(),
        localVariable.getLength(),
        localVariable.getName_index(),
        localVariable.getSignature_index(),
        localVariable.getIndex(),
        localVariable.getConstantPool());
  }

  public LocalVariable(DataInputStream dataInputStream, ConstantPool constantPool)
      throws IOException {
    this(
        dataInputStream.readUnsignedShort(),
        dataInputStream.readUnsignedShort(),
        dataInputStream.readUnsignedShort(),
        dataInputStream.readUnsignedShort(),
        dataInputStream.readUnsignedShort(),
        constantPool);
  }

  public final void setStart_pc(int start_pc) {
    this.start_pc = start_pc;
  }

  public final void setConstantPool(ConstantPool constantPool) {
    this.constant_pool = constantPool;
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visitLocalVariable(this);
  }

  public final void setName_index(int name_index) {
    this.name_index = name_index;
  }

  public LocalVariable(
      int start_pc,
      int length,
      int name_index,
      int signature_index,
      int index,
      ConstantPool constantPool) {
    super();
    this.start_pc = start_pc;
    this.length = length;
    this.name_index = name_index;
    this.signature_index = signature_index;
    this.index = index;
    this.constant_pool = constantPool;
  }

  public final int getLength() {
    return this.length;
  }

  public final int getName_index() {
    return this.name_index;
  }

  public final String getText2() {
    return ((ConstantUtf8) this.constant_pool.getConstant(this.signature_index, (byte) 1))
        .getBytes();
  }
}
