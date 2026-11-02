package com.allatori.lib.bcel.generic;

public abstract class IfInstruction extends BranchInstruction implements StackConsumer {

  public IfInstruction() {}

  public abstract IfInstruction negate();

  public IfInstruction(short value, InstructionHandle instructionHandle) {
    super(value, instructionHandle);
  }
}
