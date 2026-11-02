package com.allatori.lib.bcel.generic;

public class FADD extends ArithmeticInstruction {

  public FADD() {
    super((short) 98);
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visitTypedInstruction(this);
    visitor.visitStackProducer(this);
    visitor.visitStackConsumer(this);
    visitor.visitArithmeticInstruction(this);
    visitor.visitFADD(this);
  }
}
