package com.allatori.lib.bcel.generic;

public class POP2 extends StackInstruction implements PopInstruction {

  @Override
  public void accept(Visitor visitor) {
    visitor.visitStackConsumer(this);
    visitor.visitPopInstruction(this);
    visitor.visitStackInstruction(this);
    visitor.visitPOP2(this);
  }

  public POP2() {
    super((short) 88);
  }
}
