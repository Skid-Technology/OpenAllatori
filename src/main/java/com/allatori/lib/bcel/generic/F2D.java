package com.allatori.lib.bcel.generic;

public class F2D extends ConversionInstruction {

  @Override
  public void accept(Visitor visitor) {
    visitor.visitTypedInstruction(this);
    visitor.visitStackProducer(this);
    visitor.visitStackConsumer(this);
    visitor.visitConversionInstruction(this);
    visitor.visitF2D(this);
  }

  public F2D() {
    super((short) 141);
  }
}
