package com.allatori.lib.bcel.generic;

public class D2F extends ConversionInstruction {

  @Override
  public void accept(Visitor visitor) {
    visitor.visitTypedInstruction(this);
    visitor.visitStackProducer(this);
    visitor.visitStackConsumer(this);
    visitor.visitConversionInstruction(this);
    visitor.visitD2F(this);
  }

  public D2F() {
    super((short) 144);
  }
}
