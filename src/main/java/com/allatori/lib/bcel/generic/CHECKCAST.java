package com.allatori.lib.bcel.generic;

import com.allatori.lib.bcel.ExceptionConstants;

public class CHECKCAST extends CPInstruction
    implements LoadClass, ExceptionThrower, StackProducer, StackConsumer {

  @Override
  public ObjectType getLoadClassType(ConstantPoolGen constantPoolGen) {
    Type type;
    if ((type = this.getType(constantPoolGen)) instanceof ArrayType) {
      type = ((ArrayType) type).getBasicType();
    }
    return type instanceof ObjectType ? (ObjectType) type : null;
  }

  public CHECKCAST() {}

  @Override
  public void accept(Visitor visitor) {
    visitor.visitLoadClass(this);
    visitor.visitExceptionThrower(this);
    visitor.visitStackProducer(this);
    visitor.visitStackConsumer(this);
    visitor.visitTypedInstruction(this);
    visitor.visitCPInstruction(this);
    visitor.visitCHECKCAST(this);
  }

  @Override
  public Class[] getExceptions() {
    Class[] classs = new Class[1 + ExceptionConstants.TYPES5.length];
    System.arraycopy(ExceptionConstants.TYPES5, 0, classs, 0, ExceptionConstants.TYPES5.length);
    classs[ExceptionConstants.TYPES5.length] = ExceptionConstants.TYPE;
    return classs;
  }

  public CHECKCAST(int value) {
    super((short) 192, value);
  }
}
