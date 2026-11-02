package com.allatori.lib.bcel.generic;

import com.allatori.lib.bcel.util.ByteSequence;
import java.io.DataOutputStream;
import java.io.IOException;

public abstract class Select extends BranchInstruction
    implements VariableLengthInstruction, StackProducer {

  public int[] match;

  public int match_length;

  public int fixed_length;

  public int[] indices;

  public int padding = 0;

  public InstructionHandle[] targets;

  public int[] getIndices() {
    return this.indices;
  }

  public InstructionHandle[] getTargets() {
    return this.targets;
  }

  public void setTarget(int value, InstructionHandle instructionHandle) {
    notifyTarget(this.targets[value], instructionHandle, this);
    this.targets[value] = instructionHandle;
  }

  @Override
  public String toString(boolean matches) {
    StringBuffer stringBuffer = new StringBuffer(super.toString(matches));
    int value;
    if (matches) {
      for (int value2 = value = 0; value2 < this.match_length; value2 = value) {
        String text = "null";
        if (this.targets[value] != null) {
          text = this.targets[value].getInstruction().toString();
        }
        StringBuffer stringBuffer2 =
            stringBuffer
                .append("(")
                .append(this.match[value])
                .append(", ")
                .append(text)
                .append(" = {")
                .append(this.indices[value]);
        value++;
        stringBuffer2.append("})");
      }
    } else {
      stringBuffer.append(" ...");
    }
    return stringBuffer.toString();
  }

  @Override
  public void dump(DataOutputStream dataOutputStream) throws IOException {
    int value = 0;
    dataOutputStream.writeByte(this.opcode);
    for (int index2 = 0; value < this.padding; value = index2) {
      index2++;
      dataOutputStream.writeByte(0);
    }
    this.index = this.getTargetOffset();
    dataOutputStream.writeInt(this.index);
  }

  @Override
  public void initFromFile(ByteSequence byteSequence, boolean matches) throws IOException {
    int value = 0;
    this.padding = (4 - byteSequence.getIndex() % 4) % 4;
    for (int index2 = 0; value < this.padding; value = index2) {
      index2++;
      byteSequence.readByte();
    }
    this.index = byteSequence.readInt();
  }

  @Override
  public Object clone() throws CloneNotSupportedException {
    Select select;
    (select = (Select) super.clone()).match = (int[]) this.match.clone();
    select.indices = (int[]) this.indices.clone();
    select.targets = (InstructionHandle[]) this.targets.clone();
    return select;
  }

  @Override
  public boolean containsTarget(InstructionHandle instructionHandle) {
    if (this.target == instructionHandle) {
      return true;
    }
    int value;
    for (int value2 = value = 0; value2 < this.targets.length; value2 = ++value) {
      if (this.targets[value] == instructionHandle) {
        return true;
      }
    }
    return false;
  }

  @Override
  public int updatePosition(int count, int value2) {
    this.count += count;
    short value3 = this.length;
    this.padding = (4 - (this.count + 1) % 4) % 4;
    this.length = (short) (this.fixed_length + this.padding);
    return this.length - value3;
  }

  public int[] getMatch() {
    return this.match;
  }

  @Override
  public void updateTarget(
      InstructionHandle instructionHandle, InstructionHandle instructionHandle2) {
    byte value = 0;
    if (this.target == instructionHandle) {
      value = 1;
      this.setTarget(instructionHandle2);
    }
    int value2;
    for (int value3 = value2 = 0; value3 < this.targets.length; value3 = ++value2) {
      if (this.targets[value2] == instructionHandle) {
        value = 1;
        this.setTarget(value2, instructionHandle2);
      }
    }
    if (value == 0) {
      throw new ClassGenException(("Not targeting " + instructionHandle));
    }
  }

  public Select(
      short value,
      int[] match,
      InstructionHandle[] instructionHandles,
      InstructionHandle instructionHandle) {
    super(value, instructionHandle);
    int value2 = 0;
    this.padding = 0;
    this.targets = instructionHandles;
    for (int index = 0; value2 < instructionHandles.length; value2 = index) {
      int value3 = index;
      index++;
      notifyTarget((InstructionHandle) (Object) null, instructionHandles[value3], this);
    }
    if ((this.match_length = (this.match = match).length) != instructionHandles.length) {
      throw new ClassGenException("Match and target array have not the same length");
    }
    this.indices = new int[this.match_length];
  }

  @Override
  public void dispose() {
    int value = 0;
    super.dispose();
    for (int index = 0; value < this.targets.length; value = index) {
      int value2 = index;
      InstructionHandle[] instructionHandles = this.targets;
      index++;
      instructionHandles[value2].removeTargeter(this);
    }
  }

  public Select() {}
}
