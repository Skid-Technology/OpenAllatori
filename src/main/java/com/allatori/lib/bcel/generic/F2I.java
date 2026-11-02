package com.allatori.lib.bcel.generic;

public class F2I extends ConversionInstruction {

  @Override
  public void accept(Visitor visitor) {
    visitor.visitTypedInstruction(this);
    visitor.visitStackProducer(this);
    visitor.visitStackConsumer(this);
    visitor.visitConversionInstruction(this);
    visitor.visitF2I(this);
  }

  public F2I() {
    super((short) 139);
  }
}
