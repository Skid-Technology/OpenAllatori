package com.allatori.lib.bcel.verifier.structurals;

import com.allatori.lib.bcel.generic.InstructionHandle;
import com.allatori.lib.bcel.generic.MethodGen;
import com.allatori.lib.bcel.verifier.exc.AssertionViolatedException;
import java.util.HashMap;
import java.util.Map;

public class ControlFlowGraph {

  public final Subroutines subroutines;

  public Map entries;

  public final ExceptionHandlers exceptionhandlers;

  public ControlFlowGraph(MethodGen methodGen) {
    super();
    this.entries = new HashMap();
    this.subroutines = new Subroutines(methodGen);
    this.exceptionhandlers = new ExceptionHandlers(methodGen);
    InstructionHandle[] instructionHandles = methodGen.getInstructionList().getInstructionHandles();
    int value;
    for (int value2 = value = 0; value2 < instructionHandles.length; value2 = value) {
      this.entries.put(
          instructionHandles[value],
          new ControlFlowGraph$InstructionContextImpl(this, instructionHandles[value++]));
    }
  }

  public boolean isDead(InstructionHandle instructionHandle) {
    return this.subroutines.subroutineOf(instructionHandle) == null;
  }

  public InstructionContext[] getInstructionContexts() {
    InstructionContext[] instructionContexts = new InstructionContext[this.entries.values().size()];
    return (InstructionContext[]) this.entries.values().toArray(instructionContexts);
  }

  public InstructionContext contextOf(InstructionHandle instructionHandle) {
    InstructionContext instructionContext;
    if ((instructionContext = (InstructionContext) this.entries.get(instructionHandle)) == null) {
      throw new AssertionViolatedException(
          "InstructionContext requested for an InstructionHandle that's not known!");
    } else {
      return instructionContext;
    }
  }

  public static Subroutines access$100(ControlFlowGraph controlFlowGraph) {
    return controlFlowGraph.subroutines;
  }

  public static ExceptionHandlers access$000(ControlFlowGraph controlFlowGraph) {
    return controlFlowGraph.exceptionhandlers;
  }

  public InstructionContext[] contextsOf(InstructionHandle[] instructionHandles) {
    InstructionContext[] instructionContexts = new InstructionContext[instructionHandles.length];
    int value;
    for (int value2 = value = 0; value2 < instructionHandles.length; value2 = value) {
      InstructionContext instructionContext = this.contextOf(instructionHandles[value]);
      int value3 = value;
      value++;
      instructionContexts[value3] = instructionContext;
    }
    return instructionContexts;
  }
}
