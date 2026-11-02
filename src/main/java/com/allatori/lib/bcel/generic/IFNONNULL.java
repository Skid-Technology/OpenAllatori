package com.allatori.lib.bcel.generic;

public class IFNONNULL extends IfInstruction {

  @Override
  public void accept(Visitor visitor) {
    visitor.visitStackConsumer(this);
    visitor.visitBranchInstruction(this);
    visitor.visitIfInstruction(this);
    visitor.visitIFNONNULL(this);
  }

  @Override
  public IfInstruction negate() {
    return new IFNULL(this.target);
  }

  public IFNONNULL(InstructionHandle instructionHandle) {
    super((short) 199, instructionHandle);
  }

  public IFNONNULL() {}
}
