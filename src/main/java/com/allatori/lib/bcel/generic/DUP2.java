package com.allatori.lib.bcel.generic;

public class DUP2 extends StackInstruction implements PushInstruction {

  @Override
  public void accept(Visitor visitor) {
    visitor.visitStackProducer(this);
    visitor.visitPushInstruction(this);
    visitor.visitStackInstruction(this);
    visitor.visitDUP2(this);
  }

  public DUP2() {
    super((short) 92);
  }
}
