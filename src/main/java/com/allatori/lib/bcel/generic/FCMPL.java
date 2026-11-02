package com.allatori.lib.bcel.generic;

public class FCMPL extends Instruction implements TypedInstruction, StackProducer, StackConsumer {

  @Override
  public Type getType(ConstantPoolGen constantPoolGen) {
    return Type.FLOAT;
  }

  public FCMPL() {
    super((short) 149, (short) 1);
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visitTypedInstruction(this);
    visitor.visitStackProducer(this);
    visitor.visitStackConsumer(this);
    visitor.visitFCMPL(this);
  }
}
