package com.allatori.lib.bcel.generic;

public class LUSHR extends ArithmeticInstruction {

  @Override
  public void accept(Visitor visitor) {
    visitor.visitTypedInstruction(this);
    visitor.visitStackProducer(this);
    visitor.visitStackConsumer(this);
    visitor.visitArithmeticInstruction(this);
    visitor.visitLUSHR(this);
  }

  public LUSHR() {
    super((short) 125);
  }
}
