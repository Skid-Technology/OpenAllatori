package com.allatori.lib.bcel.generic;

public class LMUL extends ArithmeticInstruction {

  @Override
  public void accept(Visitor visitor) {
    visitor.visitTypedInstruction(this);
    visitor.visitStackProducer(this);
    visitor.visitStackConsumer(this);
    visitor.visitArithmeticInstruction(this);
    visitor.visitLMUL(this);
  }

  public LMUL() {
    super((short) 105);
  }
}
