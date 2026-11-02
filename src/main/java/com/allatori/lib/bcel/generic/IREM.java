package com.allatori.lib.bcel.generic;

import com.allatori.lib.bcel.ExceptionConstants;

public class IREM extends ArithmeticInstruction implements ExceptionThrower {

  @Override
  public Class[] getExceptions() {
    return new Class[] {ExceptionConstants.TYPE18};
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visitExceptionThrower(this);
    visitor.visitTypedInstruction(this);
    visitor.visitStackProducer(this);
    visitor.visitStackConsumer(this);
    visitor.visitArithmeticInstruction(this);
    visitor.visitIREM(this);
  }

  public IREM() {
    super((short) 112);
  }
}
