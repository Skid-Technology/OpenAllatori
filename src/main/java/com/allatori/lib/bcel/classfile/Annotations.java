package com.allatori.lib.bcel.classfile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public abstract class Annotations extends Attribute {

  public int count;

  public AnnotationEntry[] annotation_table;

  public boolean isRuntimeVisible;

  public boolean isRuntimeVisible() {
    return this.isRuntimeVisible;
  }

  public Annotations(
      byte value,
      int value2,
      int value3,
      DataInputStream dataInputStream,
      ConstantPool constantPool,
      boolean matches)
      throws IOException {
    this(value, value2, value3, (AnnotationEntry[]) null, constantPool, matches);
    this.count = dataInputStream.readUnsignedShort();
    this.annotation_table = new AnnotationEntry[this.count];
    int value4;
    for (int value5 = value4 = 0; value5 < this.count; value5 = value4) {
      AnnotationEntry[] annotationEntrys = this.annotation_table;
      AnnotationEntry annotationEntry =
          AnnotationEntry.createAnnotationEntry(dataInputStream, constantPool, matches);
      int value6 = value4;
      value4++;
      annotationEntrys[value6] = annotationEntry;
    }
  }

  public void writeAnnotations(DataOutputStream dataOutputStream) throws IOException {
    int value = 0;
    dataOutputStream.writeShort(this.count);
    for (int index = 0; value < this.count; value = index) {
      int value2 = index;
      AnnotationEntry[] annotationEntrys = this.annotation_table;
      index++;
      annotationEntrys[value2].dump(dataOutputStream);
    }
  }

  public AnnotationEntry[] getAnnotationEntries() {
    return this.annotation_table;
  }

  public final void setAnnotationTable(AnnotationEntry[] annotationEntrys) {
    this.annotation_table = annotationEntrys;
    this.count = annotationEntrys == null ? 0 : annotationEntrys.length;
  }

  public final int getNumAnnotations() {
    return this.count;
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visitAnnotation(this);
  }

  public Annotations(
      byte value,
      int value2,
      int value3,
      AnnotationEntry[] annotationEntrys,
      ConstantPool constantPool,
      boolean isRuntimeVisible) {
    super(value, value2, value3, constantPool);
    this.setAnnotationTable(annotationEntrys);
    this.isRuntimeVisible = isRuntimeVisible;
  }
}
