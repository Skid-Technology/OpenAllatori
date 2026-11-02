package com.allatori.lib.bcel.generic;

public class DCMPG extends Instruction implements TypedInstruction, StackProducer, StackConsumer {

  @Override
  public Type getType(ConstantPoolGen constantPoolGen) {
    return Type.DOUBLE;
  }

  public DCMPG() {
    super((short) 152, (short) 1);
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visitTypedInstruction(this);
    visitor.visitStackProducer(this);
    visitor.visitStackConsumer(this);
    visitor.visitDCMPG(this);
  }
}
