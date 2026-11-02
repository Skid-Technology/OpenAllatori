package com.allatori.lib.bcel.classfile;

import com.allatori.lib.bcel.generic.Type;
import com.allatori.lib.bcel.util.BCELComparator;
import com.allatori.lib.bcel.util.comparator.FieldComparator;
import java.io.DataInputStream;
import java.io.IOException;

public final class Field extends FieldOrMethod {

  public static BCELComparator _cmp = new FieldComparator();

  public Field(
      int value, int value2, int value3, Attribute[] attributes, ConstantPool constantPool) {
    super(value, value2, value3, attributes, constantPool);
  }

  public Field(DataInputStream dataInputStream, ConstantPool constantPool)
      throws IOException, ClassFormatException {
    super(dataInputStream, constantPool);
  }

  public Type getType() {
    return Type.getReturnType(this.getText2());
  }

  public final Field copy(ConstantPool constantPool) {
    return (Field) this.copy_(constantPool);
  }

  public final ConstantValue getConstantValue() {
    int value;
    for (int value2 = value = 0; value2 < this.count2; value2 = ++value) {
      if (this.attributes[value].getTag() == 1) {
        return (ConstantValue) this.attributes[value];
      }
    }
    return null;
  }

  public static BCELComparator getComparator() {
    return _cmp;
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visitField(this);
  }

  @Override
  public boolean equals(Object result) {
    return _cmp.equals(this, result);
  }

  @Override
  public int hashCode() {
    return _cmp.hashCode(this);
  }

  public static void setComparator(BCELComparator bCELComparator) {
    _cmp = bCELComparator;
  }

  public Field(Field field) {
    super(field);
  }
}
