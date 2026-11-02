package com.allatori.lib.bcel.generic;

public class FCONST extends Instruction implements ConstantPushInstruction, TypedInstruction {

  public float value;

  @Override
  public void accept(Visitor visitor) {
    visitor.visitPushInstruction(this);
    visitor.visitStackProducer(this);
    visitor.visitTypedInstruction(this);
    visitor.visitConstantPushInstruction(this);
    visitor.visitFCONST(this);
  }

  public FCONST() {}

  @Override
  public Type getType(ConstantPoolGen constantPoolGen) {
    return Type.FLOAT;
  }

  public FCONST(float value2) {
    super((short) 11, (short) 1);
    FCONST fCONST;
    if (value2 == 0.0) {
      fCONST = this;
      this.opcode = 11;
    } else if (value2 == 1.0) {
      fCONST = this;
      this.opcode = 12;
    } else {
      if (value2 != 2.0) {
        throw new ClassGenException(("FCONST can be used only for 0.0, 1.0 and 2.0: " + value2));
      }
      fCONST = this;
      this.opcode = 13;
    }
    fCONST.value = value2;
  }

  @Override
  public Number getValue() {
    return new Float(this.value);
  }
}
