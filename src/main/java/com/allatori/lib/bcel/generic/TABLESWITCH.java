package com.allatori.lib.bcel.generic;

import com.allatori.lib.bcel.util.ByteSequence;
import java.io.DataOutputStream;
import java.io.IOException;

public class TABLESWITCH extends Select {

  public TABLESWITCH() {}

  public TABLESWITCH(
      int[] values, InstructionHandle[] instructionHandles, InstructionHandle instructionHandle) {
    super((short) 170, values, instructionHandles, instructionHandle);
    this.length = (short) (13 + this.match_length * 4);
    this.fixed_length = this.length;
  }

  @Override
  public void dump(DataOutputStream dataOutputStream) throws IOException {
    super.dump(dataOutputStream);
    int value = this.match_length > 0 ? this.match[0] : 0;
    dataOutputStream.writeInt(value);
    int value2 = this.match_length > 0 ? this.match[this.match_length - 1] : 0;
    int value3 = 0;
    dataOutputStream.writeInt(value2);
    for (int index = 0; value3 < this.match_length; value3 = index) {
      int[] values = this.indices;
      int value4;
      int value5 = value4 = this.getTargetOffset(this.targets[index]);
      int value6 = index;
      index++;
      values[value6] = value4;
      dataOutputStream.writeInt(value5);
    }
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visitVariableLengthInstruction(this);
    visitor.visitStackProducer(this);
    visitor.visitBranchInstruction(this);
    visitor.visitSelect(this);
    visitor.visitTABLESWITCH(this);
  }

  @Override
  public void initFromFile(ByteSequence byteSequence, boolean matches) throws IOException {
    super.initFromFile(byteSequence, matches);
    int value = byteSequence.readInt();
    int value2;
    this.match_length = (value2 = byteSequence.readInt()) - value + 1;
    this.fixed_length = (short) (13 + this.match_length * 4);
    this.length = (short) (this.fixed_length + this.padding);
    this.match = new int[this.match_length];
    this.indices = new int[this.match_length];
    this.targets = new InstructionHandle[this.match_length];
    int value3;
    for (int value4 = value3 = value; value4 <= value2; value4 = value3) {
      int value5 = value3 - value;
      int value6 = value3;
      int[] values = this.match;
      value3++;
      values[value5] = value6;
    }
    int value7;
    for (int value8 = value7 = 0; value8 < this.match_length; value8 = value7) {
      int[] values2 = this.indices;
      int value9 = byteSequence.readInt();
      int value10 = value7;
      value7++;
      values2[value10] = value9;
    }
  }
}
