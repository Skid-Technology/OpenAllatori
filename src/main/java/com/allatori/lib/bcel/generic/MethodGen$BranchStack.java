package com.allatori.lib.bcel.generic;

import java.util.Hashtable;
import java.util.Stack;

public final class MethodGen$BranchStack {

  public Stack branchTargets = new Stack();

  public Hashtable visitedTargets = new Hashtable();

  public final MethodGen$BranchTarget visit(InstructionHandle instructionHandle, int value) {
    MethodGen$BranchTarget methodGen$BranchTarget =
        new MethodGen$BranchTarget(instructionHandle, value);
    this.visitedTargets.put(instructionHandle, methodGen$BranchTarget);
    return methodGen$BranchTarget;
  }

  public MethodGen$BranchTarget pop() {
    return !this.branchTargets.empty() ? (MethodGen$BranchTarget) this.branchTargets.pop() : null;
  }

  public final boolean visited(InstructionHandle instructionHandle) {
    return this.visitedTargets.get(instructionHandle) != null;
  }

  public void push(InstructionHandle instructionHandle, int value) {
    if (!this.visited(instructionHandle)) {
      this.branchTargets.push(this.visit(instructionHandle, value));
    }
  }
}
