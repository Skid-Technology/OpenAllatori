package com.allatori.lib.bcel.generic;

public class LASTORE extends ArrayInstruction implements StackConsumer {

  @Override
  public void accept(Visitor visitor) {
    visitor.visitStackConsumer(this);
    visitor.visitExceptionThrower(this);
    visitor.visitTypedInstruction(this);
    visitor.visitArrayInstruction(this);
    visitor.visitLASTORE(this);
  }

  public LASTORE() {
    super((short) 80);
  }
}
