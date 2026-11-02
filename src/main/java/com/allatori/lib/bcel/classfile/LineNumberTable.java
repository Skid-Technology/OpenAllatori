package com.allatori.lib.bcel.classfile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public final class LineNumberTable extends Attribute {

  public LineNumber[] line_number_table;

  public int line_number_table_length;

  @Override
  public void accept(Visitor visitor) {
    visitor.visitLineNumberTable(this);
  }

  @Override
  public final void dump(DataOutputStream dataOutputStream) throws IOException {
    int value = 0;
    super.dump(dataOutputStream);
    dataOutputStream.writeShort(this.line_number_table_length);
    for (int index = 0; value < this.line_number_table_length; value = index) {
      int value2 = index;
      LineNumber[] lineNumbers = this.line_number_table;
      index++;
      lineNumbers[value2].dump(dataOutputStream);
    }
  }

  public int getSourceLine(int value) {
    int value2 = 0;
    int value3;
    if ((value3 = this.line_number_table_length - 1) < 0) {
      return -1;
    }
    int value4 = -1;
    int value5 = -1;
    do {
      int value6 = (value2 + value3) / 2;
      int value7;
      if ((value7 = this.line_number_table[value6].getStart_pc()) == value) {
        return this.line_number_table[value6].getLine_number();
      }
      int value8;
      if (value < value7) {
        value3 = value6 - 1;
        value8 = value7;
      } else {
        value2 = value6 + 1;
        value8 = value7;
      }
      if (value8 < value && value7 > value5) {
        value5 = value7;
        value4 = value6;
      }
    } while (value2 <= value3);
    return value4 < 0 ? -1 : this.line_number_table[value4].getLine_number();
  }

  public LineNumberTable(LineNumberTable lineNumberTable) {
    this(
        lineNumberTable.getName_index(),
        lineNumberTable.getLength(),
        lineNumberTable.getLineNumberTable(),
        lineNumberTable.getConstantPool());
  }

  public LineNumberTable(
      int value, int value2, LineNumber[] lineNumbers, ConstantPool constantPool) {
    super((byte) 4, value, value2, constantPool);
    this.setLineNumberTable(lineNumbers);
  }

  @Override
  public Attribute copy(ConstantPool constantPool) {
    LineNumberTable lineNumberTable;
    (lineNumberTable = (LineNumberTable) this.clone()).line_number_table =
        new LineNumber[this.line_number_table_length];
    int value;
    for (int value2 = value = 0; value2 < this.line_number_table_length; value2 = value) {
      LineNumber[] lineNumbers = lineNumberTable.line_number_table;
      LineNumber lineNumber = this.line_number_table[value].copy();
      int value3 = value;
      value++;
      lineNumbers[value3] = lineNumber;
    }
    lineNumberTable.constant_pool = constantPool;
    return lineNumberTable;
  }

  public final LineNumber[] getLineNumberTable() {
    return this.line_number_table;
  }

  public final void setLineNumberTable(LineNumber[] lineNumbers) {
    this.line_number_table = lineNumbers;
    this.line_number_table_length = lineNumbers == null ? 0 : lineNumbers.length;
  }

  public LineNumberTable(
      int value, int value2, DataInputStream dataInputStream, ConstantPool constantPool)
      throws IOException {
    this(value, value2, (LineNumber[]) null, constantPool);
    this.line_number_table_length = dataInputStream.readUnsignedShort();
    this.line_number_table = new LineNumber[this.line_number_table_length];
    int value3;
    for (int value4 = value3 = 0; value4 < this.line_number_table_length; value4 = value3) {
      LineNumber[] lineNumbers = this.line_number_table;
      LineNumber lineNumber = new LineNumber(dataInputStream);
      int value5 = value3;
      value3++;
      lineNumbers[value5] = lineNumber;
    }
  }

  public final int getTableLength() {
    return this.line_number_table_length;
  }
}
