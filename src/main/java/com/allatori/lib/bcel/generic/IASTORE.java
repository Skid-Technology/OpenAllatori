package com.allatori.lib.bcel.generic;

public class IASTORE extends ArrayInstruction implements StackConsumer {

  @Override
  public void accept(Visitor visitor) {
    visitor.visitStackConsumer(this);
    visitor.visitExceptionThrower(this);
    visitor.visitTypedInstruction(this);
    visitor.visitArrayInstruction(this);
    visitor.visitIASTORE(this);
  }

  public IASTORE() {
    super((short) 79);
  }
}
