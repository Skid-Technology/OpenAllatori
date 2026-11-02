package com.allatori.lib.bcel.generic;

import com.allatori.lib.bcel.util.ByteSequence;
import java.io.DataOutputStream;
import java.io.IOException;

public class LOOKUPSWITCH extends Select {

  public LOOKUPSWITCH() {}

  @Override
  public void dump(DataOutputStream dataOutputStream) throws IOException {
    int value = 0;
    super.dump(dataOutputStream);
    dataOutputStream.writeInt(this.match_length);
    for (int index = 0; value < this.match_length; value = index) {
      dataOutputStream.writeInt(this.match[index]);
      int[] values = this.indices;
      int value2;
      int value3 = value2 = this.getTargetOffset(this.targets[index]);
      int value4 = index;
      index++;
      values[value4] = value2;
      dataOutputStream.writeInt(value3);
    }
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visitVariableLengthInstruction(this);
    visitor.visitStackProducer(this);
    visitor.visitBranchInstruction(this);
    visitor.visitSelect(this);
    visitor.visitLOOKUPSWITCH(this);
  }

  @Override
  public void initFromFile(ByteSequence byteSequence, boolean matches) throws IOException {
    super.initFromFile(byteSequence, matches);
    this.match_length = byteSequence.readInt();
    this.fixed_length = (short) (9 + this.match_length * 8);
    this.length = (short) (this.fixed_length + this.padding);
    this.match = new int[this.match_length];
    this.indices = new int[this.match_length];
    this.targets = new InstructionHandle[this.match_length];
    int value;
    for (int value2 = value = 0; value2 < this.match_length; value2 = value) {
      this.match[value] = byteSequence.readInt();
      int[] values = this.indices;
      int value3 = byteSequence.readInt();
      int value4 = value;
      value++;
      values[value4] = value3;
    }
  }

  public LOOKUPSWITCH(
      int[] values, InstructionHandle[] instructionHandles, InstructionHandle instructionHandle) {
    super((short) 171, values, instructionHandles, instructionHandle);
    this.length = (short) (9 + this.match_length * 8);
    this.fixed_length = this.length;
  }
}
