package com.allatori.lib.bcel.generic;

public class IF_ICMPLT extends IfInstruction {

  @Override
  public IfInstruction negate() {
    return new IF_ICMPGE(this.target);
  }

  public IF_ICMPLT() {}

  @Override
  public void accept(Visitor visitor) {
    visitor.visitStackConsumer(this);
    visitor.visitBranchInstruction(this);
    visitor.visitIfInstruction(this);
    visitor.visitIF_ICMPLT(this);
  }

  public IF_ICMPLT(InstructionHandle instructionHandle) {
    super((short) 161, instructionHandle);
  }
}
