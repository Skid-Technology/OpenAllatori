package com.allatori.lib.bcel.generic;

public class LOR extends ArithmeticInstruction {

  public LOR() {
    super((short) 129);
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visitTypedInstruction(this);
    visitor.visitStackProducer(this);
    visitor.visitStackConsumer(this);
    visitor.visitArithmeticInstruction(this);
    visitor.visitLOR(this);
  }
}
