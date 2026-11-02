package com.allatori.lib.bcel.generic;

import com.allatori.lib.bcel.ExceptionConstants;

public class GETFIELD extends FieldInstruction
    implements ExceptionThrower, StackConsumer, StackProducer {

  @Override
  public void accept(Visitor visitor) {
    visitor.visitExceptionThrower(this);
    visitor.visitStackConsumer(this);
    visitor.visitStackProducer(this);
    visitor.visitTypedInstruction(this);
    visitor.visitLoadClass(this);
    visitor.visitCPInstruction(this);
    visitor.visitFieldOrMethod(this);
    visitor.visitFieldInstruction(this);
    visitor.visitGETFIELD(this);
  }

  public GETFIELD(int value) {
    super((short) 180, value);
  }

  @Override
  public Class[] getExceptions() {
    Class[] classs = new Class[2 + ExceptionConstants.TYPES2.length];
    System.arraycopy(ExceptionConstants.TYPES2, 0, classs, 0, ExceptionConstants.TYPES2.length);
    classs[ExceptionConstants.TYPES2.length + 1] = ExceptionConstants.TYPE9;
    classs[ExceptionConstants.TYPES2.length] = ExceptionConstants.TYPE3;
    return classs;
  }

  public GETFIELD() {}

  @Override
  public int produceStack(ConstantPoolGen constantPoolGen) {
    return this.getFieldSize(constantPoolGen);
  }
}
