package com.allatori.lib.bcel.generic;

public class ISHL extends ArithmeticInstruction {

  @Override
  public void accept(Visitor visitor) {
    visitor.visitTypedInstruction(this);
    visitor.visitStackProducer(this);
    visitor.visitStackConsumer(this);
    visitor.visitArithmeticInstruction(this);
    visitor.visitISHL(this);
  }

  public ISHL() {
    super((short) 120);
  }
}
