package com.allatori.lib.bcel.generic;

public class IAND extends ArithmeticInstruction {

  public IAND() {
    super((short) 126);
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visitTypedInstruction(this);
    visitor.visitStackProducer(this);
    visitor.visitStackConsumer(this);
    visitor.visitArithmeticInstruction(this);
    visitor.visitIAND(this);
  }
}
