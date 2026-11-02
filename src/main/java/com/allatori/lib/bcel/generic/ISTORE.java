package com.allatori.lib.bcel.generic;

public class ISTORE extends StoreInstruction {

  @Override
  public void accept(Visitor visitor) {
    super.accept(visitor);
    visitor.visitISTORE(this);
  }

  public ISTORE() {
    super((short) 54, (short) 59);
  }

  public ISTORE(int value) {
    super((short) 54, (short) 59, value);
  }
}
