package com.allatori.lib.bcel.generic;

import com.allatori.lib.bcel.util.ByteSequence;
import java.io.DataOutputStream;
import java.io.IOException;

public class JSR_W extends JsrInstruction {

  @Override
  public void accept(Visitor visitor) {
    visitor.visitStackProducer(this);
    visitor.visitBranchInstruction(this);
    visitor.visitJsrInstruction(this);
    visitor.visitJSR_W(this);
  }

  public JSR_W() {}

  public JSR_W(InstructionHandle instructionHandle) {
    super((short) 201, instructionHandle);
    this.length = 5;
  }

  @Override
  public void dump(DataOutputStream dataOutputStream) throws IOException {
    this.index = this.getTargetOffset();
    dataOutputStream.writeByte(this.opcode);
    dataOutputStream.writeInt(this.index);
  }

  @Override
  public void initFromFile(ByteSequence byteSequence, boolean matches) throws IOException {
    this.index = byteSequence.readInt();
    this.length = 5;
  }
}
