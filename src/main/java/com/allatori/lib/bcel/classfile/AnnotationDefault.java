package com.allatori.lib.bcel.classfile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class AnnotationDefault extends Attribute {

  public ElementValue default_value;

  public AnnotationDefault(
      int value, int value2, ElementValue elementValue, ConstantPool constantPool) {
    super((byte) 16, value, value2, constantPool);
    this.setDefaultValue(elementValue);
  }

  @Override
  public Attribute copy(ConstantPool constantPool) {
    throw new RuntimeException("Not implemented yet!");
  }

  @Override
  public void accept(Visitor visitor) {}

  @Override
  public void dump(DataOutputStream dataOutputStream) throws IOException {
    super.dump(dataOutputStream);
    this.default_value.dump(dataOutputStream);
  }

  public final void setDefaultValue(ElementValue elementValue) {
    this.default_value = elementValue;
  }

  public AnnotationDefault(
      int value, int value2, DataInputStream dataInputStream, ConstantPool constantPool)
      throws IOException {
    this(value, value2, (ElementValue) null, constantPool);
    this.default_value = ElementValue.createElementValue(dataInputStream, constantPool);
  }

  public final ElementValue getDefaultValue() {
    return this.default_value;
  }
}
