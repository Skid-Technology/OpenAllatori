package com.allatori.lib.bcel.generic;

public class IF_ACMPNE extends IfInstruction {

  @Override
  public void accept(Visitor visitor) {
    visitor.visitStackConsumer(this);
    visitor.visitBranchInstruction(this);
    visitor.visitIfInstruction(this);
    visitor.visitIF_ACMPNE(this);
  }

  public IF_ACMPNE(InstructionHandle instructionHandle) {
    super((short) 166, instructionHandle);
  }

  @Override
  public IfInstruction negate() {
    return new IF_ACMPEQ(this.target);
  }

  public IF_ACMPNE() {}
}
