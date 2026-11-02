package com.allatori.lib.bcel.generic;

public class LNEG extends ArithmeticInstruction {

  @Override
  public void accept(Visitor visitor) {
    visitor.visitTypedInstruction(this);
    visitor.visitStackProducer(this);
    visitor.visitStackConsumer(this);
    visitor.visitArithmeticInstruction(this);
    visitor.visitLNEG(this);
  }

  public LNEG() {
    super((short) 117);
  }
}
