package com.allatori.lib.bcel.classfile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public final class StackMapEntry implements Cloneable {

  public int number_of_locals;

  public ConstantPool constant_pool;

  public int byte_code_offset;

  public StackMapType[] types_of_stack_items;

  public StackMapType[] types_of_locals;

  public int number_of_stack_items;

  public StackMapEntry(
      int byte_code_offset,
      int number_of_locals,
      StackMapType[] stackMapTypes,
      int number_of_stack_items,
      StackMapType[] stackMapTypes2,
      ConstantPool constantPool) {
    super();
    this.byte_code_offset = byte_code_offset;
    this.number_of_locals = number_of_locals;
    this.types_of_locals = stackMapTypes;
    this.number_of_stack_items = number_of_stack_items;
    this.types_of_stack_items = stackMapTypes2;
    this.constant_pool = constantPool;
  }

  public StackMapEntry(DataInputStream dataInputStream, ConstantPool constantPool)
      throws IOException {
    this(dataInputStream.readShort(), dataInputStream.readShort(), null, -1, null, constantPool);
    this.types_of_locals = new StackMapType[this.number_of_locals];
    int value;
    for (int value2 = value = 0; value2 < this.number_of_locals; value2 = value) {
      StackMapType[] stackMapTypes = this.types_of_locals;
      StackMapType stackMapType = new StackMapType(dataInputStream, constantPool);
      int value3 = value;
      value++;
      stackMapTypes[value3] = stackMapType;
    }
    this.number_of_stack_items = dataInputStream.readShort();
    this.types_of_stack_items = new StackMapType[this.number_of_stack_items];
    int value4;
    for (int value5 = value4 = 0; value5 < this.number_of_stack_items; value5 = value4) {
      StackMapType[] stackMapTypes2 = this.types_of_stack_items;
      StackMapType stackMapType2 = new StackMapType(dataInputStream, constantPool);
      int value6 = value4;
      value4++;
      stackMapTypes2[value6] = stackMapType2;
    }
  }

  public void setByte_code_offset(int byte_code_offset) {
    this.byte_code_offset = byte_code_offset;
  }

  public void accept(Visitor visitor) {
    visitor.visitStackMapEntry(this);
  }

  public int getNumber_of_locals() {
    return this.number_of_locals;
  }

  public int getByte_code_offset() {
    return this.byte_code_offset;
  }

  public void setNumber_of_stack_items(int number_of_stack_items) {
    this.number_of_stack_items = number_of_stack_items;
  }

  public final void dump(DataOutputStream dataOutputStream) throws IOException {
    int value = 0;
    dataOutputStream.writeShort(this.byte_code_offset);
    dataOutputStream.writeShort(this.number_of_locals);
    for (int index = 0; value < this.number_of_locals; value = index) {
      int value2 = index;
      StackMapType[] stackMapTypes = this.types_of_locals;
      index++;
      stackMapTypes[value2].dump(dataOutputStream);
    }
    dataOutputStream.writeShort(this.number_of_stack_items);
    int value3;
    for (int value4 = value3 = 0; value4 < this.number_of_stack_items; value4 = value3) {
      int value5 = value3;
      StackMapType[] stackMapTypes2 = this.types_of_stack_items;
      value3++;
      stackMapTypes2[value5].dump(dataOutputStream);
    }
  }

  public int getNumber_of_stack_items() {
    return this.number_of_stack_items;
  }

  public void setTypes_of_locals(StackMapType[] stackMapTypes) {
    this.types_of_locals = stackMapTypes;
  }

  public final ConstantPool getConstantPool() {
    return this.constant_pool;
  }

  public StackMapEntry copy() {
    try {
      return (StackMapEntry) this.clone();
    } catch (CloneNotSupportedException cloneNotSupportedException) {
      return null;
    }
  }

  public void setTypes_of_stack_items(StackMapType[] stackMapTypes) {
    this.types_of_stack_items = stackMapTypes;
  }

  public StackMapType[] getTypes_of_locals() {
    return this.types_of_locals;
  }

  public StackMapType[] getTypes_of_stack_items() {
    return this.types_of_stack_items;
  }

  public final void setConstantPool(ConstantPool constantPool) {
    this.constant_pool = constantPool;
  }

  public void setNumber_of_locals(int number_of_locals) {
    this.number_of_locals = number_of_locals;
  }
}
