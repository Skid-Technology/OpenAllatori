package com.allatori.lib.bcel.generic;

import com.allatori.lib.bcel.classfile.ArrayElementValue;
import com.allatori.lib.bcel.classfile.ElementValue;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ArrayElementValueGen extends ElementValueGen {

  public List evalues;

  @Override
  public ElementValue getElementValue() {
    ElementValue[] elementValues = new ElementValue[this.evalues.size()];
    int value = 0;
    Iterator iterator2;
    Iterator iterator3 = iterator2 = this.evalues.iterator();
    while (iterator3.hasNext()) {
      ElementValueGen elementValueGen = (ElementValueGen) iterator2.next();
      ElementValue elementValue = elementValueGen.getElementValue();
      int value2 = value;
      Iterator iterator4 = iterator2;
      value++;
      iterator3 = iterator4;
      elementValues[value2] = elementValue;
    }
    return new ArrayElementValue(this.type, elementValues, this.cpGen.getConstantPool());
  }

  public int getElementValuesSize() {
    return this.evalues.size();
  }

  public ArrayElementValueGen(
      int value, ElementValue[] elementValues, ConstantPoolGen constantPoolGen) {
    super(value, constantPoolGen);
    if (value != 91) {
      throw new RuntimeException("Only element values of type array can be built with this ctor");
    }
    this.evalues = new ArrayList();
    int value2;
    for (int value3 = value2 = 0; value3 < elementValues.length; value3 = value2) {
      int value4 = value2;
      List values = this.evalues;
      value2++;
      values.add(elementValues[value4]);
    }
  }

  public void addElement(ElementValueGen elementValueGen) {
    this.evalues.add(elementValueGen);
  }

  public ArrayElementValueGen(ConstantPoolGen constantPoolGen) {
    super(91, constantPoolGen);
    this.evalues = new ArrayList();
  }

  public List getElementValues() {
    return this.evalues;
  }

  @Override
  public void dump(DataOutputStream dataOutputStream) throws IOException {
    dataOutputStream.writeByte(this.type);
    dataOutputStream.writeShort(this.evalues.size());
    Iterator iterator2;
    for (Iterator iterator3 = iterator2 = this.evalues.iterator();
        iterator3.hasNext();
        iterator3 = iterator2) {
      ((ElementValueGen) iterator2.next()).dump(dataOutputStream);
    }
  }

  @Override
  public String stringifyValue() {
    StringBuffer stringBuffer;
    (stringBuffer = new StringBuffer()).append("[");
    Iterator iterator2 = this.evalues.iterator();
    while (iterator2.hasNext()) {
      ElementValueGen elementValueGen = (ElementValueGen) iterator2.next();
      stringBuffer.append(elementValueGen.stringifyValue());
      if (iterator2.hasNext()) {
        stringBuffer.append(",");
      }
    }
    stringBuffer.append("]");
    return stringBuffer.toString();
  }

  public ArrayElementValueGen(
      ArrayElementValue arrayElementValue, ConstantPoolGen constantPoolGen, boolean matches) {
    super(91, constantPoolGen);
    this.evalues = new ArrayList();
    ElementValue[] elementValuesArray = arrayElementValue.getElementValuesArray();
    int value;
    for (int value2 = value = 0; value2 < elementValuesArray.length; value2 = value) {
      ElementValue elementValue = elementValuesArray[value];
      List values = this.evalues;
      value++;
      values.add(ElementValueGen.copy(elementValue, constantPoolGen, matches));
    }
  }
}
