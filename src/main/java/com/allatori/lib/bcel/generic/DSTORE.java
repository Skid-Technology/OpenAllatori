package com.allatori.lib.bcel.generic;

public class DSTORE extends StoreInstruction {

  public DSTORE() {
    super((short) 57, (short) 71);
  }

  public DSTORE(int value) {
    super((short) 57, (short) 71, value);
  }

  @Override
  public void accept(Visitor visitor) {
    super.accept(visitor);
    visitor.visitDSTORE(this);
  }
}
