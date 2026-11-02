package com.allatori.lib.bcel.generic;

public class IF_ICMPEQ extends IfInstruction {

  public IF_ICMPEQ() {}

  @Override
  public IfInstruction negate() {
    return new IF_ICMPNE(this.target);
  }

  public IF_ICMPEQ(InstructionHandle instructionHandle) {
    super((short) 159, instructionHandle);
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visitStackConsumer(this);
    visitor.visitBranchInstruction(this);
    visitor.visitIfInstruction(this);
    visitor.visitIF_ICMPEQ(this);
  }
}
