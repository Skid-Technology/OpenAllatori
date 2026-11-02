package com.allatori.lib.bcel.generic;

import com.allatori.lib.bcel.ExceptionConstants;

public class GETSTATIC extends FieldInstruction implements PushInstruction, ExceptionThrower {

  public GETSTATIC(int value) {
    super((short) 178, value);
  }

  @Override
  public Class[] getExceptions() {
    Class[] classs = new Class[1 + ExceptionConstants.TYPES2.length];
    System.arraycopy(ExceptionConstants.TYPES2, 0, classs, 0, ExceptionConstants.TYPES2.length);
    classs[ExceptionConstants.TYPES2.length] = ExceptionConstants.TYPE9;
    return classs;
  }

  public GETSTATIC() {}

  @Override
  public int produceStack(ConstantPoolGen constantPoolGen) {
    return this.getFieldSize(constantPoolGen);
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visitStackProducer(this);
    visitor.visitPushInstruction(this);
    visitor.visitExceptionThrower(this);
    visitor.visitTypedInstruction(this);
    visitor.visitLoadClass(this);
    visitor.visitCPInstruction(this);
    visitor.visitFieldOrMethod(this);
    visitor.visitFieldInstruction(this);
    visitor.visitGETSTATIC(this);
  }
}
