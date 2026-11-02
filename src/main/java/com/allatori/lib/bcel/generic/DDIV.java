package com.allatori.lib.bcel.generic;

public class DDIV extends ArithmeticInstruction {

  public DDIV() {
    super((short) 111);
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visitTypedInstruction(this);
    visitor.visitStackProducer(this);
    visitor.visitStackConsumer(this);
    visitor.visitArithmeticInstruction(this);
    visitor.visitDDIV(this);
  }
}
