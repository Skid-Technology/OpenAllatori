package com.allatori.lib.bcel.generic;

import com.allatori.lib.bcel.ExceptionConstants;

public class IDIV extends ArithmeticInstruction implements ExceptionThrower {

  @Override
  public Class[] getExceptions() {
    return new Class[] {ExceptionConstants.TYPE18};
  }

  public IDIV() {
    super((short) 108);
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visitExceptionThrower(this);
    visitor.visitTypedInstruction(this);
    visitor.visitStackProducer(this);
    visitor.visitStackConsumer(this);
    visitor.visitArithmeticInstruction(this);
    visitor.visitIDIV(this);
  }
}
