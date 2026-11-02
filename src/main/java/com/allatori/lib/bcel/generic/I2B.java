package com.allatori.lib.bcel.generic;

public class I2B extends ConversionInstruction {

  @Override
  public void accept(Visitor visitor) {
    visitor.visitTypedInstruction(this);
    visitor.visitStackProducer(this);
    visitor.visitStackConsumer(this);
    visitor.visitConversionInstruction(this);
    visitor.visitI2B(this);
  }

  public I2B() {
    super((short) 145);
  }
}
