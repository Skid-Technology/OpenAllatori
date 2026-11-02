package com.allatori.lib.bcel.generic;

import com.allatori.lib.bcel.classfile.AnnotationElementValue;
import com.allatori.lib.bcel.classfile.ElementValue;
import java.io.DataOutputStream;
import java.io.IOException;

public class AnnotationElementValueGen extends ElementValueGen {

  public AnnotationEntryGen a;

  public AnnotationEntryGen getAnnotation() {
    return this.a;
  }

  @Override
  public String stringifyValue() {
    throw new RuntimeException("Not implemented yet");
  }

  public AnnotationElementValueGen(
      AnnotationElementValue annotationElementValue,
      ConstantPoolGen constantPoolGen,
      boolean matches) {
    super(64, constantPoolGen);
    this.a =
        new AnnotationEntryGen(
            annotationElementValue.getAnnotationEntry(), constantPoolGen, matches);
  }

  public AnnotationElementValueGen(
      int value, AnnotationEntryGen annotationEntryGen, ConstantPoolGen constantPoolGen) {
    super(value, constantPoolGen);
    if (value != 64) {
      throw new RuntimeException(
          "Only element values of type annotation can be built with this ctor");
    }
    this.a = annotationEntryGen;
  }

  @Override
  public void dump(DataOutputStream dataOutputStream) throws IOException {
    dataOutputStream.writeByte(this.type);
    this.a.dump(dataOutputStream);
  }

  public AnnotationElementValueGen(
      AnnotationEntryGen annotationEntryGen, ConstantPoolGen constantPoolGen) {
    super(64, constantPoolGen);
    this.a = annotationEntryGen;
  }

  @Override
  public ElementValue getElementValue() {
    return new AnnotationElementValue(
        this.type, this.a.getAnnotation(), this.cpGen.getConstantPool());
  }
}
