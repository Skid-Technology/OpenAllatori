package com.allatori.lib.bcel.generic;

public abstract class StackInstruction extends Instruction {

  public Type getType(ConstantPoolGen constantPoolGen) {
    return Type.UNKNOWN;
  }

  public StackInstruction() {}

  public StackInstruction(short value) {
    super(value, (short) 1);
  }
}
