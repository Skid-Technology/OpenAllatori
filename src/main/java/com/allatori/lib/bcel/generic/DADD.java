package com.allatori.lib.bcel.generic;

public class DADD extends ArithmeticInstruction {

  public DADD() {
    super((short) 99);
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visitTypedInstruction(this);
    visitor.visitStackProducer(this);
    visitor.visitStackConsumer(this);
    visitor.visitArithmeticInstruction(this);
    visitor.visitDADD(this);
  }
}
