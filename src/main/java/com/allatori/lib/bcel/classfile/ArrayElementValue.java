package com.allatori.lib.bcel.classfile;

import java.io.DataOutputStream;
import java.io.IOException;

public class ArrayElementValue extends ElementValue {

  public ElementValue[] evalues;

  public ElementValue[] getElementValuesArray() {
    return this.evalues;
  }

  public int getElementValuesArraySize() {
    return this.evalues.length;
  }

  @Override
  public String getText() {
    StringBuffer stringBuffer;
    (stringBuffer = new StringBuffer()).append("[");
    int value = 0;
    for (int value2 = value; value2 < this.evalues.length; value2 = ++value) {
      stringBuffer.append(this.evalues[value].getText());
      if (value + 1 < this.evalues.length) {
        stringBuffer.append(",");
      }
    }
    stringBuffer.append("]");
    return stringBuffer.toString();
  }

  public ArrayElementValue(int value, ElementValue[] elementValues, ConstantPool constantPool) {
    super(value, constantPool);
    if (value != 91) {
      throw new RuntimeException("Only element values of type array can be built with this ctor");
    }
    this.evalues = elementValues;
  }

  @Override
  public void dump(DataOutputStream dataOutputStream) throws IOException {
    dataOutputStream.writeByte(this.type);
    dataOutputStream.writeShort(this.evalues.length);
    int value;
    for (int value2 = value = 0; value2 < this.evalues.length; value2 = value) {
      int value3 = value;
      ElementValue[] elementValues = this.evalues;
      value++;
      elementValues[value3].dump(dataOutputStream);
    }
  }
}
