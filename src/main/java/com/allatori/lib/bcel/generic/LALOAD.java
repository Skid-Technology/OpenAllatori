package com.allatori.lib.bcel.generic;

public class LALOAD extends ArrayInstruction implements StackProducer {

  @Override
  public void accept(Visitor visitor) {
    visitor.visitStackProducer(this);
    visitor.visitExceptionThrower(this);
    visitor.visitTypedInstruction(this);
    visitor.visitArrayInstruction(this);
    visitor.visitLALOAD(this);
  }

  public LALOAD() {
    super((short) 47);
  }
}
