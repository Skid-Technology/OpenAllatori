package com.allatori.lib.bcel.generic;

public class NOP extends Instruction {

  @Override
  public void accept(Visitor visitor) {
    visitor.visitNOP(this);
  }

  public NOP() {
    super((short) 0, (short) 1);
  }
}
