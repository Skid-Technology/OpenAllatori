package com.allatori.lib.bcel.generic;

public class DUP_X1 extends StackInstruction {

  @Override
  public void accept(Visitor visitor) {
    visitor.visitStackInstruction(this);
    visitor.visitDUP_X1(this);
  }

  public DUP_X1() {
    super((short) 90);
  }
}
