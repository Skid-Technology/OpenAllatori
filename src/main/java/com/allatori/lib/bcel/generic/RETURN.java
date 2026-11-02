package com.allatori.lib.bcel.generic;

public class RETURN extends ReturnInstruction {

  public RETURN() {
    super((short) 177);
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visitExceptionThrower(this);
    visitor.visitTypedInstruction(this);
    visitor.visitStackConsumer(this);
    visitor.visitReturnInstruction(this);
    visitor.visitRETURN(this);
  }
}
