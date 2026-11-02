package com.allatori.lib.bcel.generic;

public class DUP2_X1 extends StackInstruction {

  @Override
  public void accept(Visitor visitor) {
    visitor.visitStackInstruction(this);
    visitor.visitDUP2_X1(this);
  }

  public DUP2_X1() {
    super((short) 93);
  }
}
