package com.allatori.lib.bcel.generic;

public class DUP_X2 extends StackInstruction {

  @Override
  public void accept(Visitor visitor) {
    visitor.visitStackInstruction(this);
    visitor.visitDUP_X2(this);
  }

  public DUP_X2() {
    super((short) 91);
  }
}
