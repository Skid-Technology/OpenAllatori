package com.allatori.lib.bcel.generic;

import com.allatori.lib.bcel.classfile.LocalVariable;
import java.io.Serializable;

public class LocalVariableGen
    implements InstructionTargeter, NamedAndTyped, Cloneable, Serializable {

  public InstructionHandle instructionHandle;

  public int index;

  public Type type;

  public String name;

  public InstructionHandle instructionHandle2;

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public Object clone() {
    try {
      return super.clone();
    } catch (CloneNotSupportedException cloneNotSupportedException) {
      System.err.println(cloneNotSupportedException);
      return null;
    }
  }

  public void setIndex(int index) {
    this.index = index;
  }

  @Override
  public void updateTarget(
      InstructionHandle instructionHandle3, InstructionHandle instructionHandle4) {
    byte value = 0;
    if (this.instructionHandle == instructionHandle3) {
      value = 1;
      this.processInstructionHandle2(instructionHandle4);
    }
    if (this.instructionHandle2 == instructionHandle3) {
      value = 1;
      this.processInstructionHandle(instructionHandle4);
    }
    if (value == 0) {
      throw new ClassGenException(
          ("Not targeting "
              + instructionHandle3
              + ", but {"
              + this.instructionHandle
              + ", "
              + this.instructionHandle2
              + "}"));
    }
  }

  @Override
  public boolean equals(Object result) {
    LocalVariableGen localVariableGen;
    return !(result instanceof LocalVariableGen)
        ? false
        : (localVariableGen = (LocalVariableGen) result).index == this.index
            && localVariableGen.instructionHandle == this.instructionHandle
            && localVariableGen.instructionHandle2 == this.instructionHandle2;
  }

  public int getIndex() {
    return this.index;
  }

  public InstructionHandle getInstructionHandle2() {
    return this.instructionHandle2;
  }

  @Override
  public int hashCode() {
    return this.index ^ this.name.hashCode() ^ this.type.hashCode();
  }

  public void processInstructionHandle(InstructionHandle instructionHandle) {
    BranchInstruction.notifyTarget(this.instructionHandle2, instructionHandle, this);
    this.instructionHandle2 = instructionHandle;
  }

  public LocalVariable getLocalVariable(ConstantPoolGen constantPoolGen) {
    int position = this.instructionHandle.getPosition();
    int value;
    if ((value = this.instructionHandle2.getPosition() - position) > 0) {
      value += this.instructionHandle2.getInstruction().getLength();
    }
    int value2 = constantPoolGen.addUtf8(this.name);
    int value3 = constantPoolGen.addUtf8(this.type.getSignature());
    return new LocalVariable(
        position, value, value2, value3, this.index, constantPoolGen.getConstantPool());
  }

  public InstructionHandle getInstructionHandle() {
    return this.instructionHandle;
  }

  @Override
  public void setName(String name) {
    this.name = name;
  }

  public void processInstructionHandle2(InstructionHandle instructionHandle2) {
    BranchInstruction.notifyTarget(this.instructionHandle, instructionHandle2, this);
    this.instructionHandle = instructionHandle2;
  }

  public LocalVariableGen(
      int index,
      String name,
      Type type2,
      InstructionHandle instructionHandle,
      InstructionHandle instructionHandle2) {
    super();
    if (index >= 0 && index <= 65535) {
      this.name = name;
      this.type = type2;
      this.index = index;
      this.processInstructionHandle2(instructionHandle);
      this.processInstructionHandle(instructionHandle2);
    } else {
      throw new ClassGenException(("Invalid index index: " + index));
    }
  }

  @Override
  public boolean containsTarget(InstructionHandle instructionHandle3) {
    return this.instructionHandle == instructionHandle3
        || this.instructionHandle2 == instructionHandle3;
  }

  @Override
  public Type getType() {
    return this.type;
  }

  @Override
  public void setType(Type type2) {
    this.type = type2;
  }
}
