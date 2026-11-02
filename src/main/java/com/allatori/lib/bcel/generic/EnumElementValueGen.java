package com.allatori.lib.bcel.generic;

import com.allatori.lib.bcel.classfile.ConstantUtf8;
import com.allatori.lib.bcel.classfile.ElementValue;
import com.allatori.lib.bcel.classfile.EnumElementValue;
import java.io.DataOutputStream;
import java.io.IOException;

public class EnumElementValueGen extends ElementValueGen {

  public int typeIdx;

  public int valueIdx;

  public int getValueIdx() {
    return this.valueIdx;
  }

  public int getTypeIdx() {
    return this.typeIdx;
  }

  public void setTypeIdx(int typeIdx) {
    this.typeIdx = typeIdx;
  }

  public String getTypeIdx2() {
    return ((ConstantUtf8) this.getConstantPool().getConstant(this.typeIdx)).getBytes();
  }

  public String getValueIdx2() {
    return ((ConstantUtf8) this.getConstantPool().getConstant(this.valueIdx)).getBytes();
  }

  @Override
  public String stringifyValue() {
    return ((ConstantUtf8) this.getConstantPool().getConstant(this.valueIdx)).getBytes();
  }

  @Override
  public ElementValue getElementValue() {
    return new EnumElementValue(
        this.type, this.typeIdx, this.valueIdx, this.cpGen.getConstantPool());
  }

  @Override
  public void dump(DataOutputStream dataOutputStream) throws IOException {
    dataOutputStream.writeByte(this.type);
    dataOutputStream.writeShort(this.typeIdx);
    dataOutputStream.writeShort(this.valueIdx);
  }

  public void setValueIdx(int valueIdx) {
    this.valueIdx = valueIdx;
  }

  public EnumElementValueGen(
      EnumElementValue enumElementValue, ConstantPoolGen constantPoolGen, boolean matches) {
    super(101, constantPoolGen);
    if (matches) {
      this.typeIdx = constantPoolGen.addUtf8(enumElementValue.getText2());
      this.valueIdx = constantPoolGen.addUtf8(enumElementValue.getText3());
    } else {
      this.typeIdx = enumElementValue.getTypeIdx();
      this.valueIdx = enumElementValue.getValueIdx();
    }
  }

  public EnumElementValueGen(ObjectType objectType, String text, ConstantPoolGen constantPoolGen) {
    super(101, constantPoolGen);
    this.typeIdx = constantPoolGen.addUtf8(objectType.getSignature());
    this.valueIdx = constantPoolGen.addUtf8(text);
  }

  public EnumElementValueGen(int typeIdx, int valueIdx, ConstantPoolGen constantPoolGen) {
    super(101, constantPoolGen);
    if (this.type != 101) {
      throw new RuntimeException("Only element values of type enum can be built with this ctor");
    }
    this.typeIdx = typeIdx;
    this.valueIdx = valueIdx;
  }
}
