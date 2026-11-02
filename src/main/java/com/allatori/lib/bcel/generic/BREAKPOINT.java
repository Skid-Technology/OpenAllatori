package com.allatori.lib.bcel.generic;

public class BREAKPOINT extends Instruction {

  @Override
  public void accept(Visitor visitor) {
    visitor.visitBREAKPOINT(this);
  }

  public BREAKPOINT() {
    super((short) 202, (short) 1);
  }
}
