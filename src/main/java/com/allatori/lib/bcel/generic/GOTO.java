package com.allatori.lib.bcel.generic;

import java.io.DataOutputStream;
import java.io.IOException;

public class GOTO extends GotoInstruction implements VariableLengthInstruction {

  @Override
  public void accept(Visitor visitor) {
    visitor.visitVariableLengthInstruction(this);
    visitor.visitUnconditionalBranch(this);
    visitor.visitBranchInstruction(this);
    visitor.visitGotoInstruction(this);
    visitor.visitGOTO(this);
  }

  @Override
  public void dump(DataOutputStream dataOutputStream) throws IOException {
    this.index = this.getTargetOffset();
    if (this.opcode == 167) {
      super.dump(dataOutputStream);
    } else {
      this.index = this.getTargetOffset();
      dataOutputStream.writeByte(this.opcode);
      dataOutputStream.writeInt(this.index);
    }
  }

  public GOTO(InstructionHandle instructionHandle) {
    super((short) 167, instructionHandle);
  }

  @Override
  public int updatePosition(int count, int value2) {
    int targetOffset = this.getTargetOffset();
    this.count += count;
    if (Math.abs(targetOffset) >= 32767 - value2) {
      this.opcode = 200;
      this.length = 5;
      return 2;
    } else {
      return 0;
    }
  }

  public GOTO() {}
}
