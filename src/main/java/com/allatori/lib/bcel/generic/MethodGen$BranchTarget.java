package com.allatori.lib.bcel.generic;

public final class MethodGen$BranchTarget {

  public InstructionHandle target;

  public int stackDepth;

  public MethodGen$BranchTarget(InstructionHandle instructionHandle, int stackDepth) {
    super();
    this.target = instructionHandle;
    this.stackDepth = stackDepth;
  }
}
