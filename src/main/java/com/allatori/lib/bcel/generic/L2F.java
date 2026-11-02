package com.allatori.lib.bcel.generic;

public class L2F extends ConversionInstruction {

  @Override
  public void accept(Visitor visitor) {
    visitor.visitTypedInstruction(this);
    visitor.visitStackProducer(this);
    visitor.visitStackConsumer(this);
    visitor.visitConversionInstruction(this);
    visitor.visitL2F(this);
  }

  public L2F() {
    super((short) 137);
  }
}
