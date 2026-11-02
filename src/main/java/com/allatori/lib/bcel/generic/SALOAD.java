package com.allatori.lib.bcel.generic;

public class SALOAD extends ArrayInstruction implements StackProducer {

  public SALOAD() {
    super((short) 53);
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visitStackProducer(this);
    visitor.visitExceptionThrower(this);
    visitor.visitTypedInstruction(this);
    visitor.visitArrayInstruction(this);
    visitor.visitSALOAD(this);
  }
}
