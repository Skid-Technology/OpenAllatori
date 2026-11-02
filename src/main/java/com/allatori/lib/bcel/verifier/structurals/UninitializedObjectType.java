package com.allatori.lib.bcel.verifier.structurals;

import com.allatori.lib.bcel.Constants;
import com.allatori.lib.bcel.generic.ObjectType;
import com.allatori.lib.bcel.generic.ReferenceType;

public class UninitializedObjectType extends ReferenceType implements Constants {

  public ObjectType initialized;

  @Override
  public int hashCode() {
    return this.initialized.hashCode();
  }

  public ObjectType getInitialized() {
    return this.initialized;
  }

  @Override
  public boolean equals(Object result) {
    return !(result instanceof UninitializedObjectType)
        ? false
        : this.initialized.equals(((UninitializedObjectType) result).initialized);
  }

  public UninitializedObjectType(ObjectType objectType) {
    super((byte) 15, ("<UNINITIALIZED OBJECT OF TYPE '" + objectType.getClassName() + "'>"));
    this.initialized = objectType;
  }
}
