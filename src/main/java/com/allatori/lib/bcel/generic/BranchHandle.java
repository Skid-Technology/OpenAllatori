package com.allatori.lib.bcel.generic;

public final class BranchHandle extends InstructionHandle {

  public static BranchHandle bh_list = null;

  public BranchInstruction bi;

  public static final BranchHandle getBranchHandle(BranchInstruction branchInstruction) {
    if (bh_list == null) {
      return new BranchHandle(branchInstruction);
    }
    BranchHandle branchHandle = bh_list;
    bh_list = (BranchHandle) bh_list.instructionHandle2;
    branchHandle.setInstruction(branchInstruction);
    return branchHandle;
  }

  @Override
  public int updatePosition(int value, int value2) {
    int value3 = this.bi.updatePosition(value, value2);
    this.i_position = this.bi.count;
    return value3;
  }

  public BranchHandle(BranchInstruction branchInstruction) {
    super(branchInstruction);
    this.bi = branchInstruction;
  }

  public void updateTarget(
      InstructionHandle instructionHandle, InstructionHandle instructionHandle2) {
    this.bi.updateTarget(instructionHandle, instructionHandle2);
  }

  public InstructionHandle getTarget() {
    return this.bi.getTarget();
  }

  @Override
  public void setPosition(int value) {
    this.i_position = this.bi.count = value;
  }

  @Override
  public void update2() {
    this.instructionHandle2 = bh_list;
    bh_list = this;
  }

  @Override
  public void setInstruction(Instruction instruction) {
    super.setInstruction(instruction);
    if (!(instruction instanceof BranchInstruction)) {
      throw new ClassGenException(
          ("Assigning " + instruction + " to branch handle which is not a branch instruction"));
    }
    this.bi = (BranchInstruction) instruction;
  }

  @Override
  public int getPosition() {
    return this.bi.count;
  }

  public void setTarget(InstructionHandle instructionHandle) {
    this.bi.setTarget(instructionHandle);
  }
}
