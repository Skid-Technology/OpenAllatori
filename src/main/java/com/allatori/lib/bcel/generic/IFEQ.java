package com.allatori.lib.bcel.generic;

public class IFEQ extends IfInstruction {

  public IFEQ(InstructionHandle instructionHandle) {
    super((short) 153, instructionHandle);
  }

  @Override
  public IfInstruction negate() {
    return new IFNE(this.target);
  }

  public IFEQ() {}

  @Override
  public void accept(Visitor visitor) {
    visitor.visitStackConsumer(this);
    visitor.visitBranchInstruction(this);
    visitor.visitIfInstruction(this);
    visitor.visitIFEQ(this);
  }
}
