package com.allatori.lib.bcel.generic;

import com.allatori.lib.bcel.ExceptionConstants;

public class LDIV extends ArithmeticInstruction implements ExceptionThrower {

  public LDIV() {
    super((short) 109);
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visitExceptionThrower(this);
    visitor.visitTypedInstruction(this);
    visitor.visitStackProducer(this);
    visitor.visitStackConsumer(this);
    visitor.visitArithmeticInstruction(this);
    visitor.visitLDIV(this);
  }

  @Override
  public Class[] getExceptions() {
    return new Class[] {ExceptionConstants.TYPE18};
  }
}
