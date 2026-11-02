package com.allatori.lib.bcel.generic;

public class I2F extends ConversionInstruction {

  @Override
  public void accept(Visitor visitor) {
    visitor.visitTypedInstruction(this);
    visitor.visitStackProducer(this);
    visitor.visitStackConsumer(this);
    visitor.visitConversionInstruction(this);
    visitor.visitI2F(this);
  }

  public I2F() {
    super((short) 134);
  }
}
