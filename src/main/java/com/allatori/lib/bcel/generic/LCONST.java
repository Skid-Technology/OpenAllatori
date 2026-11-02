package com.allatori.lib.bcel.generic;

public class LCONST extends Instruction implements ConstantPushInstruction, TypedInstruction {

  public long value;

  public LCONST() {}

  @Override
  public Type getType(ConstantPoolGen constantPoolGen) {
    return Type.LONG;
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visitPushInstruction(this);
    visitor.visitStackProducer(this);
    visitor.visitTypedInstruction(this);
    visitor.visitConstantPushInstruction(this);
    visitor.visitLCONST(this);
  }

  public LCONST(long value2) {
    super((short) 9, (short) 1);
    LCONST lCONST;
    if (value2 == 0L) {
      lCONST = this;
      this.opcode = 9;
    } else {
      if (value2 != 1L) {
        throw new ClassGenException(("LCONST can be used only for 0 and 1: " + value2));
      }
      lCONST = this;
      this.opcode = 10;
    }
    lCONST.value = value2;
  }

  @Override
  public Number getValue() {
    return new Long(this.value);
  }
}
