package com.allatori.lib.bcel.generic;

public class ASTORE extends StoreInstruction {

  @Override
  public void accept(Visitor visitor) {
    super.accept(visitor);
    visitor.visitASTORE(this);
  }

  public ASTORE(int value) {
    super((short) 58, (short) 75, value);
  }

  public ASTORE() {
    super((short) 58, (short) 75);
  }
}
