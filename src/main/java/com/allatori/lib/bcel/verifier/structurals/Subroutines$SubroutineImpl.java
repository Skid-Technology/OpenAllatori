package com.allatori.lib.bcel.verifier.structurals;

import com.allatori.lib.bcel.generic.ASTORE;
import com.allatori.lib.bcel.generic.IndexedInstruction;
import com.allatori.lib.bcel.generic.Instruction;
import com.allatori.lib.bcel.generic.InstructionHandle;
import com.allatori.lib.bcel.generic.JsrInstruction;
import com.allatori.lib.bcel.generic.LocalVariableInstruction;
import com.allatori.lib.bcel.generic.RET;
import com.allatori.lib.bcel.verifier.exc.AssertionViolatedException;
import com.allatori.lib.bcel.verifier.exc.StructuralCodeConstraintException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Subroutines$SubroutineImpl implements Subroutine {

  public int localVariable;

  public Set theJSRs;

  public final Subroutines this$0;

  public Set instructions;

  public static final int UNSET = -1;

  public InstructionHandle theRET;

  public void setLeavingRET() {
    if (this.localVariable == -1) {
      throw new AssertionViolatedException(
          "setLeavingRET() called for top-level 'subroutine' or forgot to set local variable"
              + " first.");
    }
    Iterator iterator2 = this.instructions.iterator();
    InstructionHandle instructionHandle = null;
    while (iterator2.hasNext()) {
      InstructionHandle instructionHandle2;
      if ((instructionHandle2 = (InstructionHandle) iterator2.next()).getInstruction()
          instanceof RET) {
        if (instructionHandle != null) {
          throw new StructuralCodeConstraintException(
              ("Subroutine with more then one RET detected: '"
                  + instructionHandle
                  + "' and '"
                  + instructionHandle2
                  + "'."));
        }
        instructionHandle = instructionHandle2;
      }
    }
    if (instructionHandle == null) {
      throw new StructuralCodeConstraintException("Subroutine without a RET detected.");
    }
    if (((RET) instructionHandle.getInstruction()).getIndex() != this.localVariable) {
      throw new StructuralCodeConstraintException(
          ("Subroutine uses '"
              + instructionHandle
              + "' which does not match the correct local variable '"
              + this.localVariable
              + "'."));
    }
    this.theRET = instructionHandle;
  }

  @Override
  public InstructionHandle[] getInstructionHandles() {
    if (this == this.this$0.TOPLEVEL) {
      throw new AssertionViolatedException(
          "getLeavingRET() called on top level pseudo-subroutine.");
    }
    InstructionHandle[] instructionHandles = new InstructionHandle[this.theJSRs.size()];
    return (InstructionHandle[]) this.theJSRs.toArray(instructionHandles);
  }

  @Override
  public InstructionHandle[] getInstructionHandles2() {
    InstructionHandle[] instructionHandles = new InstructionHandle[this.instructions.size()];
    return (InstructionHandle[]) this.instructions.toArray(instructionHandles);
  }

  public void setLocalVariable(int localVariable) {
    if (this.localVariable != -1) {
      throw new AssertionViolatedException("localVariable set twice.");
    }
    this.localVariable = localVariable;
  }

  public void _getRecursivelyAccessedLocalsIndicesHelper(Set values, Subroutine[] subroutines) {
    int value;
    for (int value2 = value = 0; value2 < subroutines.length; value2 = ++value) {
      int[] counts2 = subroutines[value].getCounts2();
      int value3;
      for (int value4 = value3 = 0; value4 < counts2.length; value4 = value3) {
        values.add(new Integer(counts2[value3++]));
      }
      if (subroutines[value].subSubs().length != 0) {
        this._getRecursivelyAccessedLocalsIndicesHelper(values, subroutines[value].subSubs());
      }
    }
  }

  public static Set access$100(Subroutines$SubroutineImpl subroutines$SubroutineImpl) {
    return subroutines$SubroutineImpl.theJSRs;
  }

  @Override
  public boolean contains(InstructionHandle instructionHandle) {
    return this.instructions.contains(instructionHandle);
  }

  @Override
  public int[] getCounts() {
    HashSet values = new HashSet();
    int[] counts2 = this.getCounts2();
    int value;
    for (int value2 = value = 0; value2 < counts2.length; value2 = value) {
      values.add(new Integer(counts2[value++]));
    }
    this._getRecursivelyAccessedLocalsIndicesHelper(values, this.subSubs());
    int[] values2 = new int[values.size()];
    Iterator iterator2 = values.iterator();
    int value3 = -1;
    for (Iterator iterator3 = iterator2; iterator3.hasNext(); iterator3 = iterator2) {
      values2[++value3] = (Integer) iterator2.next();
    }
    return values2;
  }

  public static InstructionHandle access$200(
      Subroutines$SubroutineImpl subroutines$SubroutineImpl) {
    return subroutines$SubroutineImpl.theRET;
  }

  public static int access$000(Subroutines$SubroutineImpl subroutines$SubroutineImpl) {
    return subroutines$SubroutineImpl.localVariable;
  }

  public Subroutines$SubroutineImpl(Subroutines subroutines) {
    super();
    this.this$0 = subroutines;
    this.localVariable = -1;
    this.instructions = new HashSet();
    this.theJSRs = new HashSet();
  }

  public void addInstruction(InstructionHandle instructionHandle) {
    if (this.theRET != null) {
      throw new AssertionViolatedException(
          "All instructions must have been added before invoking setLeavingRET().");
    }
    this.instructions.add(instructionHandle);
  }

  @Override
  public Subroutine[] subSubs() {
    HashSet values = new HashSet();
    Iterator iterator2 = this.instructions.iterator();
    while (iterator2.hasNext()) {
      Instruction instruction;
      if ((instruction = ((InstructionHandle) iterator2.next()).getInstruction())
          instanceof JsrInstruction) {
        InstructionHandle target = ((JsrInstruction) instruction).getTarget();
        values.add(this.this$0.getSubroutine(target));
      }
    }
    Subroutine[] subroutines = new Subroutine[values.size()];
    return (Subroutine[]) values.toArray(subroutines);
  }

  public void addEnteringJsrInstruction(InstructionHandle instructionHandle) {
    if (instructionHandle == null
        || !(instructionHandle.getInstruction() instanceof JsrInstruction)) {
      throw new AssertionViolatedException("Expecting JsrInstruction InstructionHandle.");
    }
    if (this.localVariable == -1) {
      throw new AssertionViolatedException("Set the localVariable first!");
    }
    if (this.localVariable
        != ((ASTORE)
                ((JsrInstruction) instructionHandle.getInstruction()).getTarget().getInstruction())
            .getIndex()) {
      throw new AssertionViolatedException("Setting a wrong JsrInstruction.");
    }
    this.theJSRs.add(instructionHandle);
  }

  @Override
  public int[] getCounts2() {
    HashSet values = new HashSet();
    if (this.theRET == null && this != this.this$0.TOPLEVEL) {
      throw new AssertionViolatedException(
          "This subroutine object must be built up completely before calculating accessed locals.");
    }
    Iterator iterator2 = this.instructions.iterator();
    while (iterator2.hasNext()) {
      InstructionHandle instructionHandle;
      if ((instructionHandle = (InstructionHandle) iterator2.next()).getInstruction()
              instanceof LocalVariableInstruction
          || instructionHandle.getInstruction() instanceof RET) {
        int index = ((IndexedInstruction) instructionHandle.getInstruction()).getIndex();
        values.add(new Integer(index));
        try {
          if (instructionHandle.getInstruction() instanceof LocalVariableInstruction
              && ((LocalVariableInstruction) instructionHandle.getInstruction())
                      .getType(null)
                      .getSize()
                  == 2) {
            values.add(new Integer(index + 1));
          }
        } catch (RuntimeException runtimeException) {
          throw new AssertionViolatedException(
              "Oops. BCEL did not like NULL as a ConstantPoolGen object.");
        }
      }
    }
    int[] values2 = new int[values.size()];
    Iterator iterator3 = values.iterator();
    int value = -1;
    for (Iterator iterator4 = iterator3; iterator4.hasNext(); iterator4 = iterator3) {
      values2[++value] = (Integer) iterator3.next();
    }
    return values2;
  }

  @Override
  public InstructionHandle getLeavingRET() {
    if (this == this.this$0.TOPLEVEL) {
      throw new AssertionViolatedException(
          "getLeavingRET() called on top level pseudo-subroutine.");
    } else {
      return this.theRET;
    }
  }
}
