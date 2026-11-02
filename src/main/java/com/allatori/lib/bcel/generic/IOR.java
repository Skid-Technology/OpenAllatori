package com.allatori.lib.bcel.generic;

public class IOR extends ArithmeticInstruction {

  @Override
  public void accept(Visitor visitor) {
    visitor.visitTypedInstruction(this);
    visitor.visitStackProducer(this);
    visitor.visitStackConsumer(this);
    visitor.visitArithmeticInstruction(this);
    visitor.visitIOR(this);
  }

  public IOR() {
    super((short) 128);
  }
}
