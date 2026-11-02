package com.allatori.lib.bcel.generic;

public final class TargetLostException extends Exception {

  public InstructionHandle[] targets;

  public TargetLostException(InstructionHandle[] instructionHandles, String text) {
    super(text);
    this.targets = instructionHandles;
  }

  public InstructionHandle[] getTargets() {
    return this.targets;
  }
}
