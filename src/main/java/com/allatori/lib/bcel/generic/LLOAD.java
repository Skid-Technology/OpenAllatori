package com.allatori.lib.bcel.generic;

public class LLOAD extends LoadInstruction {

  public LLOAD() {
    super((short) 22, (short) 30);
  }

  @Override
  public void accept(Visitor visitor) {
    super.accept(visitor);
    visitor.visitLLOAD(this);
  }

  public LLOAD(int value) {
    super((short) 22, (short) 30, value);
  }
}
