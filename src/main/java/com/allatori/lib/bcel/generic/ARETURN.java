package com.allatori.lib.bcel.generic;

public class ARETURN extends ReturnInstruction {

  @Override
  public void accept(Visitor visitor) {
    visitor.visitExceptionThrower(this);
    visitor.visitTypedInstruction(this);
    visitor.visitStackConsumer(this);
    visitor.visitReturnInstruction(this);
    visitor.visitARETURN(this);
  }

  public ARETURN() {
    super((short) 176);
  }
}
