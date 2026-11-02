package com.allatori.lib.bcel.generic;

public class I2S extends ConversionInstruction {

  public I2S() {
    super((short) 147);
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visitTypedInstruction(this);
    visitor.visitStackProducer(this);
    visitor.visitStackConsumer(this);
    visitor.visitConversionInstruction(this);
    visitor.visitI2S(this);
  }
}
