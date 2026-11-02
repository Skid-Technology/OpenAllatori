package com.allatori.lib.bcel.generic;

public class FALOAD extends ArrayInstruction implements StackProducer {

  public FALOAD() {
    super((short) 48);
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visitStackProducer(this);
    visitor.visitExceptionThrower(this);
    visitor.visitTypedInstruction(this);
    visitor.visitArrayInstruction(this);
    visitor.visitFALOAD(this);
  }
}
