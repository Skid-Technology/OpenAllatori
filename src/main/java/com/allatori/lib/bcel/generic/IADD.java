package com.allatori.lib.bcel.generic;

public class IADD extends ArithmeticInstruction {

  @Override
  public void accept(Visitor visitor) {
    visitor.visitTypedInstruction(this);
    visitor.visitStackProducer(this);
    visitor.visitStackConsumer(this);
    visitor.visitArithmeticInstruction(this);
    visitor.visitIADD(this);
  }

  public IADD() {
    super((short) 96);
  }
}
