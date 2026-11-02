package com.allatori.lib.bcel.generic;

import com.allatori.lib.bcel.classfile.CodeException;
import java.io.Serializable;

public final class CodeExceptionGen implements InstructionTargeter, Cloneable, Serializable {

  public InstructionHandle instructionHandle;

  public ObjectType catch_type;

  public InstructionHandle instructionHandle2;

  public InstructionHandle instructionHandle3;

  public ObjectType getCatchType() {
    return this.catch_type;
  }

  public void setCatchType(ObjectType objectType) {
    this.catch_type = objectType;
  }

  public InstructionHandle getInstructionHandle3() {
    return this.instructionHandle3;
  }

  public InstructionHandle getInstructionHandle() {
    return this.instructionHandle;
  }

  public CodeException getCodeException(ConstantPoolGen constantPoolGen) {
    return new CodeException(
        this.instructionHandle2.getPosition(),
        this.instructionHandle.getPosition() + this.instructionHandle.getInstruction().getLength(),
        this.instructionHandle3.getPosition(),
        this.catch_type == null ? 0 : constantPoolGen.addClass(this.catch_type));
  }

  public InstructionHandle getInstructionHandle2() {
    return this.instructionHandle2;
  }

  public void processInstructionHandle(InstructionHandle instructionHandle2) {
    BranchInstruction.notifyTarget(this.instructionHandle, instructionHandle2, this);
    this.instructionHandle = instructionHandle2;
  }

  @Override
  public Object clone() {
    try {
      return super.clone();
    } catch (CloneNotSupportedException cloneNotSupportedException) {
      System.err.println(cloneNotSupportedException);
      return null;
    }
  }

  public void processInstructionHandle2(InstructionHandle instructionHandle) {
    BranchInstruction.notifyTarget(this.instructionHandle2, instructionHandle, this);
    this.instructionHandle2 = instructionHandle;
  }

  public void processInstructionHandle3(InstructionHandle instructionHandle) {
    BranchInstruction.notifyTarget(this.instructionHandle3, instructionHandle, this);
    this.instructionHandle3 = instructionHandle;
  }

  public CodeExceptionGen(
      InstructionHandle instructionHandle,
      InstructionHandle instructionHandle2,
      InstructionHandle instructionHandle3,
      ObjectType objectType) {
    super();
    this.processInstructionHandle2(instructionHandle);
    this.processInstructionHandle(instructionHandle2);
    this.processInstructionHandle3(instructionHandle3);
    this.catch_type = objectType;
  }

  @Override
  public boolean containsTarget(InstructionHandle instructionHandle4) {
    return this.instructionHandle2 == instructionHandle4
        || this.instructionHandle == instructionHandle4
        || this.instructionHandle3 == instructionHandle4;
  }

  @Override
  public void updateTarget(
      InstructionHandle instructionHandle4, InstructionHandle instructionHandle5) {
    byte value = 0;
    if (this.instructionHandle2 == instructionHandle4) {
      value = 1;
      this.processInstructionHandle2(instructionHandle5);
    }
    if (this.instructionHandle == instructionHandle4) {
      value = 1;
      this.processInstructionHandle(instructionHandle5);
    }
    if (this.instructionHandle3 == instructionHandle4) {
      value = 1;
      this.processInstructionHandle3(instructionHandle5);
    }
    if (value == 0) {
      throw new ClassGenException(
          ("Not targeting "
              + instructionHandle4
              + ", but {"
              + this.instructionHandle2
              + ", "
              + this.instructionHandle
              + ", "
              + this.instructionHandle3
              + "}"));
    }
  }
}
