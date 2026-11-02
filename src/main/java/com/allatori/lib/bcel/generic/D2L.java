package com.allatori.lib.bcel.generic;

public class D2L extends ConversionInstruction {

  @Override
  public void accept(Visitor visitor) {
    visitor.visitTypedInstruction(this);
    visitor.visitStackProducer(this);
    visitor.visitStackConsumer(this);
    visitor.visitConversionInstruction(this);
    visitor.visitD2L(this);
  }

  public D2L() {
    super((short) 143);
  }
}
