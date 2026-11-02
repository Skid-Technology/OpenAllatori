package com.allatori.lib.bcel.classfile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class LocalVariableTypeTable extends Attribute {

  public LocalVariable[] local_variable_type_table;

  public int count;

  public LocalVariableTypeTable(LocalVariableTypeTable localVariableTypeTable) {
    this(
        localVariableTypeTable.getName_index(),
        localVariableTypeTable.getLength(),
        localVariableTypeTable.getLocalVariableTypeTable(),
        localVariableTypeTable.getConstantPool());
  }

  public LocalVariableTypeTable(
      int value, int value2, LocalVariable[] localVariables, ConstantPool constantPool) {
    super((byte) 17, value, value2, constantPool);
    this.setLocalVariableTable(localVariables);
  }

  public final LocalVariable getLocalVariable(int value) {
    int value2;
    for (int value3 = value2 = 0; value3 < this.count; value3 = ++value2) {
      if (this.local_variable_type_table[value2].getIndex() == value) {
        return this.local_variable_type_table[value2];
      }
    }
    return null;
  }

  public final LocalVariable[] getLocalVariableTypeTable() {
    return this.local_variable_type_table;
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visitLocalVariableTypeTable(this);
  }

  public final void setLocalVariableTable(LocalVariable[] localVariables) {
    this.local_variable_type_table = localVariables;
    this.count = localVariables == null ? 0 : localVariables.length;
  }

  @Override
  public Attribute copy(ConstantPool constantPool) {
    LocalVariableTypeTable localVariableTypeTable;
    (localVariableTypeTable = (LocalVariableTypeTable) this.clone()).local_variable_type_table =
        new LocalVariable[this.count];
    int value;
    for (int value2 = value = 0; value2 < this.count; value2 = value) {
      LocalVariable[] localVariables = localVariableTypeTable.local_variable_type_table;
      LocalVariable localVariable = this.local_variable_type_table[value].copy();
      int value3 = value;
      value++;
      localVariables[value3] = localVariable;
    }
    localVariableTypeTable.constant_pool = constantPool;
    return localVariableTypeTable;
  }

  public LocalVariableTypeTable(
      int value, int value2, DataInputStream dataInputStream, ConstantPool constantPool)
      throws IOException {
    this(value, value2, (LocalVariable[]) null, constantPool);
    this.count = dataInputStream.readUnsignedShort();
    this.local_variable_type_table = new LocalVariable[this.count];
    int value3;
    for (int value4 = value3 = 0; value4 < this.count; value4 = value3) {
      LocalVariable[] localVariables = this.local_variable_type_table;
      LocalVariable localVariable = new LocalVariable(dataInputStream, constantPool);
      int value5 = value3;
      value3++;
      localVariables[value5] = localVariable;
    }
  }

  public final int getTableLength() {
    return this.count;
  }

  @Override
  public final void dump(DataOutputStream dataOutputStream) throws IOException {
    int value = 0;
    super.dump(dataOutputStream);
    dataOutputStream.writeShort(this.count);
    for (int index = 0; value < this.count; value = index) {
      int value2 = index;
      LocalVariable[] localVariables = this.local_variable_type_table;
      index++;
      localVariables[value2].dump(dataOutputStream);
    }
  }
}
