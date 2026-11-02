package com.allatori.lib.bcel.generic;

public class DNEG extends ArithmeticInstruction {

  @Override
  public void accept(Visitor visitor) {
    visitor.visitTypedInstruction(this);
    visitor.visitStackProducer(this);
    visitor.visitStackConsumer(this);
    visitor.visitArithmeticInstruction(this);
    visitor.visitDNEG(this);
  }

  public DNEG() {
    super((short) 119);
  }
}
