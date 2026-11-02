package com.allatori.lib.bcel.generic;

import com.allatori.lib.bcel.ExceptionConstants;

public class NEW extends CPInstruction
    implements LoadClass, AllocationInstruction, ExceptionThrower, StackProducer {

  @Override
  public void accept(Visitor visitor) {
    visitor.visitLoadClass(this);
    visitor.visitAllocationInstruction(this);
    visitor.visitExceptionThrower(this);
    visitor.visitStackProducer(this);
    visitor.visitTypedInstruction(this);
    visitor.visitCPInstruction(this);
    visitor.visitNEW(this);
  }

  @Override
  public Class[] getExceptions() {
    Class[] classs = new Class[2 + ExceptionConstants.TYPES5.length];
    System.arraycopy(ExceptionConstants.TYPES5, 0, classs, 0, ExceptionConstants.TYPES5.length);
    classs[ExceptionConstants.TYPES5.length + 1] = ExceptionConstants.TYPE2;
    classs[ExceptionConstants.TYPES5.length] = ExceptionConstants.TYPE7;
    return classs;
  }

  public NEW(int value) {
    super((short) 187, value);
  }

  @Override
  public ObjectType getLoadClassType(ConstantPoolGen constantPoolGen) {
    return (ObjectType) this.getType(constantPoolGen);
  }

  public NEW() {}
}
