package com.allatori.lib.bcel.classfile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public final class ExceptionTable extends Attribute {

  public int number_of_exceptions;

  public int[] exception_index_table;

  public ExceptionTable(ExceptionTable exceptionTable) {
    this(
        exceptionTable.getName_index(),
        exceptionTable.getLength(),
        exceptionTable.getExceptionIndexTable(),
        exceptionTable.getConstantPool());
  }

  @Override
  public final void dump(DataOutputStream dataOutputStream) throws IOException {
    int value = 0;
    super.dump(dataOutputStream);
    dataOutputStream.writeShort(this.number_of_exceptions);
    for (int index = 0; value < this.number_of_exceptions; value = index) {
      int value2 = index;
      int[] values = this.exception_index_table;
      index++;
      dataOutputStream.writeShort(values[value2]);
    }
  }

  public final String[] getExceptionNames() {
    String[] texts = new String[this.number_of_exceptions];
    int value;
    for (int value2 = value = 0; value2 < this.number_of_exceptions; value2 = value) {
      String text =
          this.constant_pool
              .createText(this.exception_index_table[value], (byte) 7)
              .replace('/', '.');
      int value3 = value;
      value++;
      texts[value3] = text;
    }
    return texts;
  }

  public final int[] getExceptionIndexTable() {
    return this.exception_index_table;
  }

  @Override
  public Attribute copy(ConstantPool constantPool) {
    ExceptionTable exceptionTable = (ExceptionTable) this.clone();
    if (this.exception_index_table != null) {
      exceptionTable.exception_index_table = new int[this.exception_index_table.length];
      System.arraycopy(
          this.exception_index_table,
          0,
          exceptionTable.exception_index_table,
          0,
          this.exception_index_table.length);
    }
    exceptionTable.constant_pool = constantPool;
    return exceptionTable;
  }

  public ExceptionTable(
      int value, int value2, DataInputStream dataInputStream, ConstantPool constantPool)
      throws IOException {
    this(value, value2, (int[]) null, constantPool);
    int value3 = 0;
    this.number_of_exceptions = dataInputStream.readUnsignedShort();
    this.exception_index_table = new int[this.number_of_exceptions];
    for (int index = 0; value3 < this.number_of_exceptions; value3 = index) {
      int[] values = this.exception_index_table;
      int value4 = dataInputStream.readUnsignedShort();
      int value5 = index;
      index++;
      values[value5] = value4;
    }
  }

  public final void setExceptionIndexTable(int[] exception_index_table) {
    this.exception_index_table = exception_index_table;
    this.number_of_exceptions = exception_index_table == null ? 0 : exception_index_table.length;
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visitExceptionTable(this);
  }

  public final int getNumberOfExceptions() {
    return this.number_of_exceptions;
  }

  public ExceptionTable(int value, int value2, int[] values, ConstantPool constantPool) {
    super((byte) 3, value, value2, constantPool);
    this.setExceptionIndexTable(values);
  }
}
