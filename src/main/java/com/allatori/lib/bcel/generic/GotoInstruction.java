package com.allatori.lib.bcel.generic;

public abstract class GotoInstruction extends BranchInstruction implements UnconditionalBranch {

  public GotoInstruction() {}

  public GotoInstruction(short value, InstructionHandle instructionHandle) {
    super(value, instructionHandle);
  }
}
