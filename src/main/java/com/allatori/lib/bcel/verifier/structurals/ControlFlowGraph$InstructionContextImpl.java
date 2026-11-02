package com.allatori.lib.bcel.verifier.structurals;

import com.allatori.lib.bcel.generic.ATHROW;
import com.allatori.lib.bcel.generic.BranchInstruction;
import com.allatori.lib.bcel.generic.GotoInstruction;
import com.allatori.lib.bcel.generic.Instruction;
import com.allatori.lib.bcel.generic.InstructionHandle;
import com.allatori.lib.bcel.generic.JsrInstruction;
import com.allatori.lib.bcel.generic.RET;
import com.allatori.lib.bcel.generic.ReturnInstruction;
import com.allatori.lib.bcel.generic.Select;
import com.allatori.lib.bcel.verifier.exc.AssertionViolatedException;
import com.allatori.lib.bcel.verifier.exc.StructuralCodeConstraintException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ControlFlowGraph$InstructionContextImpl implements InstructionContext {

  public final ControlFlowGraph this$0;

  public ArrayList arrayList;

  public Map entries;

  public int TAG;

  public InstructionHandle instruction;

  public Map entries2;

  public String createText() {
    String text = this.toString();
    int value;
    for (int value2 = value = this.arrayList.size() - 1; value2 >= 0; value2 = value) {
      StringBuilder stringBuilder =
          new StringBuilder().append(this.arrayList.get(value)).append("\n");
      String text2 = text;
      value--;
      text = stringBuilder.append(text2).toString();
    }
    return text;
  }

  public boolean mergeInFrames(Frame frame) {
    Frame frame2;
    Frame frame3 = frame2 = (Frame) this.entries2.get(this.lastExecutionJSR());
    OperandStack clone = frame3.getStack().getClone();
    LocalVariables clone2 = frame3.getLocals().getClone();
    try {
      frame2.getStack().merge(frame.getStack());
      frame2.getLocals().merge(frame.getLocals());
    } catch (StructuralCodeConstraintException structuralCodeConstraintException) {
      this.extendMessageWithFlow(structuralCodeConstraintException);
      throw structuralCodeConstraintException;
    }
    return !clone.equals(frame2.getStack()) || !clone2.equals(frame2.getLocals());
  }

  @Override
  public void setTag(int TAG) {
    this.TAG = TAG;
  }

  public void extendMessageWithFlow(
      StructuralCodeConstraintException structuralCodeConstraintException) {
    structuralCodeConstraintException.extendMessage(
        "", "" + ((String) "Execution flow:\n") + this.createText());
  }

  public InstructionHandle[] _getSuccessors() {
    InstructionHandle[] instructionHandles = new InstructionHandle[0];
    InstructionHandle[] instructionHandles2 = new InstructionHandle[1];
    InstructionHandle[] instructionHandles3 = new InstructionHandle[2];
    Instruction instruction;
    if (!((instruction = this.getInstruction().getInstruction()) instanceof RET)) {
      if (instruction instanceof ReturnInstruction) {
        return instructionHandles;
      }
      if (instruction instanceof ATHROW) {
        return instructionHandles;
      }
      if (instruction instanceof JsrInstruction) {
        instructionHandles2[0] = ((JsrInstruction) instruction).getTarget();
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
          instructionHandles3[0] = this.getInstruction().getInstructionHandle2();
          instructionHandles3[1] = ((BranchInstruction) instruction).getTarget();
          return instructionHandles3;
        }
      } else {
        instructionHandles2[0] = this.getInstruction().getInstructionHandle2();
        return instructionHandles2;
      }
    } else {
      Subroutine subroutine;
      if ((subroutine =
              ControlFlowGraph.access$100(this.this$0).subroutineOf(this.getInstruction()))
          == null) {
        throw new AssertionViolatedException("Asking for successors of a RET in dead code?!");
      }
      InstructionHandle[] instructionHandles6;
      InstructionHandle[] instructionHandles7 =
          new InstructionHandle[(instructionHandles6 = subroutine.getInstructionHandles()).length];
      int value;
      for (int value2 = value = 0; value2 < instructionHandles6.length; value2 = value) {
        InstructionHandle instructionHandle2 = instructionHandles6[value].getInstructionHandle2();
        int value3 = value;
        value++;
        instructionHandles7[value3] = instructionHandle2;
      }
      return instructionHandles7;
    }
  }

  @Override
  public InstructionHandle getInstruction() {
    return this.instruction;
  }

  @Override
  public int getTag() {
    return this.TAG;
  }

  public ControlFlowGraph$InstructionContextImpl lastExecutionJSR() {
    int size2 = this.arrayList.size();
    int value = 0;
    int value2;
    for (int value3 = value2 = size2 - 1; value3 >= 0; value3 = --value2) {
      Instruction instruction;
      ControlFlowGraph$InstructionContextImpl controlFlowGraph$InstructionContextImpl;
      if ((instruction =
              (controlFlowGraph$InstructionContextImpl =
                      (ControlFlowGraph$InstructionContextImpl) this.arrayList.get(value2))
                  .getInstruction()
                  .getInstruction())
          instanceof RET) {
        value++;
      }
      if (instruction instanceof JsrInstruction) {
        if (--value == -1) {
          return controlFlowGraph$InstructionContextImpl;
        }
      }
    }
    return null;
  }

  @Override
  public InstructionContext[] getSuccessors() {
    return this.this$0.contextsOf(this._getSuccessors());
  }

  @Override
  public ExceptionHandler[] getExceptionHandlers() {
    return ControlFlowGraph.access$000(this.this$0).getExceptionHandlers(this.getInstruction());
  }

  @Override
  public Frame getOutFrame(ArrayList arrayList) {
    this.arrayList = arrayList;
    ControlFlowGraph$InstructionContextImpl controlFlowGraph$InstructionContextImpl =
        this.lastExecutionJSR();
    Frame frame;
    if ((frame = (Frame) this.entries.get(controlFlowGraph$InstructionContextImpl)) == null) {
      throw new AssertionViolatedException(
          ("outFrame not set! This:\n"
              + this
              + "\nExecutionChain: "
              + this.createText()
              + "\nOutFrames: '"
              + this.entries
              + "'."));
    } else {
      return frame.getClone();
    }
  }

  @Override
  public Frame getInFrame() {
    ControlFlowGraph$InstructionContextImpl controlFlowGraph$InstructionContextImpl =
        this.lastExecutionJSR();
    Frame frame;
    if ((frame = (Frame) this.entries2.get(controlFlowGraph$InstructionContextImpl)) == null) {
      throw new AssertionViolatedException(
          ("inFrame not set! This:\n" + this + "\nInFrames: '" + this.entries2 + "'."));
    } else {
      return frame.getClone();
    }
  }

  @Override
  public boolean execute(
      Frame frame,
      ArrayList values,
      InstConstraintVisitor instConstraintVisitor,
      ExecutionVisitor executionVisitor) {
    this.arrayList = (ArrayList) values.clone();
    if (this.lastExecutionJSR() == null
        && ControlFlowGraph.access$100(this.this$0).subroutineOf(this.getInstruction())
            != ControlFlowGraph.access$100(this.this$0).getTopLevel()) {
      throw new AssertionViolatedException(
          ("Huh?! Am I '" + this + "' part of a subroutine or not?"));
    }
    if (this.lastExecutionJSR() != null
        && ControlFlowGraph.access$100(this.this$0).subroutineOf(this.getInstruction())
            == ControlFlowGraph.access$100(this.this$0).getTopLevel()) {
      throw new AssertionViolatedException(
          ("Huh?! Am I '" + this + "' part of a subroutine or not?"));
    }
    Frame frame2;
    Frame frame3;
    if ((frame2 = (Frame) this.entries2.get(this.lastExecutionJSR())) == null) {
      this.entries2.put(this.lastExecutionJSR(), frame);
      frame3 = frame;
    } else {
      if (frame2.equals(frame)) {
        return false;
      }
      if (!this.mergeInFrames(frame)) {
        return false;
      }
      frame3 = frame2;
    }
    Frame clone2 = frame3.getClone();
    try {
      instConstraintVisitor.setFrame(clone2);
      this.getInstruction().accept(instConstraintVisitor);
    } catch (StructuralCodeConstraintException structuralCodeConstraintException) {
      structuralCodeConstraintException.extendMessage(
          "", "\nInstructionHandle: " + this.getInstruction() + "\n");
      structuralCodeConstraintException.extendMessage("", "\nExecution Frame:\n" + clone2);
      this.extendMessageWithFlow(structuralCodeConstraintException);
      throw structuralCodeConstraintException;
    }
    executionVisitor.setFrame(clone2);
    this.getInstruction().accept(executionVisitor);
    this.entries.put(this.lastExecutionJSR(), clone2);
    return true;
  }

  public ControlFlowGraph$InstructionContextImpl(
      ControlFlowGraph controlFlowGraph, InstructionHandle instructionHandle) {
    super();
    this.this$0 = controlFlowGraph;
    this.arrayList = null;
    if (instructionHandle == null) {
      throw new AssertionViolatedException("Cannot instantiate InstructionContextImpl from NULL.");
    }
    this.instruction = instructionHandle;
    this.entries2 = new HashMap();
    this.entries = new HashMap();
  }
}
