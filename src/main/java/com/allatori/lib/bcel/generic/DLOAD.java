package com.allatori.lib.bcel.generic;

public class DLOAD extends LoadInstruction {

  @Override
  public void accept(Visitor visitor) {
    super.accept(visitor);
    visitor.visitDLOAD(this);
  }

  public DLOAD() {
    super((short) 24, (short) 38);
  }

  public DLOAD(int value) {
    super((short) 24, (short) 38, value);
  }
}
