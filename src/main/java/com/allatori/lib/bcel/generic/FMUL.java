package com.allatori.lib.bcel.generic;

public class FMUL extends ArithmeticInstruction {

  @Override
  public void accept(Visitor visitor) {
    visitor.visitTypedInstruction(this);
    visitor.visitStackProducer(this);
    visitor.visitStackConsumer(this);
    visitor.visitArithmeticInstruction(this);
    visitor.visitFMUL(this);
  }

  public FMUL() {
    super((short) 106);
  }
}
