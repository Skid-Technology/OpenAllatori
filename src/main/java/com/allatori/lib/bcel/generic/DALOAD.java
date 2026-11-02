package com.allatori.lib.bcel.generic;

public class DALOAD extends ArrayInstruction implements StackProducer {

  @Override
  public void accept(Visitor visitor) {
    visitor.visitStackProducer(this);
    visitor.visitExceptionThrower(this);
    visitor.visitTypedInstruction(this);
    visitor.visitArrayInstruction(this);
    visitor.visitDALOAD(this);
  }

  public DALOAD() {
    super((short) 49);
  }
}
