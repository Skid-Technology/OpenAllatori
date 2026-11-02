package com.allatori.lib.bcel.generic;

public class AASTORE extends ArrayInstruction implements StackConsumer {

  @Override
  public void accept(Visitor visitor) {
    visitor.visitStackConsumer(this);
    visitor.visitExceptionThrower(this);
    visitor.visitTypedInstruction(this);
    visitor.visitArrayInstruction(this);
    visitor.visitAASTORE(this);
  }

  public AASTORE() {
    super((short) 83);
  }
}
