package com.allatori.lib.bcel.generic;

import com.allatori.lib.bcel.classfile.ConstantDouble;
import com.allatori.lib.bcel.classfile.ConstantFloat;
import com.allatori.lib.bcel.classfile.ConstantInteger;
import com.allatori.lib.bcel.classfile.ConstantLong;
import com.allatori.lib.bcel.classfile.ConstantUtf8;
import com.allatori.lib.bcel.classfile.ElementValue;
import com.allatori.lib.bcel.classfile.SimpleElementValue;
import java.io.DataOutputStream;
import java.io.IOException;

public class SimpleElementValueGen extends ElementValueGen {

  public int idx;

  public SimpleElementValueGen(int value, int idx, ConstantPoolGen constantPoolGen) {
    super(value, constantPoolGen);
    this.idx = idx;
  }

  @Override
  public String stringifyValue() {
    switch (this.type) {
      case 66:
        return Integer.toString(((ConstantInteger) this.cpGen.getConstant(this.idx)).getBytes());
      case 67:
        return Integer.toString(((ConstantInteger) this.cpGen.getConstant(this.idx)).getBytes());
      case 68:
        return Double.toString(((ConstantDouble) this.cpGen.getConstant(this.idx)).getBytes());
      case 70:
        return Float.toString(((ConstantFloat) this.cpGen.getConstant(this.idx)).getBytes());
      case 73:
        return Integer.toString(((ConstantInteger) this.cpGen.getConstant(this.idx)).getBytes());
      case 74:
        return Long.toString(((ConstantLong) this.cpGen.getConstant(this.idx)).getBytes());
      case 83:
        return Integer.toString(((ConstantInteger) this.cpGen.getConstant(this.idx)).getBytes());
      case 90:
        ConstantInteger constantInteger;
        if ((constantInteger = (ConstantInteger) this.cpGen.getConstant(this.idx)).getBytes()
            == 0) {
          return "false";
        } else if (constantInteger.getBytes() != 0) {
          return "true";
        }
      case 115:
        return ((ConstantUtf8) this.cpGen.getConstant(this.idx)).getBytes();
      default:
        throw new RuntimeException(
            ("SimpleElementValueGen class does not know how to stringify type " + this.type));
    }
  }

  public void setIdx(int idx) {
    this.idx = idx;
  }

  public SimpleElementValueGen(int value, ConstantPoolGen constantPoolGen, double value2) {
    super(value, constantPoolGen);
    this.idx = constantPoolGen.addDouble(value2);
  }

  public SimpleElementValueGen(int value, ConstantPoolGen constantPoolGen, byte value2) {
    super(value, constantPoolGen);
    this.idx = constantPoolGen.addInteger(value2);
  }

  @Override
  public ElementValue getElementValue() {
    return new SimpleElementValue(this.type, this.idx, this.cpGen.getConstantPool());
  }

  public SimpleElementValueGen(int value, ConstantPoolGen constantPoolGen, String text) {
    super(value, constantPoolGen);
    this.idx = constantPoolGen.addUtf8(text);
  }

  public SimpleElementValueGen(int value, ConstantPoolGen constantPoolGen, boolean matches) {
    super(value, constantPoolGen);
    if (matches) {
      this.idx = constantPoolGen.addInteger(1);
    } else {
      this.idx = constantPoolGen.addInteger(0);
    }
  }

  public int getIndex() {
    return this.idx;
  }

  public SimpleElementValueGen(
      SimpleElementValue simpleElementValue, ConstantPoolGen constantPoolGen, boolean matches) {
    super(simpleElementValue.getType(), constantPoolGen);
    if (!matches) {
      this.idx = simpleElementValue.getIndex();
    } else {
      switch (simpleElementValue.getType()) {
        case 66:
          this.idx = constantPoolGen.addInteger(simpleElementValue.getValueByte());
          return;
        case 67:
          this.idx = constantPoolGen.addInteger(simpleElementValue.getValueChar());
          return;
        case 68:
          this.idx = constantPoolGen.addDouble(simpleElementValue.getValueDouble());
          return;
        case 70:
          this.idx = constantPoolGen.addFloat(simpleElementValue.getValueFloat());
          return;
        case 73:
          this.idx = constantPoolGen.addInteger(simpleElementValue.calculateCount());
          return;
        case 74:
          this.idx = constantPoolGen.addLong(simpleElementValue.getValueLong());
          return;
        case 83:
          this.idx = constantPoolGen.addInteger(simpleElementValue.getValueShort());
          return;
        case 90:
          if (simpleElementValue.getValueBoolean()) {
            this.idx = constantPoolGen.addInteger(1);
            return;
          }
          this.idx = constantPoolGen.addInteger(0);
          return;
        case 115:
          this.idx = constantPoolGen.addUtf8(simpleElementValue.createText2());
          return;
        default:
          throw new RuntimeException(
              ("SimpleElementValueGen class does not know how to copy this type " + this.type));
      }
    }
  }

  public SimpleElementValueGen(int value, ConstantPoolGen constantPoolGen, char value2) {
    super(value, constantPoolGen);
    this.idx = constantPoolGen.addInteger(value2);
  }

  public SimpleElementValueGen(int value, ConstantPoolGen constantPoolGen, long value2) {
    super(value, constantPoolGen);
    this.idx = constantPoolGen.addLong(value2);
  }

  public SimpleElementValueGen(int value, ConstantPoolGen constantPoolGen, short value2) {
    super(value, constantPoolGen);
    this.idx = constantPoolGen.addInteger(value2);
  }

  public String getValueString() {
    if (this.type != 115) {
      throw new RuntimeException("Dont call getValueString() on a non STRING ElementValue");
    } else {
      return ((ConstantUtf8) this.cpGen.getConstant(this.idx)).getBytes();
    }
  }

  public SimpleElementValueGen(int value, ConstantPoolGen constantPoolGen, float value2) {
    super(value, constantPoolGen);
    this.idx = constantPoolGen.addFloat(value2);
  }

  public SimpleElementValueGen(int value, ConstantPoolGen constantPoolGen, int value2) {
    super(value, constantPoolGen);
    this.idx = constantPoolGen.addInteger(value2);
  }

  public int getValueInt() {
    if (this.type != 73) {
      throw new RuntimeException("Dont call getValueString() on a non STRING ElementValue");
    } else {
      return ((ConstantInteger) this.cpGen.getConstant(this.idx)).getBytes();
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
        dataOutputStream.writeShort(this.idx);
        return;
      default:
        throw new RuntimeException(
            ("SimpleElementValueGen doesnt know how to write out type " + this.type));
    }
  }
}
