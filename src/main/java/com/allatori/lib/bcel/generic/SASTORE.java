package com.allatori.lib.bcel.generic;

public class SASTORE extends ArrayInstruction implements StackConsumer {

  @Override
  public void accept(Visitor visitor) {
    visitor.visitStackConsumer(this);
    visitor.visitExceptionThrower(this);
    visitor.visitTypedInstruction(this);
    visitor.visitArrayInstruction(this);
    visitor.visitSASTORE(this);
  }

  public SASTORE() {
    super((short) 86);
  }
}
