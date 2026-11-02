package com.allatori.lib.bcel.classfile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public abstract class ParameterAnnotations extends Attribute {

  public ParameterAnnotationEntry[] parameter_annotation_table;

  public int count;

  public ParameterAnnotations(
      byte value,
      int value2,
      int value3,
      DataInputStream dataInputStream,
      ConstantPool constantPool,
      boolean matches)
      throws IOException {
    this(value, value2, value3, (ParameterAnnotationEntry[]) null, constantPool);
    this.count = dataInputStream.readUnsignedByte();
    this.parameter_annotation_table = new ParameterAnnotationEntry[this.count];
    int value4;
    for (int value5 = value4 = 0; value5 < this.count; value5 = value4) {
      ParameterAnnotationEntry[] parameterAnnotationEntrys = this.parameter_annotation_table;
      ParameterAnnotationEntry parameterAnnotationEntry =
          new ParameterAnnotationEntry(dataInputStream, constantPool, matches);
      int value6 = value4;
      value4++;
      parameterAnnotationEntrys[value6] = parameterAnnotationEntry;
    }
  }

  @Override
  public void accept(Visitor visitor) {}

  public final int getCount() {
    return this.count;
  }

  @Override
  public final void dump(DataOutputStream dataOutputStream) throws IOException {
    super.dump(dataOutputStream);
    this.dump2(dataOutputStream);
  }

  public ParameterAnnotationEntry[] getParameter_annotation_table() {
    return this.parameter_annotation_table;
  }

  public ParameterAnnotations(
      byte value,
      int value2,
      int value3,
      ParameterAnnotationEntry[] parameterAnnotationEntrys,
      ConstantPool constantPool) {
    super(value, value2, value3, constantPool);
    this.setParameterAnnotationTable(parameterAnnotationEntrys);
  }

  public void dump2(DataOutputStream dataOutputStream) throws IOException {
    if (this.parameter_annotation_table != null) {
      dataOutputStream.writeByte(this.parameter_annotation_table.length);
      int value;
      for (int value2 = value = 0;
          value2 < this.parameter_annotation_table.length;
          value2 = ++value) {
        AnnotationEntry[] annotationEntries =
            this.parameter_annotation_table[value].getAnnotationEntries();
        dataOutputStream.writeShort(annotationEntries.length);
        int value3;
        for (int value4 = value3 = 0; value4 < annotationEntries.length; value4 = value3) {
          int value5 = value3;
          value3++;
          annotationEntries[value5].dump(dataOutputStream);
        }
      }
    }
  }

  public final void setParameterAnnotationTable(
      ParameterAnnotationEntry[] parameterAnnotationEntrys) {
    this.parameter_annotation_table = parameterAnnotationEntrys;
    this.count = parameterAnnotationEntrys == null ? 0 : parameterAnnotationEntrys.length;
  }

  public final ParameterAnnotationEntry[] getParameter_annotation_table2() {
    return this.parameter_annotation_table;
  }
}
