package com.allatori.lib.bcel.generic;

public class FDIV extends ArithmeticInstruction {

  @Override
  public void accept(Visitor visitor) {
    visitor.visitTypedInstruction(this);
    visitor.visitStackProducer(this);
    visitor.visitStackConsumer(this);
    visitor.visitArithmeticInstruction(this);
    visitor.visitFDIV(this);
  }

  public FDIV() {
    super((short) 110);
  }
}
