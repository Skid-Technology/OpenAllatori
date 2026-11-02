package com.allatori.lib.bcel.generic;

public class LAND extends ArithmeticInstruction {

  public LAND() {
    super((short) 127);
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visitTypedInstruction(this);
    visitor.visitStackProducer(this);
    visitor.visitStackConsumer(this);
    visitor.visitArithmeticInstruction(this);
    visitor.visitLAND(this);
  }
}
