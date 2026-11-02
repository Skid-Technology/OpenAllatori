package com.allatori.lib.bcel.generic;

public class LADD extends ArithmeticInstruction {

  @Override
  public void accept(Visitor visitor) {
    visitor.visitTypedInstruction(this);
    visitor.visitStackProducer(this);
    visitor.visitStackConsumer(this);
    visitor.visitArithmeticInstruction(this);
    visitor.visitLADD(this);
  }

  public LADD() {
    super((short) 97);
  }
}
