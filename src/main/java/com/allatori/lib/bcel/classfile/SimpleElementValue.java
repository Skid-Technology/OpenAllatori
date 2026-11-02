package com.allatori.lib.bcel.classfile;

import java.io.DataOutputStream;
import java.io.IOException;

public class SimpleElementValue extends ElementValue {

  public int index;

  public short getValueShort() {
    if (this.type != 83) {
      throw new RuntimeException("Dont call getValueShort() on a non SHORT ElementValue");
    } else {
      return (short) ((ConstantInteger) this.cpool.getConstant(this.getIndex())).getBytes();
    }
  }

  public SimpleElementValue(int value, int index, ConstantPool constantPool) {
    super(value, constantPool);
    this.index = index;
  }

  public int calculateCount() {
    if (this.type != 73) {
      throw new RuntimeException("Dont call getValueString() on a non STRING ElementValue");
    } else {
      return ((ConstantInteger) this.cpool.getConstant(this.getIndex(), (byte) 3)).getBytes();
    }
  }

  public float getValueFloat() {
    if (this.type != 70) {
      throw new RuntimeException("Dont call getValueFloat() on a non FLOAT ElementValue");
    } else {
      return ((ConstantFloat) this.cpool.getConstant(this.getIndex())).getBytes();
    }
  }

  public boolean getValueBoolean() {
    if (this.type != 90) {
      throw new RuntimeException("Dont call getValueBoolean() on a non BOOLEAN ElementValue");
    } else {
      return ((ConstantInteger) this.cpool.getConstant(this.getIndex())).getBytes() != 0;
    }
  }

  public int getIndex() {
    return this.index;
  }

  public double getValueDouble() {
    if (this.type != 68) {
      throw new RuntimeException("Dont call getValueDouble() on a non DOUBLE ElementValue");
    } else {
      return ((ConstantDouble) this.cpool.getConstant(this.getIndex())).getBytes();
    }
  }

  public void setIndex(int index) {
    this.index = index;
  }

  public long getValueLong() {
    if (this.type != 74) {
      throw new RuntimeException("Dont call getValueLong() on a non LONG ElementValue");
    } else {
      return ((ConstantLong) this.cpool.getConstant(this.getIndex())).getBytes();
    }
  }

  @Override
  public void dump(DataOutputStream dataOutputStream) throws IOException {
    dataOutputStream.writeByte(this.type);
    switch (this.type) {
      case 66:
      case 67:
      case 68:
      case 70:
      case 73:
      case 74:
      case 83:
      case 90:
      case 115:
        dataOutputStream.writeShort(this.getIndex());
        return;
      default:
        throw new RuntimeException(
            ("SimpleElementValue doesnt know how to write out type " + this.type));
    }
  }

  public String createText2() {
    if (this.type != 115) {
      throw new RuntimeException("Dont call getValueString() on a non STRING ElementValue");
    } else {
      return ((ConstantUtf8) this.cpool.getConstant(this.getIndex(), (byte) 1)).getBytes();
    }
  }

  public char getValueChar() {
    if (this.type != 67) {
      throw new RuntimeException("Dont call getValueChar() on a non CHAR ElementValue");
    } else {
      return (char)
          ((ConstantInteger) this.cpool.getConstant(this.getIndex(), (byte) 3)).getBytes();
    }
  }

  @Override
  public String getText() {
    switch (this.type) {
      case 66:
        return Integer.toString(
            ((ConstantInteger) this.cpool.getConstant(this.getIndex(), (byte) 3)).getBytes());
      case 67:
        return String.valueOf(
            (char)
                ((ConstantInteger) this.cpool.getConstant(this.getIndex(), (byte) 3)).getBytes());
      case 68:
        return Double.toString(
            ((ConstantDouble) this.cpool.getConstant(this.getIndex(), (byte) 6)).getBytes());
      case 70:
        return Float.toString(
            ((ConstantFloat) this.cpool.getConstant(this.getIndex(), (byte) 4)).getBytes());
      case 73:
        return Integer.toString(
            ((ConstantInteger) this.cpool.getConstant(this.getIndex(), (byte) 3)).getBytes());
      case 74:
        return Long.toString(
            ((ConstantLong) this.cpool.getConstant(this.getIndex(), (byte) 5)).getBytes());
      case 83:
        return Integer.toString(
            ((ConstantInteger) this.cpool.getConstant(this.getIndex(), (byte) 3)).getBytes());
      case 90:
        ConstantInteger constantInteger;
        if ((constantInteger = (ConstantInteger) this.cpool.getConstant(this.getIndex(), (byte) 3))
                .getBytes()
            == 0) {
          return "false";
        } else if (constantInteger.getBytes() != 0) {
          return "true";
        }
      case 115:
        return ((ConstantUtf8) this.cpool.getConstant(this.getIndex(), (byte) 1)).getBytes();
      default:
        throw new RuntimeException(
            ("SimpleElementValue class does not know how to stringify type " + this.type));
    }
  }

  public byte getValueByte() {
    if (this.type != 66) {
      throw new RuntimeException("Dont call getValueByte() on a non BYTE ElementValue");
    } else {
      return (byte)
          ((ConstantInteger) this.cpool.getConstant(this.getIndex(), (byte) 3)).getBytes();
    }
  }
}
