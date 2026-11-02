package com.allatori.lib.bcel.generic;

import com.allatori.lib.bcel.classfile.ConstantUtf8;
import com.allatori.lib.bcel.classfile.ElementValue;
import com.allatori.lib.bcel.classfile.ElementValuePair;
import java.io.DataOutputStream;
import java.io.IOException;

public class ElementValuePairGen {

  public ConstantPoolGen cpool;

  public ElementValueGen value;

  public int nameIdx;

  public final String getText() {
    return ((ConstantUtf8) this.cpool.getConstant(this.nameIdx)).getBytes();
  }

  public void dump(DataOutputStream dataOutputStream) throws IOException {
    dataOutputStream.writeShort(this.nameIdx);
    this.value.dump(dataOutputStream);
  }

  public int getNameIndex() {
    return this.nameIdx;
  }

  public void setNameIdx(int nameIdx) {
    this.nameIdx = nameIdx;
  }

  public ElementValuePairGen(
      String text, ElementValueGen elementValueGen, ConstantPoolGen constantPoolGen) {
    super();
    this.nameIdx = constantPoolGen.addUtf8(text);
    this.value = elementValueGen;
    this.cpool = constantPoolGen;
  }

  public ElementValuePair getElementNameValuePair() {
    ElementValue elementValue = this.value.getElementValue();
    return new ElementValuePair(this.nameIdx, elementValue, this.cpool.getConstantPool());
  }

  public final ElementValueGen getValue() {
    return this.value;
  }

  public ElementValuePairGen(
      ElementValuePair elementValuePair, ConstantPoolGen constantPoolGen, boolean matches) {
    super();
    this.cpool = constantPoolGen;
    ElementValuePairGen elementValuePairGen;
    if (matches) {
      elementValuePairGen = this;
      this.nameIdx = constantPoolGen.addUtf8(elementValuePair.getText());
    } else {
      elementValuePairGen = this;
      this.nameIdx = elementValuePair.getNameIndex();
    }
    elementValuePairGen.value =
        ElementValueGen.copy(elementValuePair.getValue(), constantPoolGen, matches);
  }

  public ElementValuePairGen(
      int nameIdx, ElementValueGen elementValueGen, ConstantPoolGen constantPoolGen) {
    super();
    this.nameIdx = nameIdx;
    this.value = elementValueGen;
    this.cpool = constantPoolGen;
  }
}
