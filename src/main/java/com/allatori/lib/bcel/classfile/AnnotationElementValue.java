package com.allatori.lib.bcel.classfile;

import java.io.DataOutputStream;
import java.io.IOException;

public class AnnotationElementValue extends ElementValue {

  public AnnotationEntry annotationEntry;

  @Override
  public String getText() {
    StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(this.annotationEntry.toString());
    return stringBuffer.toString();
  }

  public AnnotationEntry getAnnotationEntry() {
    return this.annotationEntry;
  }

  public AnnotationElementValue(
      int value, AnnotationEntry annotationEntry2, ConstantPool constantPool) {
    super(value, constantPool);
    if (value != 64) {
      throw new RuntimeException(
          "Only element values of type annotation can be built with this ctor");
    }
    this.annotationEntry = annotationEntry2;
  }

  @Override
  public void dump(DataOutputStream dataOutputStream) throws IOException {
    dataOutputStream.writeByte(this.type);
    this.annotationEntry.dump(dataOutputStream);
  }
}
