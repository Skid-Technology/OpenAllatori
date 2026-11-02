package com.allatori.lib.bcel.verifier.structurals;

import com.allatori.lib.bcel.generic.InstructionHandle;
import com.allatori.lib.bcel.generic.ObjectType;

public class ExceptionHandler {

  public InstructionHandle handlerpc;

  public ObjectType catchtype;

  public InstructionHandle getHandlerStart() {
    return this.handlerpc;
  }

  public ObjectType getExceptionType() {
    return this.catchtype;
  }

  public ExceptionHandler(ObjectType objectType, InstructionHandle instructionHandle) {
    super();
    this.catchtype = objectType;
    this.handlerpc = instructionHandle;
  }
}
