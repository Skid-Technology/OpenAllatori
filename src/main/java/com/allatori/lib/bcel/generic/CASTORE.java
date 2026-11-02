package com.allatori.lib.bcel.generic;

public class CASTORE extends ArrayInstruction implements StackConsumer {

  public CASTORE() {
    super((short) 85);
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visitStackConsumer(this);
    visitor.visitExceptionThrower(this);
    visitor.visitTypedInstruction(this);
    visitor.visitArrayInstruction(this);
    visitor.visitCASTORE(this);
  }
}
