package com.allatori.lib.bcel.generic;

public class IXOR extends ArithmeticInstruction {

  public IXOR() {
    super((short) 130);
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visitTypedInstruction(this);
    visitor.visitStackProducer(this);
    visitor.visitStackConsumer(this);
    visitor.visitArithmeticInstruction(this);
    visitor.visitIXOR(this);
  }
}
