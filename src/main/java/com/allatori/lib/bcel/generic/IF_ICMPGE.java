package com.allatori.lib.bcel.generic;

public class IF_ICMPGE extends IfInstruction {

  public IF_ICMPGE() {}

  @Override
  public IfInstruction negate() {
    return new IF_ICMPLT(this.target);
  }

  public IF_ICMPGE(InstructionHandle instructionHandle) {
    super((short) 162, instructionHandle);
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visitStackConsumer(this);
    visitor.visitBranchInstruction(this);
    visitor.visitIfInstruction(this);
    visitor.visitIF_ICMPGE(this);
  }
}
