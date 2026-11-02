package com.allatori.lib.bcel.generic;

public class ReturnaddressType extends Type {

  public static final ReturnaddressType NO_TARGET = new ReturnaddressType();

  public InstructionHandle returnTarget;

  @Override
  public int hashCode() {
    return this.returnTarget == null ? 0 : this.returnTarget.hashCode();
  }

  public ReturnaddressType(InstructionHandle instructionHandle) {
    super((byte) 16, ("<return address targeting " + instructionHandle + ">"));
    this.returnTarget = instructionHandle;
  }

  public InstructionHandle getTarget() {
    return this.returnTarget;
  }

  public ReturnaddressType() {
    super((byte) 16, "<return address>");
  }

  @Override
  public boolean equals(Object result) {
    if (!(result instanceof ReturnaddressType)) {
      return false;
    }
    ReturnaddressType returnaddressType = (ReturnaddressType) result;
    return this.returnTarget != null && returnaddressType.returnTarget != null
        ? returnaddressType.returnTarget.equals(this.returnTarget)
        : returnaddressType.returnTarget == this.returnTarget;
  }
}
