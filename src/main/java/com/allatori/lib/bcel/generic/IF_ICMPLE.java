package com.allatori.lib.bcel.generic;

public class IF_ICMPLE extends IfInstruction {

  @Override
  public IfInstruction negate() {
    return new IF_ICMPGT(this.target);
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visitStackConsumer(this);
    visitor.visitBranchInstruction(this);
    visitor.visitIfInstruction(this);
    visitor.visitIF_ICMPLE(this);
  }

  public IF_ICMPLE(InstructionHandle instructionHandle) {
    super((short) 164, instructionHandle);
  }

  public IF_ICMPLE() {}
}
