package com.allatori.lib.bcel.generic;

public interface InstructionTargeter {

  void updateTarget(InstructionHandle instructionHandle, InstructionHandle instructionHandle2)
      throws ClassGenException;

  boolean containsTarget(InstructionHandle instructionHandle);
}
