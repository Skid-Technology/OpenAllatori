package com.allatori.lib.bcel.generic;

public class IMUL extends ArithmeticInstruction {

  public IMUL() {
    super((short) 104);
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visitTypedInstruction(this);
    visitor.visitStackProducer(this);
    visitor.visitStackConsumer(this);
    visitor.visitArithmeticInstruction(this);
    visitor.visitIMUL(this);
  }
}
