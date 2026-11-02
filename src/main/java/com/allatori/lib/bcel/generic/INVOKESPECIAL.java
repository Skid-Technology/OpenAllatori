package com.allatori.lib.bcel.generic;

import com.allatori.lib.bcel.ExceptionConstants;

public class INVOKESPECIAL extends InvokeInstruction {

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
    visitor.visitINVOKESPECIAL(this);
  }

  @Override
  public Class[] getExceptions() {
    Class[] classs = new Class[4 + ExceptionConstants.TYPES2.length];
    System.arraycopy(ExceptionConstants.TYPES2, 0, classs, 0, ExceptionConstants.TYPES2.length);
    classs[ExceptionConstants.TYPES2.length + 3] = ExceptionConstants.TYPE6;
    classs[ExceptionConstants.TYPES2.length + 2] = ExceptionConstants.TYPE14;
    classs[ExceptionConstants.TYPES2.length + 1] = ExceptionConstants.TYPE9;
    classs[ExceptionConstants.TYPES2.length] = ExceptionConstants.TYPE3;
    return classs;
  }

  public INVOKESPECIAL() {}

  public INVOKESPECIAL(int value) {
    super((short) 183, value);
  }
}
