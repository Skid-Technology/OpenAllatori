package com.allatori.lib.bcel.generic;

import java.io.DataOutputStream;
import java.io.IOException;

public class JSR extends JsrInstruction implements VariableLengthInstruction {

  @Override
  public void dump(DataOutputStream dataOutputStream) throws IOException {
    this.index = this.getTargetOffset();
    if (this.opcode == 168) {
      super.dump(dataOutputStream);
    } else {
      this.index = this.getTargetOffset();
      dataOutputStream.writeByte(this.opcode);
      dataOutputStream.writeInt(this.index);
    }
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visitStackProducer(this);
    visitor.visitVariableLengthInstruction(this);
    visitor.visitBranchInstruction(this);
    visitor.visitJsrInstruction(this);
    visitor.visitJSR(this);
  }

  public JSR(InstructionHandle instructionHandle) {
    super((short) 168, instructionHandle);
  }

  public JSR() {}

  @Override
  public int updatePosition(int count, int value2) {
    int targetOffset = this.getTargetOffset();
    this.count += count;
    if (Math.abs(targetOffset) >= 32767 - value2) {
      this.opcode = 201;
      this.length = 5;
      return 2;
    } else {
      return 0;
    }
  }
}
