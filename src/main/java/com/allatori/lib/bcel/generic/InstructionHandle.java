package com.allatori.lib.bcel.generic;

import com.allatori.lib.bcel.classfile.Utility;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class InstructionHandle implements Serializable {

  public int i_position;

  public Set targeters;

  public InstructionHandle instructionHandle;

  public Map attributes;

  public static InstructionHandle ih_list = null;

  public Instruction instruction;

  public InstructionHandle instructionHandle2;

  public void setPosition(int i_position) {
    this.i_position = i_position;
  }

  public void accept(Visitor visitor) {
    this.instruction.accept(visitor);
  }

  public final InstructionHandle getInstructionHandle() {
    return this.instructionHandle;
  }

  public InstructionTargeter[] getTargeters() {
    if (!this.hasTargeters()) {
      return null;
    }
    InstructionTargeter[] instructionTargeters = new InstructionTargeter[this.targeters.size()];
    this.targeters.toArray(instructionTargeters);
    return instructionTargeters;
  }

  public void removeAllTargeters() {
    if (this.targeters != null) {
      this.targeters.clear();
    }
  }

  public void removeAttribute(Object result) {
    if (this.attributes != null) {
      this.attributes.remove(result);
    }
  }

  public InstructionHandle(Instruction instruction) {
    super();
    this.i_position = -1;
    this.setInstruction(instruction);
  }

  public void setInstruction(Instruction instruction2) {
    if (instruction2 == null) {
      throw new ClassGenException("Assigning null to handle");
    }
    if (this.getClass() != BranchHandle.class && instruction2 instanceof BranchInstruction) {
      throw new ClassGenException(
          ("Assigning branch instruction " + instruction2 + " to plain handle"));
    }
    if (this.instruction != null) {
      this.instruction.dispose();
    }
    this.instruction = instruction2;
  }

  public Collection getAttributes() {
    if (this.attributes == null) {
      this.attributes = new HashMap(3);
    }
    return this.attributes.values();
  }

  public void update() {
    this.instructionHandle2 = this.instructionHandle = null;
    this.instruction.dispose();
    this.instruction = null;
    this.i_position = -1;
    this.attributes = null;
    this.removeAllTargeters();
    this.update2();
  }

  public int updatePosition(int i_position, int value2) {
    this.i_position += i_position;
    return 0;
  }

  public Instruction swapInstruction(Instruction instruction2) {
    Instruction instruction3 = this.instruction;
    this.instruction = instruction2;
    return instruction3;
  }

  public String toString(boolean matches) {
    return ""
        + Utility.format(this.i_position, 4, false, ' ')
        + ": "
        + this.instruction.toString(matches);
  }

  public final Instruction getInstruction() {
    return this.instruction;
  }

  public void update2() {
    this.instructionHandle2 = ih_list;
    ih_list = this;
  }

  public void addTargeter(InstructionTargeter instructionTargeter) {
    if (this.targeters == null) {
      this.targeters = new HashSet();
    }
    this.targeters.add(instructionTargeter);
  }

  public Object getAttribute(Object result) {
    return this.attributes != null ? this.attributes.get(result) : null;
  }

  public void removeTargeter(InstructionTargeter instructionTargeter) {
    if (this.targeters != null) {
      this.targeters.remove(instructionTargeter);
    }
  }

  public boolean hasTargeters() {
    return this.targeters != null && this.targeters.size() > 0;
  }

  public static final InstructionHandle getInstructionHandle(Instruction instruction) {
    if (ih_list == null) {
      return new InstructionHandle(instruction);
    }
    InstructionHandle instructionHandle = ih_list;
    ih_list = instructionHandle.instructionHandle2;
    instructionHandle.setInstruction(instruction);
    return instructionHandle;
  }

  public void addAttribute(Object result, Object result2) {
    if (this.attributes == null) {
      this.attributes = new HashMap(3);
    }
    this.attributes.put(result, result2);
  }

  public final InstructionHandle getInstructionHandle2() {
    return this.instructionHandle2;
  }

  public int getPosition() {
    return this.i_position;
  }
}
