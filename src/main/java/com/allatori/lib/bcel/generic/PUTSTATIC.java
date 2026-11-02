package com.allatori.lib.bcel.generic;

import com.allatori.lib.bcel.ExceptionConstants;

public class PUTSTATIC extends FieldInstruction implements ExceptionThrower, PopInstruction {

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
    visitor.visitPUTSTATIC(this);
  }

  @Override
  public int consumeStack(ConstantPoolGen constantPoolGen) {
    return this.getFieldSize(constantPoolGen);
  }

  public PUTSTATIC() {}

  public PUTSTATIC(int value) {
    super((short) 179, value);
  }

  @Override
  public Class[] getExceptions() {
    Class[] classs = new Class[1 + ExceptionConstants.TYPES2.length];
    System.arraycopy(ExceptionConstants.TYPES2, 0, classs, 0, ExceptionConstants.TYPES2.length);
    classs[ExceptionConstants.TYPES2.length] = ExceptionConstants.TYPE9;
    return classs;
  }
}
