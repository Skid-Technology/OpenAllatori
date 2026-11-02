package com.allatori.lib.bcel.generic;

public class L2I extends ConversionInstruction {

  @Override
  public void accept(Visitor visitor) {
    visitor.visitTypedInstruction(this);
    visitor.visitStackProducer(this);
    visitor.visitStackConsumer(this);
    visitor.visitConversionInstruction(this);
    visitor.visitL2I(this);
  }

  public L2I() {
    super((short) 136);
  }
}
