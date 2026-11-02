package com.allatori.lib.bcel.generic;

import com.allatori.lib.bcel.ExceptionConstants;

public class ANEWARRAY extends CPInstruction
    implements LoadClass, AllocationInstruction, ExceptionThrower, StackConsumer, StackProducer {

  @Override
  public ObjectType getLoadClassType(ConstantPoolGen constantPoolGen) {
    Type type;
    if ((type = this.getType(constantPoolGen)) instanceof ArrayType) {
      type = ((ArrayType) type).getBasicType();
    }
    return type instanceof ObjectType ? (ObjectType) type : null;
  }

  @Override
  public Class[] getExceptions() {
    Class[] classs = new Class[1 + ExceptionConstants.TYPES5.length];
    System.arraycopy(ExceptionConstants.TYPES5, 0, classs, 0, ExceptionConstants.TYPES5.length);
    classs[ExceptionConstants.TYPES5.length] = ExceptionConstants.TYPE11;
    return classs;
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visitLoadClass(this);
    visitor.visitAllocationInstruction(this);
    visitor.visitExceptionThrower(this);
    visitor.visitStackProducer(this);
    visitor.visitTypedInstruction(this);
    visitor.visitCPInstruction(this);
    visitor.visitANEWARRAY(this);
  }

  public ANEWARRAY() {}

  public ANEWARRAY(int value) {
    super((short) 189, value);
  }
}
