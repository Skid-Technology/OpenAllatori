package com.allatori.lib.bcel.generic;

import com.allatori.lib.bcel.ExceptionConstants;

public class PUTFIELD extends FieldInstruction implements PopInstruction, ExceptionThrower {

  public PUTFIELD() {}

  @Override
  public int consumeStack(ConstantPoolGen constantPoolGen) {
    return this.getFieldSize(constantPoolGen) + 1;
  }

  public PUTFIELD(int value) {
    super((short) 181, value);
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visitExceptionThrower(this);
    visitor.visitStackConsumer(this);
    visitor.visitPopInstruction(this);
    visitor.visitTypedInstruction(this);
    visitor.visitLoadClass(this);
    visitor.visitCPInstruction(this);
    visitor.visitFieldOrMethod(this);
    visitor.visitFieldInstruction(this);
    visitor.visitPUTFIELD(this);
  }

  @Override
  public Class[] getExceptions() {
    Class[] classs = new Class[2 + ExceptionConstants.TYPES2.length];
    System.arraycopy(ExceptionConstants.TYPES2, 0, classs, 0, ExceptionConstants.TYPES2.length);
    classs[ExceptionConstants.TYPES2.length + 1] = ExceptionConstants.TYPE9;
    classs[ExceptionConstants.TYPES2.length] = ExceptionConstants.TYPE3;
    return classs;
  }
}
