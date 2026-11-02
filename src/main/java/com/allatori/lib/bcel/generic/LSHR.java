package com.allatori.lib.bcel.generic;

public class LSHR extends ArithmeticInstruction {

  @Override
  public void accept(Visitor visitor) {
    visitor.visitTypedInstruction(this);
    visitor.visitStackProducer(this);
    visitor.visitStackConsumer(this);
    visitor.visitArithmeticInstruction(this);
    visitor.visitLSHR(this);
  }

  public LSHR() {
    super((short) 123);
  }
}
