package com.allatori.lib.bcel.generic;

public final class SWITCH implements CompoundInstruction {

  public InstructionHandle[] targets;

  public int[] match;

  public Select instruction;

  public int match_length;

  @Override
  public final InstructionList getInstructionList() {
    return new InstructionList(this.instruction);
  }

  public SWITCH(
      int[] values,
      InstructionHandle[] instructionHandles,
      InstructionHandle instructionHandle,
      int value) {
    super();
    this.match = (int[]) values.clone();
    this.targets = (InstructionHandle[]) instructionHandles.clone();
    if ((this.match_length = values.length) < 2) {
      this.instruction = new TABLESWITCH(values, instructionHandles, instructionHandle);
    } else {
      this.sort(0, this.match_length - 1);
      if (this.matchIsOrdered(value)) {
        this.fillup(value, instructionHandle);
        this.instruction = new TABLESWITCH(this.match, this.targets, instructionHandle);
      } else {
        this.instruction = new LOOKUPSWITCH(this.match, this.targets, instructionHandle);
      }
    }
  }

  public final boolean matchIsOrdered(int value) {
    int value2;
    for (int value3 = value2 = 1; value3 < this.match_length; value3 = ++value2) {
      if (this.match[value2] - this.match[value2 - 1] > value) {
        return false;
      }
    }
    return true;
  }

  public final void sort(int value, int value2) {
    int value3 = value;
    int value4 = value2;
    int value5 = this.match[(value + value2) / 2];
    do {
      SWITCH sWITCH = this;
      while (sWITCH.match[value3] < value5) {
        value3++;
        sWITCH = this;
      }
      int value6 = value5;
      while (value6 < this.match[value4]) {
        value4--;
        value6 = value5;
      }
      if (value3 <= value4) {
        int value7 = this.match[value3];
        this.match[value3] = this.match[value4];
        this.match[value4] = value7;
        InstructionHandle instructionHandle = this.targets[value3];
        this.targets[value3] = this.targets[value4];
        int value8 = value4;
        InstructionHandle[] instructionHandles = this.targets;
        value3++;
        instructionHandles[value8] = instructionHandle;
        value4--;
      }
    } while (value3 <= value4);
    if (value < value4) {
      this.sort(value, value4);
    }
    if (value3 < value2) {
      this.sort(value3, value2);
    }
  }

  public final void fillup(int value, InstructionHandle instructionHandle) {
    int value2;
    int[] values = new int[value2 = this.match_length + this.match_length * value];
    InstructionHandle[] instructionHandles = new InstructionHandle[value2];
    int value3 = 1;
    int value4 = 1;
    values[0] = this.match[0];
    instructionHandles[0] = this.targets[0];
    for (int index = 1; value4 < this.match_length; value4 = ++index) {
      int value5 = this.match[index - 1];
      int value6 = this.match[index] - value5;
      int value7;
      for (int value8 = value7 = 1; value8 < value6; value8 = ++value7) {
        values[value3] = value5 + value7;
        int value9 = value3;
        value3++;
        instructionHandles[value9] = instructionHandle;
      }
      values[value3] = this.match[index];
      InstructionHandle instructionHandle2 = this.targets[index];
      int value10 = value3;
      value3++;
      instructionHandles[value10] = instructionHandle2;
    }
    this.match = new int[value3];
    this.targets = new InstructionHandle[value3];
    System.arraycopy(values, 0, this.match, 0, value3);
    System.arraycopy(instructionHandles, 0, this.targets, 0, value3);
  }

  public final Instruction getInstruction() {
    return this.instruction;
  }

  public SWITCH(
      int[] values, InstructionHandle[] instructionHandles, InstructionHandle instructionHandle) {
    this(values, instructionHandles, instructionHandle, 1);
  }
}
