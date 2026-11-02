package com.allatori.lib.bcel.verifier.structurals;

import com.allatori.lib.bcel.generic.InstructionHandle;
import java.util.ArrayList;

public interface InstructionContext {

  Frame getOutFrame(ArrayList values);

  InstructionContext[] getSuccessors();

  InstructionHandle getInstruction();

  int getTag();

  ExceptionHandler[] getExceptionHandlers();

  boolean execute(
      Frame frame,
      ArrayList values,
      InstConstraintVisitor instConstraintVisitor,
      ExecutionVisitor executionVisitor);

  void setTag(int value);

  Frame getInFrame();
}
