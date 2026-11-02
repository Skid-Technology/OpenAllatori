package com.allatori.lib.bcel.generic;

public class DCONST extends Instruction implements ConstantPushInstruction, TypedInstruction {

  public double value;

  public DCONST(double value2) {
    super((short) 14, (short) 1);
    DCONST dCONST;
    if (value2 == 0.0) {
      dCONST = this;
      this.opcode = 14;
    } else {
      if (value2 != 1.0) {
        throw new ClassGenException(("DCONST can be used only for 0.0 and 1.0: " + value2));
      }
      dCONST = this;
      this.opcode = 15;
    }
    dCONST.value = value2;
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visitPushInstruction(this);
    visitor.visitStackProducer(this);
    visitor.visitTypedInstruction(this);
    visitor.visitConstantPushInstruction(this);
    visitor.visitDCONST(this);
  }

  @Override
  public Type getType(ConstantPoolGen constantPoolGen) {
    return Type.DOUBLE;
  }

  @Override
  public Number getValue() {
    return new Double(this.value);
  }

  public DCONST() {}
}
