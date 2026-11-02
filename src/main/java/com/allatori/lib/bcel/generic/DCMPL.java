package com.allatori.lib.bcel.generic;

public class DCMPL extends Instruction implements TypedInstruction, StackProducer, StackConsumer {

  public DCMPL() {
    super((short) 151, (short) 1);
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visitTypedInstruction(this);
    visitor.visitStackProducer(this);
    visitor.visitStackConsumer(this);
    visitor.visitDCMPL(this);
  }

  @Override
  public Type getType(ConstantPoolGen constantPoolGen) {
    return Type.DOUBLE;
  }
}
