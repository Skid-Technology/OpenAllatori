package com.allatori.lib.bcel.generic;

public class IFNULL extends IfInstruction {

  @Override
  public IfInstruction negate() {
    return new IFNONNULL(this.target);
  }

  public IFNULL(InstructionHandle instructionHandle) {
    super((short) 198, instructionHandle);
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visitStackConsumer(this);
    visitor.visitBranchInstruction(this);
    visitor.visitIfInstruction(this);
    visitor.visitIFNULL(this);
  }

  public IFNULL() {}
}
