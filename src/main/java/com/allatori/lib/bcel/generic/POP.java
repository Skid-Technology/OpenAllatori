package com.allatori.lib.bcel.generic;

public class POP extends StackInstruction implements PopInstruction {

  @Override
  public void accept(Visitor visitor) {
    visitor.visitStackConsumer(this);
    visitor.visitPopInstruction(this);
    visitor.visitStackInstruction(this);
    visitor.visitPOP(this);
  }

  public POP() {
    super((short) 87);
  }
}
