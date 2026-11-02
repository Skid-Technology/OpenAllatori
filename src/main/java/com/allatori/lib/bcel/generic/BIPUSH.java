package com.allatori.lib.bcel.generic;

import com.allatori.lib.bcel.util.ByteSequence;
import java.io.DataOutputStream;
import java.io.IOException;

public class BIPUSH extends Instruction implements ConstantPushInstruction {

  public byte b;

  @Override
  public void initFromFile(ByteSequence byteSequence, boolean matches) throws IOException {
    this.length = 2;
    this.b = byteSequence.readByte();
  }

  public BIPUSH(byte b) {
    super((short) 16, (short) 2);
    this.b = b;
  }

  @Override
  public Number getValue() {
    return new Integer(this.b);
  }

  @Override
  public void dump(DataOutputStream dataOutputStream) throws IOException {
    super.dump(dataOutputStream);
    dataOutputStream.writeByte(this.b);
  }

  @Override
  public String toString(boolean matches) {
    return "" + super.toString(matches) + " " + this.b;
  }

  public BIPUSH() {}

  @Override
  public void accept(Visitor visitor) {
    visitor.visitPushInstruction(this);
    visitor.visitStackProducer(this);
    visitor.visitTypedInstruction(this);
    visitor.visitConstantPushInstruction(this);
    visitor.visitBIPUSH(this);
  }

  @Override
  public Type getType(ConstantPoolGen constantPoolGen) {
    return Type.BYTE;
  }
}
