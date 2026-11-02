package com.allatori.lib.bcel.generic;

public class IMPDEP2 extends Instruction {

  public IMPDEP2() {
    super((short) 255, (short) 1);
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visitIMPDEP2(this);
  }
}
