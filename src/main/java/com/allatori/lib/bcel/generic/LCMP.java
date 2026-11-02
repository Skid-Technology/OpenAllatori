package com.allatori.lib.bcel.generic;

public class LCMP extends Instruction implements TypedInstruction, StackProducer, StackConsumer {

  public LCMP() {
    super((short) 148, (short) 1);
  }

  @Override
  public Type getType(ConstantPoolGen constantPoolGen) {
    return Type.LONG;
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visitTypedInstruction(this);
    visitor.visitStackProducer(this);
    visitor.visitStackConsumer(this);
    visitor.visitLCMP(this);
  }
}
