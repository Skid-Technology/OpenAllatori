package com.allatori.lib.bcel.generic;

public class InstructionComparator$1 implements InstructionComparator {

  @Override
  public boolean equals(Instruction instruction, Instruction instruction2) {
    if (instruction.opcode == instruction2.opcode) {
      if (!(instruction instanceof Select)) {
        if (instruction instanceof BranchInstruction) {
          if (((BranchInstruction) instruction).target
              == ((BranchInstruction) instruction2).target) {
            return true;
          }
          return false;
        }
        if (instruction instanceof ConstantPushInstruction) {
          return ((ConstantPushInstruction) instruction)
              .getValue()
              .equals(((ConstantPushInstruction) instruction2).getValue());
        }
        if (instruction instanceof IndexedInstruction) {
          if (((IndexedInstruction) instruction).getIndex()
              == ((IndexedInstruction) instruction2).getIndex()) {
            return true;
          }
          return false;
        }
        if (instruction instanceof NEWARRAY) {
          if (((NEWARRAY) instruction).getTypecode() == ((NEWARRAY) instruction2).getTypecode()) {
            return true;
          }
          return false;
        }
        return true;
      }
      InstructionHandle[] targets = ((Select) instruction).getTargets();
      InstructionHandle[] targets2 = ((Select) instruction2).getTargets();
      if (targets.length == targets2.length) {
        int value;
        for (int value2 = value = 0; value2 < targets.length; value2 = ++value) {
          if (targets[value] != targets2[value]) {
            return false;
          }
        }
        return true;
      }
    }
    return false;
  }
}
