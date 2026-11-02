package com.allatori.lib.bcel.generic;

public class IF_ICMPNE extends IfInstruction {

  @Override
  public void accept(Visitor visitor) {
    visitor.visitStackConsumer(this);
    visitor.visitBranchInstruction(this);
    visitor.visitIfInstruction(this);
    visitor.visitIF_ICMPNE(this);
  }

  @Override
  public IfInstruction negate() {
    return new IF_ICMPEQ(this.target);
  }

  public IF_ICMPNE() {}

  public IF_ICMPNE(InstructionHandle instructionHandle) {
    super((short) 160, instructionHandle);
  }
}
