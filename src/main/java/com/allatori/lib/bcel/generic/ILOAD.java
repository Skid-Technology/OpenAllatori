package com.allatori.lib.bcel.generic;

public class ILOAD extends LoadInstruction {

  public ILOAD(int value) {
    super((short) 21, (short) 26, value);
  }

  @Override
  public void accept(Visitor visitor) {
    super.accept(visitor);
    visitor.visitILOAD(this);
  }

  public ILOAD() {
    super((short) 21, (short) 26);
  }
}
