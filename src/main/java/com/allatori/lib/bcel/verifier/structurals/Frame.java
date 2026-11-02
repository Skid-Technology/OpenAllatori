package com.allatori.lib.bcel.verifier.structurals;

public class Frame {

  public static UninitializedObjectType _this;

  public LocalVariables locals;

  public OperandStack stack;

  public OperandStack getStack() {
    return this.stack;
  }

  public LocalVariables getLocals() {
    return this.locals;
  }

  @Override
  public boolean equals(Object result) {
    if (!(result instanceof Frame)) {
      return false;
    }
    Frame frame = (Frame) result;
    return this.stack.equals(frame.stack) && this.locals.equals(frame.locals);
  }

  @Override
  public int hashCode() {
    return this.stack.hashCode() ^ this.locals.hashCode();
  }

  public Frame(LocalVariables localVariables, OperandStack operandStack) {
    super();
    this.locals = localVariables;
    this.stack = operandStack;
  }

  @Override
  public Object clone() {
    return new Frame(this.locals.getClone(), this.stack.getClone());
  }

  public Frame getClone() {
    return (Frame) this.clone();
  }

  public Frame(int value, int value2) {
    super();
    this.locals = new LocalVariables(value);
    this.stack = new OperandStack(value2);
  }
}
