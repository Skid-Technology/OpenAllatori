package com.allatori.lib.bcel.generic;

public class AALOAD extends ArrayInstruction implements StackProducer {

  @Override
  public void accept(Visitor visitor) {
    visitor.visitStackProducer(this);
    visitor.visitExceptionThrower(this);
    visitor.visitTypedInstruction(this);
    visitor.visitArrayInstruction(this);
    visitor.visitAALOAD(this);
  }

  public AALOAD() {
    super((short) 50);
  }
}
