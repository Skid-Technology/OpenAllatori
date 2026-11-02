package com.allatori.lib.bcel.generic;

public class BALOAD extends ArrayInstruction implements StackProducer {

  @Override
  public void accept(Visitor visitor) {
    visitor.visitStackProducer(this);
    visitor.visitExceptionThrower(this);
    visitor.visitTypedInstruction(this);
    visitor.visitArrayInstruction(this);
    visitor.visitBALOAD(this);
  }

  public BALOAD() {
    super((short) 51);
  }
}
