package com.allatori.lib.bcel.generic;

import com.allatori.lib.bcel.classfile.LineNumber;
import java.io.Serializable;

public class LineNumberGen implements InstructionTargeter, Cloneable, Serializable {

  public InstructionHandle ih;

  public int src_line;

  @Override
  public Object clone() {
    try {
      return super.clone();
    } catch (CloneNotSupportedException cloneNotSupportedException) {
      System.err.println(cloneNotSupportedException);
      return null;
    }
  }

  public InstructionHandle getInstruction() {
    return this.ih;
  }

  public LineNumber getLineNumber() {
    return new LineNumber(this.ih.getPosition(), this.src_line);
  }

  public int getSourceLine() {
    return this.src_line;
  }

  @Override
  public void updateTarget(
      InstructionHandle instructionHandle, InstructionHandle instructionHandle2) {
    if (instructionHandle != this.ih) {
      throw new ClassGenException(
          ("Not targeting " + instructionHandle + ", but " + this.ih + "}"));
    }
    this.setInstruction(instructionHandle2);
  }

  public void setInstruction(InstructionHandle instructionHandle) {
    BranchInstruction.notifyTarget(this.ih, instructionHandle, this);
    this.ih = instructionHandle;
  }

  public LineNumberGen(InstructionHandle instructionHandle, int value) {
    super();
    this.setInstruction(instructionHandle);
    this.setSourceLine(value);
  }

  @Override
  public boolean containsTarget(InstructionHandle instructionHandle) {
    return this.ih == instructionHandle;
  }

  public void setSourceLine(int src_line) {
    this.src_line = src_line;
  }
}
