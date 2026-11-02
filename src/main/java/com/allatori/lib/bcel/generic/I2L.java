package com.allatori.lib.bcel.generic;

public class I2L extends ConversionInstruction {

  @Override
  public void accept(Visitor visitor) {
    visitor.visitTypedInstruction(this);
    visitor.visitStackProducer(this);
    visitor.visitStackConsumer(this);
    visitor.visitConversionInstruction(this);
    visitor.visitI2L(this);
  }

  public I2L() {
    super((short) 133);
  }
}
