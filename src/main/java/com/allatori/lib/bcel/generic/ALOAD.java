package com.allatori.lib.bcel.generic;

public class ALOAD extends LoadInstruction {

  public ALOAD(int value) {
    super((short) 25, (short) 42, value);
  }

  @Override
  public void accept(Visitor visitor) {
    super.accept(visitor);
    visitor.visitALOAD(this);
  }

  public ALOAD() {
    super((short) 25, (short) 42);
  }
}
