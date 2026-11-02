package com.allatori.lib.bcel.generic;

public class IFGT extends IfInstruction {

  @Override
  public IfInstruction negate() {
    return new IFLE(this.target);
  }

  public IFGT() {}

  @Override
  public void accept(Visitor visitor) {
    visitor.visitStackConsumer(this);
    visitor.visitBranchInstruction(this);
    visitor.visitIfInstruction(this);
    visitor.visitIFGT(this);
  }

  public IFGT(InstructionHandle instructionHandle) {
    super((short) 157, instructionHandle);
  }
}
