package com.allatori.lib.bcel.verifier.structurals;

import com.allatori.lib.bcel.generic.ObjectType;
import com.allatori.lib.bcel.generic.ReferenceType;
import com.allatori.lib.bcel.generic.Type;
import com.allatori.lib.bcel.verifier.exc.AssertionViolatedException;
import com.allatori.lib.bcel.verifier.exc.StructuralCodeConstraintException;
import java.util.ArrayList;

public class OperandStack {

  public int maxStack;

  public ArrayList stack;

  public boolean isEmpty() {
    return this.stack.isEmpty();
  }

  public int slotsUsed() {
    int value = 0;
    int value2;
    for (int value3 = value2 = 0; value3 < this.stack.size(); value3 = value2) {
      Type type = this.peek(value2);
      int value4 = value;
      value2++;
      value = value4 + type.getSize();
    }
    return value;
  }

  public int size() {
    return this.stack.size();
  }

  public void clear() {
    this.stack = new ArrayList();
  }

  @Override
  public int hashCode() {
    return this.stack.hashCode();
  }

  public OperandStack getClone() {
    return (OperandStack) this.clone();
  }

  public void merge(OperandStack operandStack) {
    try {
      if (this.slotsUsed() == operandStack.slotsUsed() && this.size() == operandStack.size()) {
        for (int index = 0; index < this.size(); index++) {
          if (!(this.stack.get(index) instanceof UninitializedObjectType)
              && operandStack.stack.get(index) instanceof UninitializedObjectType) {
            throw new StructuralCodeConstraintException(
                "Backwards branch with an uninitialized object on the stack detected.");
          }
          if (!this.stack.get(index).equals(operandStack.stack.get(index))
              && this.stack.get(index) instanceof UninitializedObjectType
              && !(operandStack.stack.get(index) instanceof UninitializedObjectType)) {
            throw new StructuralCodeConstraintException(
                "Backwards branch with an uninitialized object on the stack detected.");
          }
          if (this.stack.get(index) instanceof UninitializedObjectType
              && !(operandStack.stack.get(index) instanceof UninitializedObjectType)) {
            this.stack.set(
                index, ((UninitializedObjectType) this.stack.get(index)).getInitialized());
          }
          if (!this.stack.get(index).equals(operandStack.stack.get(index))) {
            if (!(this.stack.get(index) instanceof ReferenceType)
                || !(operandStack.stack.get(index) instanceof ReferenceType)) {
              throw new StructuralCodeConstraintException(
                  ("Cannot merge stacks of different types:\nStack A:\n"
                      + this
                      + "\nStack B:\n"
                      + operandStack));
            }
            this.stack.set(
                index,
                ((ReferenceType) this.stack.get(index))
                    .createReferenceType((ReferenceType) operandStack.stack.get(index)));
          }
        }
      } else {
        throw new StructuralCodeConstraintException(
            ("Cannot merge stacks of different size:\nOperandStack A:\n"
                + this
                + "\nOperandStack B:\n"
                + operandStack));
      }
    } catch (ClassNotFoundException classNotFoundException) {
      throw new AssertionViolatedException(("Missing class: " + classNotFoundException.toString()));
    }
  }

  @Override
  public Object clone() {
    OperandStack operandStack;
    (operandStack = new OperandStack(this.maxStack)).stack = (ArrayList) this.stack.clone();
    return operandStack;
  }

  public void push(Type type) {
    if (type == null) {
      throw new AssertionViolatedException("Cannot push NULL onto OperandStack.");
    }
    if (type != Type.BOOLEAN && type != Type.CHAR && type != Type.BYTE && type != Type.SHORT) {
      if (this.slotsUsed() >= this.maxStack) {
        throw new AssertionViolatedException(
            ("OperandStack too small, should have thrown proper Exception elsewhere. Stack: "
                + this));
      }
      this.stack.add(type);
    } else {
      throw new AssertionViolatedException(
          ("The OperandStack does not know about '" + type + "'; use Type.INT instead."));
    }
  }

  public Type peek(int value) {
    return (Type) this.stack.get(this.size() - value - 1);
  }

  public void initializeObject(UninitializedObjectType uninitializedObjectType) {
    int value;
    for (int value2 = value = 0; value2 < this.stack.size(); value2 = ++value) {
      if (this.stack.get(value) == uninitializedObjectType) {
        this.stack.set(value, uninitializedObjectType.getInitialized());
      }
    }
  }

  public OperandStack(int maxStack, ObjectType objectType) {
    super();
    this.stack = new ArrayList();
    this.maxStack = maxStack;
    this.push(objectType);
  }

  public OperandStack(int maxStack) {
    super();
    this.stack = new ArrayList();
    this.maxStack = maxStack;
  }

  @Override
  public boolean equals(Object result) {
    if (!(result instanceof OperandStack)) {
      return false;
    }
    OperandStack operandStack = (OperandStack) result;
    return this.stack.equals(operandStack.stack);
  }

  public Type pop() {
    return (Type) this.stack.remove(this.size() - 1);
  }

  public int maxStack() {
    return this.maxStack;
  }

  public Type pop(int value) {
    int value2;
    for (int value3 = value2 = 0; value3 < value; value3 = value2) {
      value2++;
      this.pop();
    }
    return null;
  }

  public Type peek() {
    return this.peek(0);
  }
}
