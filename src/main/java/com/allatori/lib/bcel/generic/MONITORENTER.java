package com.allatori.lib.bcel.generic;

import com.allatori.lib.bcel.ExceptionConstants;

public class MONITORENTER extends Instruction implements ExceptionThrower, StackConsumer {

  @Override
  public void accept(Visitor visitor) {
    visitor.visitExceptionThrower(this);
    visitor.visitStackConsumer(this);
    visitor.visitMONITORENTER(this);
  }

  @Override
  public Class[] getExceptions() {
    return new Class[] {ExceptionConstants.TYPE3};
  }

  public MONITORENTER() {
    super((short) 194, (short) 1);
  }
}
