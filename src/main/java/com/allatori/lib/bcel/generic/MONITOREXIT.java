package com.allatori.lib.bcel.generic;

import com.allatori.lib.bcel.ExceptionConstants;

public class MONITOREXIT extends Instruction implements ExceptionThrower, StackConsumer {

  public MONITOREXIT() {
    super((short) 195, (short) 1);
  }

  @Override
  public Class[] getExceptions() {
    return new Class[] {ExceptionConstants.TYPE3};
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visitExceptionThrower(this);
    visitor.visitStackConsumer(this);
    visitor.visitMONITOREXIT(this);
  }
}
