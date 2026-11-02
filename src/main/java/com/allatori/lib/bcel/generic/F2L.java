package com.allatori.lib.bcel.generic;

public class F2L extends ConversionInstruction {

  @Override
  public void accept(Visitor visitor) {
    visitor.visitTypedInstruction(this);
    visitor.visitStackProducer(this);
    visitor.visitStackConsumer(this);
    visitor.visitConversionInstruction(this);
    visitor.visitF2L(this);
  }

  public F2L() {
    super((short) 140);
  }
}
