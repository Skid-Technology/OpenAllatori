package com.allatori.lib.bcel.generic;

public class IALOAD extends ArrayInstruction implements StackProducer {

  public IALOAD() {
    super((short) 46);
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visitStackProducer(this);
    visitor.visitExceptionThrower(this);
    visitor.visitTypedInstruction(this);
    visitor.visitArrayInstruction(this);
    visitor.visitIALOAD(this);
  }
}
