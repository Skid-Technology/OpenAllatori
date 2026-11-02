package com.allatori.lib.bcel.generic;

public class IUSHR extends ArithmeticInstruction {

  @Override
  public void accept(Visitor visitor) {
    visitor.visitTypedInstruction(this);
    visitor.visitStackProducer(this);
    visitor.visitStackConsumer(this);
    visitor.visitArithmeticInstruction(this);
    visitor.visitIUSHR(this);
  }

  public IUSHR() {
    super((short) 124);
  }
}
