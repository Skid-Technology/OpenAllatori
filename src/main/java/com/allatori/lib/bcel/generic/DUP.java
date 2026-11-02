package com.allatori.lib.bcel.generic;

public class DUP extends StackInstruction implements PushInstruction {

  @Override
  public void accept(Visitor visitor) {
    visitor.visitStackProducer(this);
    visitor.visitPushInstruction(this);
    visitor.visitStackInstruction(this);
    visitor.visitDUP(this);
  }

  public DUP() {
    super((short) 89);
  }
}
