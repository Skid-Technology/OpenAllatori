package com.allatori.lib.bcel.verifier.structurals;

import com.allatori.lib.bcel.generic.ReferenceType;
import com.allatori.lib.bcel.generic.Type;
import com.allatori.lib.bcel.verifier.exc.AssertionViolatedException;
import com.allatori.lib.bcel.verifier.exc.StructuralCodeConstraintException;

public class LocalVariables {

  public Type[] locals;

  public LocalVariables getClone() {
    return (LocalVariables) this.clone();
  }

  public LocalVariables(int value) {
    this.locals = new Type[value];
    int value2;
    for (int value3 = value2 = 0; value3 < value; value3 = value2) {
      Type type = Type.UNKNOWN;
      int value4 = value2;
      Type[] types = this.locals;
      value2++;
      types[value4] = type;
    }
  }

  @Override
  public Object clone() {
    LocalVariables localVariables = new LocalVariables(this.locals.length);
    int value;
    for (int value2 = value = 0; value2 < this.locals.length; value2 = value) {
      Type type = this.locals[value];
      int value3 = value;
      Type[] types = localVariables.locals;
      value++;
      types[value3] = type;
    }
    return localVariables;
  }

  public Type get(int value) {
    return this.locals[value];
  }

  @Override
  public int hashCode() {
    return this.locals.length;
  }

  public void merge(LocalVariables localVariables) {
    if (this.locals.length != localVariables.locals.length) {
      throw new AssertionViolatedException(
          "Merging LocalVariables of different size?!? From different methods or what?!?");
    }
    int value;
    for (int value2 = value = 0; value2 < this.locals.length; value2 = value) {
      int value3 = value;
      value++;
      this.merge(localVariables, value3);
    }
  }

  public void merge(LocalVariables localVariables, int value) {
    try {
      if (!(this.locals[value] instanceof UninitializedObjectType)
          && localVariables.locals[value] instanceof UninitializedObjectType) {
        throw new StructuralCodeConstraintException(
            "Backwards branch with an uninitialized object in the local variables detected.");
      }
      if (!this.locals[value].equals(localVariables.locals[value])
          && this.locals[value] instanceof UninitializedObjectType
          && localVariables.locals[value] instanceof UninitializedObjectType) {
        throw new StructuralCodeConstraintException(
            "Backwards branch with an uninitialized object in the local variables detected.");
      }
      if (this.locals[value] instanceof UninitializedObjectType
          && !(localVariables.locals[value] instanceof UninitializedObjectType)) {
        this.locals[value] = ((UninitializedObjectType) this.locals[value]).getInitialized();
      }
      if (this.locals[value] instanceof ReferenceType
          && localVariables.locals[value] instanceof ReferenceType) {
        if (!this.locals[value].equals(localVariables.locals[value])) {
          ReferenceType referenceType;
          if ((referenceType =
                  ((ReferenceType) this.locals[value])
                      .createReferenceType((ReferenceType) localVariables.locals[value]))
              != null) {
            this.locals[value] = referenceType;
            return;
          }
          throw new AssertionViolatedException(
              ("Could not load all the super classes of '"
                  + this.locals[value]
                  + "' and '"
                  + localVariables.locals[value]
                  + "'."));
        }
      } else if (!this.locals[value].equals(localVariables.locals[value])) {
        this.locals[value] = Type.UNKNOWN;
        return;
      }
    } catch (ClassNotFoundException classNotFoundException) {
      throw new AssertionViolatedException(("Missing class: " + classNotFoundException.toString()));
    }
  }

  public void set(int value, Type type) {
    if (type != Type.BYTE && type != Type.SHORT && type != Type.BOOLEAN && type != Type.CHAR) {
      this.locals[value] = type;
    } else {
      throw new AssertionViolatedException(
          ("LocalVariables do not know about '" + type + "'. Use Type.INT instead."));
    }
  }

  public int getLocals() {
    return this.locals.length;
  }

  @Override
  public boolean equals(Object result) {
    if (!(result instanceof LocalVariables)) {
      return false;
    }
    LocalVariables localVariables = (LocalVariables) result;
    if (this.locals.length != localVariables.locals.length) {
      return false;
    }
    int value;
    for (int value2 = value = 0; value2 < this.locals.length; value2 = ++value) {
      if (!this.locals[value].equals(localVariables.locals[value])) {
        return false;
      }
    }
    return true;
  }

  public void initializeObject(UninitializedObjectType uninitializedObjectType) {
    int value;
    for (int value2 = value = 0; value2 < this.locals.length; value2 = ++value) {
      if (this.locals[value] == uninitializedObjectType) {
        this.locals[value] = uninitializedObjectType.getInitialized();
      }
    }
  }
}
