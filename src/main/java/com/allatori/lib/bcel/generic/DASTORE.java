package com.allatori.lib.bcel.generic;

public class DASTORE extends ArrayInstruction implements StackConsumer {

  @Override
  public void accept(Visitor visitor) {
    visitor.visitStackConsumer(this);
    visitor.visitExceptionThrower(this);
    visitor.visitTypedInstruction(this);
    visitor.visitArrayInstruction(this);
    visitor.visitDASTORE(this);
  }

  public DASTORE() {
    super((short) 82);
  }
}
