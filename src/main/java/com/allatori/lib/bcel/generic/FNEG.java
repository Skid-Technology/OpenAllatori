package com.allatori.lib.bcel.generic;

public class FNEG extends ArithmeticInstruction {

  public FNEG() {
    super((short) 118);
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visitTypedInstruction(this);
    visitor.visitStackProducer(this);
    visitor.visitStackConsumer(this);
    visitor.visitArithmeticInstruction(this);
    visitor.visitFNEG(this);
  }
}
