package com.allatori.lib.bcel.generic;

public class BASTORE extends ArrayInstruction implements StackConsumer {

  @Override
  public void accept(Visitor visitor) {
    visitor.visitStackConsumer(this);
    visitor.visitExceptionThrower(this);
    visitor.visitTypedInstruction(this);
    visitor.visitArrayInstruction(this);
    visitor.visitBASTORE(this);
  }

  public BASTORE() {
    super((short) 84);
  }
}
