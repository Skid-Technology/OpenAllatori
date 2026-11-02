package com.allatori.lib.bcel.generic;

public abstract class StoreInstruction extends LocalVariableInstruction implements PopInstruction {

  public StoreInstruction(short value, short value2, int value3) {
    super(value, value2, value3);
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visitStackConsumer(this);
    visitor.visitPopInstruction(this);
    visitor.visitTypedInstruction(this);
    visitor.visitLocalVariableInstruction(this);
    visitor.visitStoreInstruction(this);
  }

  public StoreInstruction(short value, short value2) {
    super(value, value2);
  }
}
