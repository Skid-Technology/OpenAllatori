package com.allatori.lib.bcel.generic;

public class LSTORE extends StoreInstruction {

  @Override
  public void accept(Visitor visitor) {
    super.accept(visitor);
    visitor.visitLSTORE(this);
  }

  public LSTORE(int value) {
    super((short) 55, (short) 63, value);
  }

  public LSTORE() {
    super((short) 55, (short) 63);
  }
}
