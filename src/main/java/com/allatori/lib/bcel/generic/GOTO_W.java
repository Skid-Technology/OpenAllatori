package com.allatori.lib.bcel.generic;

import com.allatori.lib.bcel.util.ByteSequence;
import java.io.DataOutputStream;
import java.io.IOException;

public class GOTO_W extends GotoInstruction {

  public GOTO_W(InstructionHandle instructionHandle) {
    super((short) 200, instructionHandle);
    this.length = 5;
  }

  @Override
  public void initFromFile(ByteSequence byteSequence, boolean matches) throws IOException {
    this.index = byteSequence.readInt();
    this.length = 5;
  }

  @Override
  public void dump(DataOutputStream dataOutputStream) throws IOException {
    this.index = this.getTargetOffset();
    dataOutputStream.writeByte(this.opcode);
    dataOutputStream.writeInt(this.index);
  }

  public GOTO_W() {}

  @Override
  public void accept(Visitor visitor) {
    visitor.visitUnconditionalBranch(this);
    visitor.visitBranchInstruction(this);
    visitor.visitGotoInstruction(this);
    visitor.visitGOTO_W(this);
  }
}
