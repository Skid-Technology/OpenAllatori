package com.allatori.lib.bcel.generic;

public class ISHR extends ArithmeticInstruction {

  public ISHR() {
    super((short) 122);
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visitTypedInstruction(this);
    visitor.visitStackProducer(this);
    visitor.visitStackConsumer(this);
    visitor.visitArithmeticInstruction(this);
    visitor.visitISHR(this);
  }
}
