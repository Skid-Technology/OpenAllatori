package com.allatori.lib.bcel.generic;

public class IF_ACMPEQ extends IfInstruction {

  @Override
  public IfInstruction negate() {
    return new IF_ACMPNE(this.target);
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visitStackConsumer(this);
    visitor.visitBranchInstruction(this);
    visitor.visitIfInstruction(this);
    visitor.visitIF_ACMPEQ(this);
  }

  public IF_ACMPEQ(InstructionHandle instructionHandle) {
    super((short) 165, instructionHandle);
  }

  public IF_ACMPEQ() {}
}
