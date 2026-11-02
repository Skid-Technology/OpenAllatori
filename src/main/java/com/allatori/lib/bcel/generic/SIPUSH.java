package com.allatori.lib.bcel.generic;

import com.allatori.lib.bcel.util.ByteSequence;
import java.io.DataOutputStream;
import java.io.IOException;

public class SIPUSH extends Instruction implements ConstantPushInstruction {

  public short b;

  @Override
  public String toString(boolean matches) {
    return "" + super.toString(matches) + " " + this.b;
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visitPushInstruction(this);
    visitor.visitStackProducer(this);
    visitor.visitTypedInstruction(this);
    visitor.visitConstantPushInstruction(this);
    visitor.visitSIPUSH(this);
  }

  public SIPUSH(short b) {
    super((short) 17, (short) 3);
    this.b = b;
  }

  public SIPUSH() {}

  @Override
  public Number getValue() {
    return new Integer(this.b);
  }

  @Override
  public void dump(DataOutputStream dataOutputStream) throws IOException {
    super.dump(dataOutputStream);
    dataOutputStream.writeShort(this.b);
  }

  @Override
  public void initFromFile(ByteSequence byteSequence, boolean matches) throws IOException {
    this.length = 3;
    this.b = byteSequence.readShort();
  }

  @Override
  public Type getType(ConstantPoolGen constantPoolGen) {
    return Type.SHORT;
  }
}
