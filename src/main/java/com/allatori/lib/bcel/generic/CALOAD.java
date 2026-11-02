package com.allatori.lib.bcel.generic;

public class CALOAD extends ArrayInstruction implements StackProducer {

  public CALOAD() {
    super((short) 52);
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visitStackProducer(this);
    visitor.visitExceptionThrower(this);
    visitor.visitTypedInstruction(this);
    visitor.visitArrayInstruction(this);
    visitor.visitCALOAD(this);
  }
}
