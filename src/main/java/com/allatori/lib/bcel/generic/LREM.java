package com.allatori.lib.bcel.generic;

import com.allatori.lib.bcel.ExceptionConstants;

public class LREM extends ArithmeticInstruction implements ExceptionThrower {

  public LREM() {
    super((short) 113);
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visitExceptionThrower(this);
    visitor.visitTypedInstruction(this);
    visitor.visitStackProducer(this);
    visitor.visitStackConsumer(this);
    visitor.visitArithmeticInstruction(this);
    visitor.visitLREM(this);
  }

  @Override
  public Class[] getExceptions() {
    return new Class[] {ExceptionConstants.TYPE18};
  }
}
