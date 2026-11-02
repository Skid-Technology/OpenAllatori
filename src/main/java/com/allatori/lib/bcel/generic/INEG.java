package com.allatori.lib.bcel.generic;

public class INEG extends ArithmeticInstruction {

  public INEG() {
    super((short) 116);
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visitTypedInstruction(this);
    visitor.visitStackProducer(this);
    visitor.visitStackConsumer(this);
    visitor.visitArithmeticInstruction(this);
    visitor.visitINEG(this);
  }
}
