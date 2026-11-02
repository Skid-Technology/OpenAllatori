package com.allatori.lib.bcel.generic;

public class FRETURN extends ReturnInstruction {

  @Override
  public void accept(Visitor visitor) {
    visitor.visitExceptionThrower(this);
    visitor.visitTypedInstruction(this);
    visitor.visitStackConsumer(this);
    visitor.visitReturnInstruction(this);
    visitor.visitFRETURN(this);
  }

  public FRETURN() {
    super((short) 174);
  }
}
