package com.allatori.lib.bcel.generic;

public class D2I extends ConversionInstruction {

  @Override
  public void accept(Visitor visitor) {
    visitor.visitTypedInstruction(this);
    visitor.visitStackProducer(this);
    visitor.visitStackConsumer(this);
    visitor.visitConversionInstruction(this);
    visitor.visitD2I(this);
  }

  public D2I() {
    super((short) 142);
  }
}
