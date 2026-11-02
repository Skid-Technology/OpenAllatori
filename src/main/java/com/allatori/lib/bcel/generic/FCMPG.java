package com.allatori.lib.bcel.generic;

public class FCMPG extends Instruction implements TypedInstruction, StackProducer, StackConsumer {

  @Override
  public void accept(Visitor visitor) {
    visitor.visitTypedInstruction(this);
    visitor.visitStackProducer(this);
    visitor.visitStackConsumer(this);
    visitor.visitFCMPG(this);
  }

  public FCMPG() {
    super((short) 150, (short) 1);
  }

  @Override
  public Type getType(ConstantPoolGen constantPoolGen) {
    return Type.FLOAT;
  }
}
