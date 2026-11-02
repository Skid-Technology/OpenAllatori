package com.allatori.lib.bcel.generic;

public class IFLT extends IfInstruction {

  @Override
  public void accept(Visitor visitor) {
    visitor.visitStackConsumer(this);
    visitor.visitBranchInstruction(this);
    visitor.visitIfInstruction(this);
    visitor.visitIFLT(this);
  }

  public IFLT() {}

  @Override
  public IfInstruction negate() {
    return new IFGE(this.target);
  }

  public IFLT(InstructionHandle instructionHandle) {
    super((short) 155, instructionHandle);
  }
}
