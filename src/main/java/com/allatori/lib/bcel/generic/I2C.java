package com.allatori.lib.bcel.generic;

public class I2C extends ConversionInstruction {

  @Override
  public void accept(Visitor visitor) {
    visitor.visitTypedInstruction(this);
    visitor.visitStackProducer(this);
    visitor.visitStackConsumer(this);
    visitor.visitConversionInstruction(this);
    visitor.visitI2C(this);
  }

  public I2C() {
    super((short) 146);
  }
}
