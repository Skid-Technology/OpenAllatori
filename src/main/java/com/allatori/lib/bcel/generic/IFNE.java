package com.allatori.lib.bcel.generic;

public class IFNE extends IfInstruction {

  @Override
  public IfInstruction negate() {
    return new IFEQ(this.target);
  }

  public IFNE() {}

  @Override
  public void accept(Visitor visitor) {
    visitor.visitStackConsumer(this);
    visitor.visitBranchInstruction(this);
    visitor.visitIfInstruction(this);
    visitor.visitIFNE(this);
  }

  public IFNE(InstructionHandle instructionHandle) {
    super((short) 154, instructionHandle);
  }
}
