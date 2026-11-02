package com.allatori.lib.bcel.generic;

public class DMUL extends ArithmeticInstruction {

  public DMUL() {
    super((short) 107);
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visitTypedInstruction(this);
    visitor.visitStackProducer(this);
    visitor.visitStackConsumer(this);
    visitor.visitArithmeticInstruction(this);
    visitor.visitDMUL(this);
  }
}
