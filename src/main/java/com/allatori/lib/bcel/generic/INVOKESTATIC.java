package com.allatori.lib.bcel.generic;

import com.allatori.lib.bcel.ExceptionConstants;

public class INVOKESTATIC extends InvokeInstruction {

  public INVOKESTATIC() {}

  @Override
  public Class[] getExceptions() {
    Class[] classs = new Class[2 + ExceptionConstants.TYPES2.length];
    System.arraycopy(ExceptionConstants.TYPES2, 0, classs, 0, ExceptionConstants.TYPES2.length);
    classs[ExceptionConstants.TYPES2.length] = ExceptionConstants.TYPE6;
    classs[ExceptionConstants.TYPES2.length + 1] = ExceptionConstants.TYPE9;
    return classs;
  }

  public INVOKESTATIC(int value) {
    super((short) 184, value);
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visitExceptionThrower(this);
    visitor.visitTypedInstruction(this);
    visitor.visitStackConsumer(this);
    visitor.visitStackProducer(this);
    visitor.visitLoadClass(this);
    visitor.visitCPInstruction(this);
    visitor.visitFieldOrMethod(this);
    visitor.visitInvokeInstruction(this);
    visitor.visitINVOKESTATIC(this);
  }
}
