package com.allatori.lib.bcel.generic;

import com.allatori.lib.bcel.classfile.Constant;
import com.allatori.lib.bcel.classfile.ConstantDouble;
import com.allatori.lib.bcel.classfile.ConstantLong;

public class LDC2_W extends CPInstruction implements PushInstruction, TypedInstruction {

  @Override
  public void accept(Visitor visitor) {
    visitor.visitStackProducer(this);
    visitor.visitPushInstruction(this);
    visitor.visitTypedInstruction(this);
    visitor.visitCPInstruction(this);
    visitor.visitLDC2_W(this);
  }

  @Override
  public Type getType(ConstantPoolGen constantPoolGen) {
    switch (constantPoolGen.getConstantPool().getConstant(this.index).getTag()) {
      case 5:
        return Type.LONG;
      case 6:
        return Type.DOUBLE;
      default:
        throw new RuntimeException(("Unknown constant type " + this.opcode));
    }
  }

  public Number getValue(ConstantPoolGen constantPoolGen) {
    Constant constant;
    switch ((constant = constantPoolGen.getConstantPool().getConstant(this.index)).getTag()) {
      case 5:
        return new Long(((ConstantLong) constant).getBytes());
      case 6:
        return new Double(((ConstantDouble) constant).getBytes());
      default:
        throw new RuntimeException(("Unknown or invalid constant type at " + this.index));
    }
  }

  public LDC2_W(int value) {
    super((short) 20, value);
  }

  public LDC2_W() {}
}
