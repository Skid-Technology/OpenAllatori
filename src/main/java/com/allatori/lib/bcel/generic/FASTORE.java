package com.allatori.lib.bcel.generic;

public class FASTORE extends ArrayInstruction implements StackConsumer {

  @Override
  public void accept(Visitor visitor) {
    visitor.visitStackConsumer(this);
    visitor.visitExceptionThrower(this);
    visitor.visitTypedInstruction(this);
    visitor.visitArrayInstruction(this);
    visitor.visitFASTORE(this);
  }

  public FASTORE() {
    super((short) 81);
  }
}
