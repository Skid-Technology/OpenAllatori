package com.allatori.lib.bcel.generic;

import com.allatori.lib.bcel.ExceptionConstants;

public class ARRAYLENGTH extends Instruction implements ExceptionThrower, StackProducer {

  @Override
  public Class[] getExceptions() {
    return new Class[] {ExceptionConstants.TYPE3};
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visitExceptionThrower(this);
    visitor.visitStackProducer(this);
    visitor.visitARRAYLENGTH(this);
  }

  public ARRAYLENGTH() {
    super((short) 190, (short) 1);
  }
}
