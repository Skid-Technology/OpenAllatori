package com.allatori.lib.bcel.generic;

public class FLOAD extends LoadInstruction {

  @Override
  public void accept(Visitor visitor) {
    super.accept(visitor);
    visitor.visitFLOAD(this);
  }

  public FLOAD() {
    super((short) 23, (short) 34);
  }

  public FLOAD(int value) {
    super((short) 23, (short) 34, value);
  }
}
