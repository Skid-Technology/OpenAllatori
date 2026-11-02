package com.allatori.lib.bcel.generic;

public class ICONST extends Instruction implements ConstantPushInstruction, TypedInstruction {

  public int value;

  @Override
  public void accept(Visitor visitor) {
    visitor.visitPushInstruction(this);
    visitor.visitStackProducer(this);
    visitor.visitTypedInstruction(this);
    visitor.visitConstantPushInstruction(this);
    visitor.visitICONST(this);
  }

  public ICONST(int value) {
    super((short) 3, (short) 1);
    if (value >= -1 && value <= 5) {
      this.opcode = (short) (3 + value);
      this.value = value;
    } else {
      throw new ClassGenException(("ICONST can be used only for value between -1 and 5: " + value));
    }
  }

  @Override
  public Number getValue() {
    return new Integer(this.value);
  }

  @Override
  public Type getType(ConstantPoolGen constantPoolGen) {
    return Type.INT;
  }

  public ICONST() {}
}
