package com.allatori.lib.bcel.generic;

public class IF_ICMPGT extends IfInstruction {

  @Override
  public void accept(Visitor visitor) {
    visitor.visitStackConsumer(this);
    visitor.visitBranchInstruction(this);
    visitor.visitIfInstruction(this);
    visitor.visitIF_ICMPGT(this);
  }

  public IF_ICMPGT() {}

  @Override
  public IfInstruction negate() {
    return new IF_ICMPLE(this.target);
  }

  public IF_ICMPGT(InstructionHandle instructionHandle) {
    super((short) 163, instructionHandle);
  }
}
