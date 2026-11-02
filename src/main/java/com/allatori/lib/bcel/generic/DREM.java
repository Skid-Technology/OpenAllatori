package com.allatori.lib.bcel.generic;

public class DREM extends ArithmeticInstruction {

  @Override
  public void accept(Visitor visitor) {
    visitor.visitTypedInstruction(this);
    visitor.visitStackProducer(this);
    visitor.visitStackConsumer(this);
    visitor.visitArithmeticInstruction(this);
    visitor.visitDREM(this);
  }

  public DREM() {
    super((short) 115);
  }
}
