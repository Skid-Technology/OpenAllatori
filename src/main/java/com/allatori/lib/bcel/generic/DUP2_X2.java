package com.allatori.lib.bcel.generic;

public class DUP2_X2 extends StackInstruction {

  @Override
  public void accept(Visitor visitor) {
    visitor.visitStackInstruction(this);
    visitor.visitDUP2_X2(this);
  }

  public DUP2_X2() {
    super((short) 94);
  }
}
