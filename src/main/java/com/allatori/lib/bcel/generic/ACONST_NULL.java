package com.allatori.lib.bcel.generic;

public class ACONST_NULL extends Instruction implements PushInstruction, TypedInstruction {

  public ACONST_NULL() {
    super((short) 1, (short) 1);
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visitStackProducer(this);
    visitor.visitPushInstruction(this);
    visitor.visitTypedInstruction(this);
    visitor.visitACONST_NULL(this);
  }

  @Override
  public Type getType(ConstantPoolGen constantPoolGen) {
    return Type.NULL;
  }
}
