package com.allatori.lib.bcel.classfile;

import com.allatori.lib.bcel.Constants;
import java.io.DataInputStream;
import java.io.IOException;

public class ParameterAnnotationEntry implements Node, Constants {

  public int count;

  public AnnotationEntry[] annotation_table;

  public AnnotationEntry[] getAnnotationEntries() {
    return this.annotation_table;
  }

  public final int getCount() {
    return this.count;
  }

  @Override
  public void accept(Visitor visitor) {}

  public ParameterAnnotationEntry(
      DataInputStream dataInputStream, ConstantPool constantPool, boolean matches)
      throws IOException {
    this.count = dataInputStream.readUnsignedShort();
    this.annotation_table = new AnnotationEntry[this.count];
    int value;
    for (int value2 = value = 0; value2 < this.count; value2 = value) {
      AnnotationEntry[] annotationEntrys = this.annotation_table;
      AnnotationEntry annotationEntry =
          AnnotationEntry.createAnnotationEntry(dataInputStream, constantPool, matches);
      int value3 = value;
      value++;
      annotationEntrys[value3] = annotationEntry;
    }
  }
}
