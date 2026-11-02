package com.allatori.lib.bcel.generic;

import com.allatori.lib.bcel.classfile.ClassElementValue;
import com.allatori.lib.bcel.classfile.ConstantUtf8;
import com.allatori.lib.bcel.classfile.ElementValue;
import java.io.DataOutputStream;
import java.io.IOException;

public class ClassElementValueGen extends ElementValueGen {

  public int idx;

  @Override
  public void dump(DataOutputStream dataOutputStream) throws IOException {
    dataOutputStream.writeByte(this.type);
    dataOutputStream.writeShort(this.idx);
  }

  public ClassElementValueGen(ObjectType objectType, ConstantPoolGen constantPoolGen) {
    super(99, constantPoolGen);
    this.idx = constantPoolGen.addUtf8(objectType.getSignature());
  }

  public String getIdx() {
    return ((ConstantUtf8) this.getConstantPool().getConstant(this.idx)).getBytes();
  }

  @Override
  public ElementValue getElementValue() {
    return new ClassElementValue(this.type, this.idx, this.cpGen.getConstantPool());
  }

  public void setIdx(int idx) {
    this.idx = idx;
  }

  public ClassElementValueGen(int idx, ConstantPoolGen constantPoolGen) {
    super(99, constantPoolGen);
    this.idx = idx;
  }

  @Override
  public String stringifyValue() {
    return this.getIdx();
  }

  public ClassElementValueGen(
      ClassElementValue classElementValue, ConstantPoolGen constantPoolGen, boolean matches) {
    super(99, constantPoolGen);
    if (matches) {
      this.idx = constantPoolGen.addUtf8(classElementValue.getText2());
    } else {
      this.idx = classElementValue.getIndex();
    }
  }

  public int getIndex() {
    return this.idx;
  }
}
