package com.allatori.lib.bcel.generic;

public class FSUB extends ArithmeticInstruction {

  @Override
  public void accept(Visitor visitor) {
    visitor.visitTypedInstruction(this);
    visitor.visitStackProducer(this);
    visitor.visitStackConsumer(this);
    visitor.visitArithmeticInstruction(this);
    visitor.visitFSUB(this);
  }

  public FSUB() {
    super((short) 102);
  }
}
