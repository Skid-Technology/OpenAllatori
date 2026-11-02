package com.allatori.lib.bcel.generic;

import com.allatori.lib.bcel.util.ByteSequence;
import java.io.DataOutputStream;
import java.io.IOException;

public class RET extends Instruction implements IndexedInstruction, TypedInstruction {

  public int index;

  public boolean wide;

  @Override
  public final int getIndex() {
    return this.index;
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visitRET(this);
  }

  public RET() {}

  public final void setWide() {
    this.wide = this.index > 255;
    if (this.wide) {
      this.length = 4;
    } else {
      this.length = 2;
    }
  }

  @Override
  public Type getType(ConstantPoolGen constantPoolGen) {
    return ReturnaddressType.NO_TARGET;
  }

  @Override
  public void initFromFile(ByteSequence byteSequence, boolean wide) throws IOException {
    if (this.wide = wide) {
      this.index = byteSequence.readUnsignedShort();
      this.length = 4;
    } else {
      this.index = byteSequence.readUnsignedByte();
      this.length = 2;
    }
  }

  @Override
  public String toString(boolean matches) {
    return "" + super.toString(matches) + " " + this.index;
  }

  @Override
  public final void setIndex(int index) {
    if (index < 0) {
      throw new ClassGenException(("Negative index value: " + index));
    }
    this.index = index;
    this.setWide();
  }

  @Override
  public void dump(DataOutputStream dataOutputStream) throws IOException {
    if (this.wide) {
      dataOutputStream.writeByte(196);
    }
    dataOutputStream.writeByte(this.opcode);
    if (this.wide) {
      dataOutputStream.writeShort(this.index);
    } else {
      dataOutputStream.writeByte(this.index);
    }
  }

  public RET(int value) {
    super((short) 169, (short) 2);
    this.setIndex(value);
  }
}
