package com.allatori.lib.bcel.generic;

import com.allatori.lib.bcel.ExceptionConstants;

public abstract class ReturnInstruction extends Instruction
    implements ExceptionThrower, TypedInstruction, StackConsumer {

  public Type getType() {
    switch (this.opcode) {
      case 172:
        return Type.INT;
      case 173:
        return Type.LONG;
      case 174:
        return Type.FLOAT;
      case 175:
        return Type.DOUBLE;
      case 176:
        return Type.OBJECT;
      case 177:
        return Type.VOID;
      default:
        throw new ClassGenException(("Unknown type " + this.opcode));
    }
  }

  @Override
  public Class[] getExceptions() {
    return new Class[] {ExceptionConstants.TYPE19};
  }

  public ReturnInstruction() {}

  @Override
  public Type getType(ConstantPoolGen constantPoolGen) {
    return this.getType();
  }

  public ReturnInstruction(short value) {
    super(value, (short) 1);
  }
}
