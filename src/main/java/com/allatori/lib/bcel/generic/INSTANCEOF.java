package com.allatori.lib.bcel.generic;

import com.allatori.lib.bcel.ExceptionConstants;

public class INSTANCEOF extends CPInstruction
    implements LoadClass, ExceptionThrower, StackProducer, StackConsumer {

  @Override
  public void accept(Visitor visitor) {
    visitor.visitLoadClass(this);
    visitor.visitExceptionThrower(this);
    visitor.visitStackProducer(this);
    visitor.visitStackConsumer(this);
    visitor.visitTypedInstruction(this);
    visitor.visitCPInstruction(this);
    visitor.visitINSTANCEOF(this);
  }

  public INSTANCEOF(int value) {
    super((short) 193, value);
  }

  @Override
  public Class[] getExceptions() {
    return ExceptionConstants.TYPES5;
  }

  @Override
  public ObjectType getLoadClassType(ConstantPoolGen constantPoolGen) {
    Type type;
    if ((type = this.getType(constantPoolGen)) instanceof ArrayType) {
      type = ((ArrayType) type).getBasicType();
    }
    return type instanceof ObjectType ? (ObjectType) type : null;
  }

  public INSTANCEOF() {}
}
