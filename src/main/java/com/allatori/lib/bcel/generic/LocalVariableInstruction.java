package com.allatori.lib.bcel.generic;

import com.allatori.lib.bcel.util.ByteSequence;
import java.io.DataOutputStream;
import java.io.IOException;

public abstract class LocalVariableInstruction extends Instruction
    implements TypedInstruction, IndexedInstruction {

  public short c_tag;

  public int n = -1;

  public short canon_tag;

  public final boolean wide() {
    return this.n > 255;
  }

  @Override
  public Type getType(ConstantPoolGen constantPoolGen) {
    switch (this.canon_tag) {
      case 21:
      case 54:
        return Type.INT;
      case 22:
      case 55:
        return Type.LONG;
      case 23:
      case 56:
        return Type.FLOAT;
      case 24:
      case 57:
        return Type.DOUBLE;
      case 25:
      case 58:
        return Type.OBJECT;
      case 26:
      case 27:
      case 28:
      case 29:
      case 30:
      case 31:
      case 32:
      case 33:
      case 34:
      case 35:
      case 36:
      case 37:
      case 38:
      case 39:
      case 40:
      case 41:
      case 42:
      case 43:
      case 44:
      case 45:
      case 46:
      case 47:
      case 48:
      case 49:
      case 50:
      case 51:
      case 52:
      case 53:
      default:
        throw new ClassGenException(("Oops: unknown case in switch" + this.canon_tag));
    }
  }

  public short getCanonicalTag() {
    return this.canon_tag;
  }

  public LocalVariableInstruction() {
    this.c_tag = -1;
    this.canon_tag = -1;
  }

  public LocalVariableInstruction(short canon_tag, short c_tag) {
    super();
    this.n = -1;
    this.c_tag = -1;
    this.canon_tag = -1;
    this.canon_tag = canon_tag;
    this.c_tag = c_tag;
  }

  @Override
  public void dump(DataOutputStream dataOutputStream) throws IOException {
    if (this.wide()) {
      dataOutputStream.writeByte(196);
    }
    dataOutputStream.writeByte(this.opcode);
    if (this.length > 1) {
      if (this.wide()) {
        dataOutputStream.writeShort(this.n);
        return;
      }
      dataOutputStream.writeByte(this.n);
    }
  }

  @Override
  public void initFromFile(ByteSequence byteSequence, boolean matches) throws IOException {
    if (matches) {
      this.n = byteSequence.readUnsignedShort();
      this.length = 4;
    } else if ((this.opcode < 21 || this.opcode > 25) && (this.opcode < 54 || this.opcode > 58)) {
      if (this.opcode <= 45) {
        this.n = (this.opcode - 26) % 4;
        this.length = 1;
      } else {
        this.n = (this.opcode - 59) % 4;
        this.length = 1;
      }
    } else {
      this.n = byteSequence.readUnsignedByte();
      this.length = 2;
    }
  }

  public LocalVariableInstruction(short canon_tag, short c_tag, int value3) {
    super(canon_tag, (short) 2);
    this.n = -1;
    this.c_tag = -1;
    this.canon_tag = -1;
    this.c_tag = c_tag;
    this.canon_tag = canon_tag;
    this.setIndex(value3);
  }

  @Override
  public void setIndex(int n) {
    if (n >= 0 && n <= 65535) {
      this.n = n;
      if (n >= 0 && n <= 3) {
        this.opcode = (short) (this.c_tag + n);
        this.length = 1;
      } else {
        this.opcode = this.canon_tag;
        if (this.wide()) {
          this.length = 4;
        } else {
          this.length = 2;
        }
      }
    } else {
      throw new ClassGenException(("Illegal value: " + n));
    }
  }

  @Override
  public final int getIndex() {
    return this.n;
  }

  @Override
  public String toString(boolean matches) {
    return (this.opcode < 26 || this.opcode > 45) && (this.opcode < 59 || this.opcode > 78)
        ? ("" + super.toString(matches) + " " + this.n)
        : super.toString(matches);
  }
}
