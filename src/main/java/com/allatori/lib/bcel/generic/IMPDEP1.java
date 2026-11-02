package com.allatori.lib.bcel.generic;

public class IMPDEP1 extends Instruction {

  @Override
  public void accept(Visitor visitor) {
    visitor.visitIMPDEP1(this);
  }

  public IMPDEP1() {
    super((short) 254, (short) 1);
  }
}
