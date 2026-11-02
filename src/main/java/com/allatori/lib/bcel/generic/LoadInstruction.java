package com.allatori.lib.bcel.generic;

public abstract class LoadInstruction extends LocalVariableInstruction implements PushInstruction {

  public LoadInstruction(short value, short value2, int value3) {
    super(value, value2, value3);
  }

  public LoadInstruction(short value, short value2) {
    super(value, value2);
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visitStackProducer(this);
    visitor.visitPushInstruction(this);
    visitor.visitTypedInstruction(this);
    visitor.visitLocalVariableInstruction(this);
    visitor.visitLoadInstruction(this);
  }
}
