package com.allatori.lib.bcel.generic;

public class IFLE extends IfInstruction {

  public IFLE(InstructionHandle instructionHandle) {
    super((short) 158, instructionHandle);
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visitStackConsumer(this);
    visitor.visitBranchInstruction(this);
    visitor.visitIfInstruction(this);
    visitor.visitIFLE(this);
  }

  @Override
  public IfInstruction negate() {
    return new IFGT(this.target);
  }

  public IFLE() {}
}
