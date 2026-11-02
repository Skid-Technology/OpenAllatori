package com.allatori.lib.bcel.generic;

import com.allatori.lib.bcel.Constants;
import com.allatori.lib.bcel.classfile.ConstantPool;

public abstract class FieldInstruction extends FieldOrMethod implements TypedInstruction {

  @Override
  public Type getType(ConstantPoolGen constantPoolGen) {
    return this.getType2(constantPoolGen);
  }

  public Type getType2(ConstantPoolGen constantPoolGen) {
    return Type.getType(this.getText3(constantPoolGen));
  }

  public String getFieldName(ConstantPoolGen constantPoolGen) {
    return this.getText(constantPoolGen);
  }

  public FieldInstruction(short value, int value2) {
    super(value, value2);
  }

  public FieldInstruction() {}

  @Override
  public String toString(ConstantPool constantPool) {
    return "" + Constants.TEXTS8[this.opcode] + " " + constantPool.getText(this.index, (byte) 9);
  }

  public int getFieldSize(ConstantPoolGen constantPoolGen) {
    return this.getType(constantPoolGen).getSize();
  }
}
