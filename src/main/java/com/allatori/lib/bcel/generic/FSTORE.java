package com.allatori.lib.bcel.generic;

public class FSTORE extends StoreInstruction {

  public FSTORE() {
    super((short) 56, (short) 67);
  }

  @Override
  public void accept(Visitor visitor) {
    super.accept(visitor);
    visitor.visitFSTORE(this);
  }

  public FSTORE(int value) {
    super((short) 56, (short) 67, value);
  }
}
