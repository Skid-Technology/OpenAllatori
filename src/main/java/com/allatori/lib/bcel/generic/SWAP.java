package com.allatori.lib.bcel.generic;

public class SWAP extends StackInstruction implements StackConsumer, StackProducer {

  public SWAP() {
    super((short) 95);
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visitStackConsumer(this);
    visitor.visitStackProducer(this);
    visitor.visitStackInstruction(this);
    visitor.visitSWAP(this);
  }
}
