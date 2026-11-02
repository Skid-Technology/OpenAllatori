package com.allatori.lib.bcel.generic;

public class LXOR extends ArithmeticInstruction {

  @Override
  public void accept(Visitor visitor) {
    visitor.visitTypedInstruction(this);
    visitor.visitStackProducer(this);
    visitor.visitStackConsumer(this);
    visitor.visitArithmeticInstruction(this);
    visitor.visitLXOR(this);
  }

  public LXOR() {
    super((short) 131);
  }
}
