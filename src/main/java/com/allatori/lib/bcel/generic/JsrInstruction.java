package com.allatori.lib.bcel.generic;

public abstract class JsrInstruction extends BranchInstruction
    implements UnconditionalBranch, TypedInstruction, StackProducer {

  public JsrInstruction() {}

  @Override
  public Type getType(ConstantPoolGen constantPoolGen) {
    return new ReturnaddressType(this.physicalSuccessor());
  }

  public InstructionHandle physicalSuccessor() {
    InstructionHandle instructionHandle;
    InstructionHandle instructionHandle2 = instructionHandle = this.target;
    while (instructionHandle2.getInstructionHandle() != null) {
      instructionHandle2 = instructionHandle = instructionHandle.getInstructionHandle();
    }
    instructionHandle2 = instructionHandle;
    while (instructionHandle2.getInstruction() != this) {
      instructionHandle2 = instructionHandle = instructionHandle.getInstructionHandle2();
    }
    InstructionHandle instructionHandle3 = instructionHandle;
    while (instructionHandle != null) {
      if ((instructionHandle = instructionHandle.getInstructionHandle2()) != null
          && instructionHandle.getInstruction() == this) {
        throw new RuntimeException("physicalSuccessor() called on a shared JsrInstruction.");
      }
    }
    return instructionHandle3.getInstructionHandle2();
  }

  public JsrInstruction(short value, InstructionHandle instructionHandle) {
    super(value, instructionHandle);
  }
}
