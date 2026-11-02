package com.allatori.lib.bcel.classfile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class LocalVariableTable extends Attribute {

  public int local_variable_table_length;

  public LocalVariable[] local_variable_table;

  public LocalVariableTable(LocalVariableTable localVariableTable) {
    this(
        localVariableTable.getName_index(),
        localVariableTable.getLength(),
        localVariableTable.getLocalVariableTable(),
        localVariableTable.getConstantPool());
  }

  public final LocalVariable getLocalVariable(int value, int value2) {
    int value3;
    for (int value4 = value3 = 0; value4 < this.local_variable_table_length; value4 = ++value3) {
      if (this.local_variable_table[value3].getIndex() == value) {
        int value5;
        int value6 =
            (value5 = this.local_variable_table[value3].getStart_pc())
                + this.local_variable_table[value3].getLength();
        if (value2 >= value5 && value2 < value6) {
          return this.local_variable_table[value3];
        }
      }
    }
    return null;
  }

  public LocalVariableTable(
      int value, int value2, DataInputStream dataInputStream, ConstantPool constantPool)
      throws IOException {
    this(value, value2, (LocalVariable[]) null, constantPool);
    this.local_variable_table_length = dataInputStream.readUnsignedShort();
    this.local_variable_table = new LocalVariable[this.local_variable_table_length];
    int value3;
    for (int value4 = value3 = 0; value4 < this.local_variable_table_length; value4 = value3) {
      LocalVariable[] localVariables = this.local_variable_table;
      LocalVariable localVariable = new LocalVariable(dataInputStream, constantPool);
      int value5 = value3;
      value3++;
      localVariables[value5] = localVariable;
    }
  }

  /**
   * @deprecated
   */
  public final LocalVariable getLocalVariable(int value) {
    int value2;
    for (int value3 = value2 = 0; value3 < this.local_variable_table_length; value3 = ++value2) {
      if (this.local_variable_table[value2].getIndex() == value) {
        return this.local_variable_table[value2];
      }
    }
    return null;
  }

  @Override
  public Attribute copy(ConstantPool constantPool) {
    LocalVariableTable localVariableTable;
    (localVariableTable = (LocalVariableTable) this.clone()).local_variable_table =
        new LocalVariable[this.local_variable_table_length];
    int value;
    for (int value2 = value = 0; value2 < this.local_variable_table_length; value2 = value) {
      LocalVariable[] localVariables = localVariableTable.local_variable_table;
      LocalVariable localVariable = this.local_variable_table[value].copy();
      int value3 = value;
      value++;
      localVariables[value3] = localVariable;
    }
    localVariableTable.constant_pool = constantPool;
    return localVariableTable;
  }

  public final void setLocalVariableTable(LocalVariable[] localVariables) {
    this.local_variable_table = localVariables;
    this.local_variable_table_length = localVariables == null ? 0 : localVariables.length;
  }

  @Override
  public final void dump(DataOutputStream dataOutputStream) throws IOException {
    int value = 0;
    super.dump(dataOutputStream);
    dataOutputStream.writeShort(this.local_variable_table_length);
    for (int index = 0; value < this.local_variable_table_length; value = index) {
      int value2 = index;
      LocalVariable[] localVariables = this.local_variable_table;
      index++;
      localVariables[value2].dump(dataOutputStream);
    }
  }

  public final int getTableLength() {
    return this.local_variable_table_length;
  }

  public final LocalVariable[] getLocalVariableTable() {
    return this.local_variable_table;
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visitLocalVariableTable(this);
  }

  public LocalVariableTable(
      int value, int value2, LocalVariable[] localVariables, ConstantPool constantPool) {
    super((byte) 5, value, value2, constantPool);
    this.setLocalVariableTable(localVariables);
  }
}
