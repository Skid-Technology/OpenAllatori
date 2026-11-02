package com.allatori.lib.bcel.classfile;

import java.io.DataOutputStream;
import java.io.IOException;

public class ElementValuePair {

  public ConstantPool constantPool;

  public ElementValue elementValue;

  public int elementNameIndex;

  public int getNameIndex() {
    return this.elementNameIndex;
  }

  public void dump(DataOutputStream dataOutputStream) throws IOException {
    dataOutputStream.writeShort(this.elementNameIndex);
    this.elementValue.dump(dataOutputStream);
  }

  public String createText() {
    StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(this.getText()).append("=").append(this.getValue().createText());
    return stringBuffer.toString();
  }

  public final ElementValue getValue() {
    return this.elementValue;
  }

  public String getText() {
    return ((ConstantUtf8) this.constantPool.getConstant(this.elementNameIndex, (byte) 1))
        .getBytes();
  }

  public ElementValuePair(
      int elementNameIndex, ElementValue elementValue2, ConstantPool constantPool2) {
    super();
    this.elementValue = elementValue2;
    this.elementNameIndex = elementNameIndex;
    this.constantPool = constantPool2;
  }
}
