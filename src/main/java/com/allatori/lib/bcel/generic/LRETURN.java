package com.allatori.lib.bcel.generic;

public class LRETURN extends ReturnInstruction {

  @Override
  public void accept(Visitor visitor) {
    visitor.visitExceptionThrower(this);
    visitor.visitTypedInstruction(this);
    visitor.visitStackConsumer(this);
    visitor.visitReturnInstruction(this);
    visitor.visitLRETURN(this);
  }

  public LRETURN() {
    super((short) 173);
  }
}
