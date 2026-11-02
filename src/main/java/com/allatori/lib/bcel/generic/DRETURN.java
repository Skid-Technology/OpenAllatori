package com.allatori.lib.bcel.generic;

public class DRETURN extends ReturnInstruction {

  @Override
  public void accept(Visitor visitor) {
    visitor.visitExceptionThrower(this);
    visitor.visitTypedInstruction(this);
    visitor.visitStackConsumer(this);
    visitor.visitReturnInstruction(this);
    visitor.visitDRETURN(this);
  }

  public DRETURN() {
    super((short) 175);
  }
}
