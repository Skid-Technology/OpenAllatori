package com.allatori.lib.bcel.generic;

public class DSUB extends ArithmeticInstruction {

  @Override
  public void accept(Visitor visitor) {
    visitor.visitTypedInstruction(this);
    visitor.visitStackProducer(this);
    visitor.visitStackConsumer(this);
    visitor.visitArithmeticInstruction(this);
    visitor.visitDSUB(this);
  }

  public DSUB() {
    super((short) 103);
  }
}
