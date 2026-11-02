package com.allatori.lib.bcel.generic;

import com.allatori.lib.bcel.util.ByteSequence;
import java.io.DataOutputStream;
import java.io.IOException;

public class IINC extends LocalVariableInstruction {

  public boolean wide;

  public int c;

  public final void setIncrement(int c) {
    this.c = c;
    this.setWide();
  }

  public IINC() {}

  @Override
  public String toString(boolean matches) {
    return "" + super.toString(matches) + " " + this.c;
  }

  public IINC(int value, int value2) {
    super();
    this.opcode = 132;
    this.length = 3;
    this.setIndex(value);
    this.setIncrement(value2);
  }

  @Override
  public final void setIndex(int n) {
    if (n < 0) {
      throw new ClassGenException(("Negative index value: " + n));
    }
    this.n = n;
    this.setWide();
  }

  @Override
  public void initFromFile(ByteSequence byteSequence, boolean wide) throws IOException {
    if (this.wide = wide) {
      this.length = 6;
      this.n = byteSequence.readUnsignedShort();
      this.c = byteSequence.readShort();
    } else {
      this.length = 3;
      this.n = byteSequence.readUnsignedByte();
      this.c = byteSequence.readByte();
    }
  }

  @Override
  public void dump(DataOutputStream dataOutputStream) throws IOException {
    if (this.wide) {
      dataOutputStream.writeByte(196);
    }
    dataOutputStream.writeByte(this.opcode);
    if (this.wide) {
      dataOutputStream.writeShort(this.n);
      dataOutputStream.writeShort(this.c);
    } else {
      dataOutputStream.writeByte(this.n);
      dataOutputStream.writeByte(this.c);
    }
  }

  @Override
  public Type getType(ConstantPoolGen constantPoolGen) {
    return Type.INT;
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visitLocalVariableInstruction(this);
    visitor.visitIINC(this);
  }

  public final int getIncrement() {
    return this.c;
  }

  public final void setWide() {
    this.wide = this.n > 255 || Math.abs(this.c) > 127;
    if (this.wide) {
      this.length = 6;
    } else {
      this.length = 3;
    }
  }
}
