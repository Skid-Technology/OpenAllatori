package com.allatori.lib.bcel.verifier.structurals;

import com.allatori.lib.bcel.generic.InstructionHandle;

public interface Subroutine {

  int[] getCounts();

  boolean contains(InstructionHandle instructionHandle);

  InstructionHandle getLeavingRET();

  Subroutine[] subSubs();

  int[] getCounts2();

  InstructionHandle[] getInstructionHandles();

  InstructionHandle[] getInstructionHandles2();
}
