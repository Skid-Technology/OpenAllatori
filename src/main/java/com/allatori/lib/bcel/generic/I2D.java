package com.allatori.lib.bcel.generic;

public class I2D extends ConversionInstruction {

  public I2D() {
    super((short) 135);
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visitTypedInstruction(this);
    visitor.visitStackProducer(this);
    visitor.visitStackConsumer(this);
    visitor.visitConversionInstruction(this);
    visitor.visitI2D(this);
  }
}
