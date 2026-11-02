package com.allatori.lib.bcel.generic;

public interface InstructionComparator {

  InstructionComparator DEFAULT = new InstructionComparator$1();

  boolean equals(Instruction instruction, Instruction instruction2);
}
