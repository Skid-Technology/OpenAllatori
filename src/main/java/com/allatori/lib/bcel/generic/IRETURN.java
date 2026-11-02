package com.allatori.lib.bcel.generic;

public class IRETURN extends ReturnInstruction {

  public IRETURN() {
    super((short) 172);
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visitExceptionThrower(this);
    visitor.visitTypedInstruction(this);
    visitor.visitStackConsumer(this);
    visitor.visitReturnInstruction(this);
    visitor.visitIRETURN(this);
  }
}
