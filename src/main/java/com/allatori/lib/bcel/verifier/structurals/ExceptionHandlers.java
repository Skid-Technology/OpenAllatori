package com.allatori.lib.bcel.verifier.structurals;

import com.allatori.lib.bcel.generic.CodeExceptionGen;
import com.allatori.lib.bcel.generic.InstructionHandle;
import com.allatori.lib.bcel.generic.MethodGen;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ExceptionHandlers {

  public Map exceptionhandlers;

  public ExceptionHandlers(MethodGen methodGen) {
    super();
    this.exceptionhandlers = new HashMap();
    CodeExceptionGen[] exceptionHandlers = methodGen.getExceptionHandlers();
    int value;
    for (int value2 = value = 0; value2 < exceptionHandlers.length; value2 = ++value) {
      ExceptionHandler exceptionHandler =
          new ExceptionHandler(
              exceptionHandlers[value].getCatchType(),
              exceptionHandlers[value].getInstructionHandle3());
      InstructionHandle instructionHandle;
      for (InstructionHandle instructionHandle2 =
              instructionHandle = exceptionHandlers[value].getInstructionHandle2();
          instructionHandle2
              != exceptionHandlers[value].getInstructionHandle().getInstructionHandle2();
          instructionHandle2 = instructionHandle) {
        Set values;
        if ((values = (Set) this.exceptionhandlers.get(instructionHandle)) == null) {
          values = new HashSet();
          this.exceptionhandlers.put(instructionHandle, values);
        }
        values.add(exceptionHandler);
        instructionHandle = instructionHandle.getInstructionHandle2();
      }
    }
  }

  public ExceptionHandler[] getExceptionHandlers(InstructionHandle instructionHandle) {
    Set values;
    if ((values = (Set) this.exceptionhandlers.get(instructionHandle)) == null) {
      return new ExceptionHandler[0];
    }
    ExceptionHandler[] exceptionHandlers = new ExceptionHandler[values.size()];
    return (ExceptionHandler[]) values.toArray(exceptionHandlers);
  }
}
