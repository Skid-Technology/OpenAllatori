package com.allatori.lib.bcel.generic;

public class LSHL extends ArithmeticInstruction {

  public LSHL() {
    super((short) 121);
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visitTypedInstruction(this);
    visitor.visitStackProducer(this);
    visitor.visitStackConsumer(this);
    visitor.visitArithmeticInstruction(this);
    visitor.visitLSHL(this);
  }
}
