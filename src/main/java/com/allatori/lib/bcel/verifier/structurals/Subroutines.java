package com.allatori.lib.bcel.verifier.structurals;

import com.allatori.lib.bcel.generic.ASTORE;
import com.allatori.lib.bcel.generic.ATHROW;
import com.allatori.lib.bcel.generic.BranchInstruction;
import com.allatori.lib.bcel.generic.CodeExceptionGen;
import com.allatori.lib.bcel.generic.GotoInstruction;
import com.allatori.lib.bcel.generic.Instruction;
import com.allatori.lib.bcel.generic.InstructionHandle;
import com.allatori.lib.bcel.generic.JsrInstruction;
import com.allatori.lib.bcel.generic.MethodGen;
import com.allatori.lib.bcel.generic.RET;
import com.allatori.lib.bcel.generic.ReturnInstruction;
import com.allatori.lib.bcel.generic.Select;
import com.allatori.lib.bcel.verifier.exc.AssertionViolatedException;
import com.allatori.lib.bcel.verifier.exc.StructuralCodeConstraintException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Subroutines {

  public Map entries;

  public final Subroutine TOPLEVEL;

  public static final Integer INTEGER3 = new Integer(0);

  public static final Integer INTEGER2 = new Integer(1);

  public static final Integer INTEGER = new Integer(2);

  public Subroutine subroutineOf(InstructionHandle instructionHandle) {
    Iterator iterator2 = this.entries.values().iterator();
    while (iterator2.hasNext()) {
      Subroutine subroutine;
      if ((subroutine = (Subroutine) iterator2.next()).contains(instructionHandle)) {
        return subroutine;
      }
    }
    System.err.println("DEBUG: Please verify '" + instructionHandle + "' lies in dead code.");
    return null;
  }

  public static InstructionHandle[] getSuccessors(InstructionHandle instructionHandle) {
    InstructionHandle[] instructionHandles = new InstructionHandle[0];
    InstructionHandle[] instructionHandles2 = new InstructionHandle[1];
    InstructionHandle[] instructionHandles3 = new InstructionHandle[2];
    Instruction instruction;
    if ((instruction = instructionHandle.getInstruction()) instanceof RET) {
      return instructionHandles;
    }
    if (instruction instanceof ReturnInstruction) {
      return instructionHandles;
    }
    if (instruction instanceof ATHROW) {
      return instructionHandles;
    }
    if (instruction instanceof JsrInstruction) {
      instructionHandles2[0] = instructionHandle.getInstructionHandle2();
      return instructionHandles2;
    }
    if (instruction instanceof GotoInstruction) {
      instructionHandles2[0] = ((GotoInstruction) instruction).getTarget();
      return instructionHandles2;
    }
    if (instruction instanceof BranchInstruction) {
      if (instruction instanceof Select) {
        InstructionHandle[] instructionHandles4;
        InstructionHandle[] instructionHandles5;
        (instructionHandles5 =
                    new InstructionHandle
                        [(instructionHandles4 = ((Select) instruction).getTargets()).length + 1])
                [0] =
            ((Select) instruction).getTarget();
        System.arraycopy(
            instructionHandles4, 0, instructionHandles5, 1, instructionHandles4.length);
        return instructionHandles5;
      } else {
        instructionHandles3[0] = instructionHandle.getInstructionHandle2();
        instructionHandles3[1] = ((BranchInstruction) instruction).getTarget();
        return instructionHandles3;
      }
    } else {
      instructionHandles2[0] = instructionHandle.getInstructionHandle2();
      return instructionHandles2;
    }
  }

  public Subroutines(MethodGen methodGen) {
    super();
    this.entries = new HashMap();
    InstructionHandle[] instructionHandles = methodGen.getInstructionList().getInstructionHandles();
    CodeExceptionGen[] exceptionHandlers = methodGen.getExceptionHandlers();
    this.TOPLEVEL = new Subroutines$SubroutineImpl(this);
    HashSet values2 = new HashSet();
    int value;
    for (int value2 = value = 0; value2 < instructionHandles.length; value2 = ++value) {
      Instruction instruction;
      if ((instruction = instructionHandles[value].getInstruction()) instanceof JsrInstruction) {
        values2.add(((JsrInstruction) instruction).getTarget());
      }
    }
    Iterator iterator2;
    Iterator iterator3 = iterator2 = values2.iterator();
    while (iterator3.hasNext()) {
      Subroutines$SubroutineImpl subroutines$SubroutineImpl = new Subroutines$SubroutineImpl(this);
      InstructionHandle instructionHandle = (InstructionHandle) iterator2.next();
      subroutines$SubroutineImpl.setLocalVariable(
          ((ASTORE) instructionHandle.getInstruction()).getIndex());
      iterator3 = iterator2;
      this.entries.put(instructionHandle, subroutines$SubroutineImpl);
    }
    this.entries.put(instructionHandles[0], this.TOPLEVEL);
    values2.add(instructionHandles[0]);
    int value3 = 0;
    for (int value4 = value3; value4 < instructionHandles.length; value4 = ++value3) {
      Instruction instruction2;
      if ((instruction2 = instructionHandles[value3].getInstruction()) instanceof JsrInstruction) {
        InstructionHandle target = ((JsrInstruction) instruction2).getTarget();
        ((Subroutines$SubroutineImpl) this.getSubroutine(target))
            .addEnteringJsrInstruction(instructionHandles[value3]);
      }
    }
    HashSet values3 = new HashSet();
    HashMap entries2 = new HashMap();
    for (InstructionHandle instructionHandle2 :
        ((Iterable<InstructionHandle>) ((Iterable<?>) values2))) {
      int value5;
      for (int value6 = value5 = 0; value6 < instructionHandles.length; value6 = value5) {
        int value7 = value5;
        value5++;
        entries2.put(instructionHandles[value7], INTEGER3);
      }
      entries2.put(instructionHandle2, INTEGER2);
      ArrayList values4;
      (values4 = new ArrayList()).add(instructionHandle2);
      int value8;
      if (instructionHandle2 == instructionHandles[0]) {
        for (int value9 = value8 = 0; value9 < exceptionHandlers.length; value9 = value8) {
          entries2.put(exceptionHandlers[value8].getInstructionHandle3(), INTEGER2);
          int value10 = value8;
          ArrayList values5 = values4;
          value8++;
          values5.add(exceptionHandlers[value10].getInstructionHandle3());
        }
      }
      for (ArrayList values6 = values4; values6.size() != 0; values6 = values4) {
        InstructionHandle instructionHandle3;
        InstructionHandle[] successors =
            getSuccessors(instructionHandle3 = (InstructionHandle) values4.remove(0));
        int value11;
        for (int value12 = value11 = 0; value12 < successors.length; value12 = ++value11) {
          if ((Integer) entries2.get(successors[value11]) == INTEGER3) {
            entries2.put(successors[value11], INTEGER2);
            values4.add(successors[value11]);
          }
        }
        entries2.put(instructionHandle3, INTEGER);
      }
      int value13;
      for (int value14 = value13 = 0; value14 < instructionHandles.length; value14 = ++value13) {
        if (entries2.get(instructionHandles[value13]) == INTEGER) {
          ((Subroutines$SubroutineImpl)
                  (instructionHandle2 == instructionHandles[0]
                      ? this.getTopLevel()
                      : this.getSubroutine(instructionHandle2)))
              .addInstruction(instructionHandles[value13]);
          if (values3.contains(instructionHandles[value13])) {
            throw new StructuralCodeConstraintException(
                ("Instruction '"
                    + instructionHandles[value13]
                    + "' is part of more than one subroutine (or of the top level and a"
                    + " subroutine)."));
          }
          values3.add(instructionHandles[value13]);
        }
      }
      if (instructionHandle2 != instructionHandles[0]) {
        ((Subroutines$SubroutineImpl) this.getSubroutine(instructionHandle2)).setLeavingRET();
      }
    }
    InstructionHandle instructionHandle4;
    int value15;
    for (int value16 = value15 = 0; value16 < exceptionHandlers.length; value16 = ++value15) {
      for (InstructionHandle instructionHandle5 =
              instructionHandle4 = exceptionHandlers[value15].getInstructionHandle2();
          instructionHandle5
              != exceptionHandlers[value15].getInstructionHandle().getInstructionHandle2();
          instructionHandle5 = instructionHandle4 = instructionHandle4.getInstructionHandle2()) {
        Iterator iterator4 = this.entries.values().iterator();
        while (iterator4.hasNext()) {
          Subroutine subroutine;
          if ((subroutine = (Subroutine) iterator4.next())
                  != this.entries.get(instructionHandles[0])
              && subroutine.contains(instructionHandle4)) {
            throw new StructuralCodeConstraintException(
                ("Subroutine instruction '"
                    + instructionHandle4
                    + "' is protected by an exception handler, '"
                    + exceptionHandlers[value15]
                    + "'. This is forbidden by the JustIce verifier due to its clear definition of"
                    + " subroutines."));
          }
        }
      }
    }
    this.noRecursiveCalls(this.getTopLevel(), new HashSet());
  }

  public Subroutine getSubroutine(InstructionHandle instructionHandle) {
    Subroutine subroutine;
    if ((subroutine = (Subroutine) this.entries.get(instructionHandle)) == null) {
      throw new AssertionViolatedException(
          "Subroutine requested for an InstructionHandle that is not a leader of a subroutine.");
    } else if (subroutine == this.TOPLEVEL) {
      throw new AssertionViolatedException(
          "TOPLEVEL special subroutine requested; use getTopLevel().");
    } else {
      return subroutine;
    }
  }

  public void noRecursiveCalls(Subroutine subroutine, Set values) {
    Subroutine[] subroutines = subroutine.subSubs();
    int value;
    for (int value2 = value = 0; value2 < subroutines.length; value2 = value) {
      int index = ((RET) subroutines[value].getLeavingRET().getInstruction()).getIndex();
      if (!values.add(new Integer(index))) {
        Subroutines$SubroutineImpl subroutines$SubroutineImpl =
            (Subroutines$SubroutineImpl) subroutines[value];
        throw new StructuralCodeConstraintException(
            ("Subroutine with local variable '"
                + Subroutines$SubroutineImpl.access$000(subroutines$SubroutineImpl)
                + "', JSRs '"
                + Subroutines$SubroutineImpl.access$100(subroutines$SubroutineImpl)
                + "', RET '"
                + Subroutines$SubroutineImpl.access$200(subroutines$SubroutineImpl)
                + "' is called by a subroutine which uses the same local variable index as itself;"
                + " maybe even a recursive call? JustIce's clean definition of a subroutine forbids"
                + " both."));
      }
      this.noRecursiveCalls(subroutines[value], values);
      value++;
      values.remove(new Integer(index));
    }
  }

  public Subroutine getTopLevel() {
    return this.TOPLEVEL;
  }
}
