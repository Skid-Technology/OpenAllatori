package com.allatori.lib.bcel.generic;

public class L2D extends ConversionInstruction {

  public L2D() {
    super((short) 138);
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visitTypedInstruction(this);
    visitor.visitStackProducer(this);
    visitor.visitStackConsumer(this);
    visitor.visitConversionInstruction(this);
    visitor.visitL2D(this);
  }
}
