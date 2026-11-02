package com.allatori.lib.bcel.classfile;

import com.allatori.lib.bcel.generic.Type;
import com.allatori.lib.bcel.util.BCELComparator;
import com.allatori.lib.bcel.util.comparator.MethodComparator2;
import java.io.DataInputStream;
import java.io.IOException;

public final class Method extends FieldOrMethod {

  public static BCELComparator _cmp = new MethodComparator2();

  public static BCELComparator getComparator() {
    return _cmp;
  }

  public final LineNumberTable getLineNumberTable() {
    Code code;
    return (code = this.getCode()) == null ? null : code.getLineNumberTable();
  }

  public static void setComparator(BCELComparator bCELComparator) {
    _cmp = bCELComparator;
  }

  public Type getReturnType() {
    return Type.getReturnType(this.getText2());
  }

  public final ExceptionTable getExceptionTable() {
    int value;
    for (int value2 = value = 0; value2 < this.count2; value2 = ++value) {
      if (this.attributes[value] instanceof ExceptionTable) {
        return (ExceptionTable) this.attributes[value];
      }
    }
    return null;
  }

  public final Method copy(ConstantPool constantPool) {
    return (Method) this.copy_(constantPool);
  }

  public final Code getCode() {
    int value;
    for (int value2 = value = 0; value2 < this.count2; value2 = ++value) {
      if (this.attributes[value] instanceof Code) {
        return (Code) this.attributes[value];
      }
    }
    return null;
  }

  public Type[] getArgumentTypes() {
    return Type.getArgumentTypes(this.getText2());
  }

  public Method(
      int value, int value2, int value3, Attribute[] attributes, ConstantPool constantPool) {
    super(value, value2, value3, attributes, constantPool);
  }

  @Override
  public boolean equals(Object result) {
    return _cmp.equals(this, result);
  }

  public Method(Method method) {
    super(method);
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visitMethod(this);
  }

  public Method(DataInputStream dataInputStream, ConstantPool constantPool)
      throws IOException, ClassFormatException {
    super(dataInputStream, constantPool);
  }

  public final LocalVariableTable getLocalVariableTable() {
    Code code;
    return (code = this.getCode()) == null ? null : code.getLocalVariableTable();
  }

  @Override
  public int hashCode() {
    return _cmp.hashCode(this);
  }

  public Method() {}
}
