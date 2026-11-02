package com.allatori.lib.bcel.generic;

public class IFGE extends IfInstruction {

  public IFGE(InstructionHandle instructionHandle) {
    super((short) 156, instructionHandle);
  }

  @Override
  public IfInstruction negate() {
    return new IFLT(this.target);
  }

  public IFGE() {}

  @Override
  public void accept(Visitor visitor) {
    visitor.visitStackConsumer(this);
    visitor.visitBranchInstruction(this);
    visitor.visitIfInstruction(this);
    visitor.visitIFGE(this);
  }
}
