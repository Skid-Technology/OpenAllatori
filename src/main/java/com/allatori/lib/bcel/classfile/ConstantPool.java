package com.allatori.lib.bcel.classfile;

import com.allatori.lib.bcel.Constants;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;

public class ConstantPool implements Cloneable, Node, Serializable {

  public Constant[] constant_pool;

  public int constant_pool_count;

  public String getText(int value, byte value2) {
    return this.constantToString(this.getConstant(value, value2));
  }

  public static final String escape(String text) {
    int size = text.length();
    StringBuffer stringBuffer = new StringBuffer(size + 5);
    char[] values = text.toCharArray();
    int value;
    for (int value2 = value = 0; value2 < size; value2 = ++value) {
      switch (values[value]) {
        case '\b':
          stringBuffer.append("\\b");
          break;
        case '\t':
          stringBuffer.append("\\t");
          break;
        case '\n':
          stringBuffer.append("\\n");
          break;
        case '\r':
          stringBuffer.append("\\r");
          break;
        case '"':
          stringBuffer.append("\\\"");
          break;
        default:
          stringBuffer.append(values[value]);
      }
    }
    return stringBuffer.toString();
  }

  public String createText(int value, byte value2) {
    Constant constant = this.getConstant(value, value2);
    int value3;
    ConstantPool constantPool;
    switch (value2) {
      case 7:
        value3 = ((ConstantClass) constant).getNameIndex();
        constantPool = this;
        break;
      case 8:
        value3 = ((ConstantString) constant).getStringIndex();
        constantPool = this;
        break;
      default:
        throw new RuntimeException(("getConstantString called with illegal tag " + value2));
    }
    return ((ConstantUtf8) constantPool.getConstant(value3, (byte) 1)).getBytes();
  }

  public ConstantPool(DataInputStream dataInputStream) throws IOException, ClassFormatException {
    this.constant_pool_count = dataInputStream.readUnsignedShort();
    this.constant_pool = new Constant[this.constant_pool_count];
    int value;
    for (int value2 = value = 1; value2 < this.constant_pool_count; value2 = ++value) {
      this.constant_pool[value] = Constant.readConstant(dataInputStream);
      byte value3;
      if ((value3 = this.constant_pool[value].getTag()) == 6 || value3 == 5) {
        value++;
      }
    }
  }

  public Constant getConstant(int value, byte value2) throws ClassFormatException {
    Constant constant;
    if ((constant = this.getConstant(value)) == null) {
      throw new ClassFormatException(("Constant pool at index " + value + " is null."));
    } else if (constant.getTag() != value2) {
      throw new ClassFormatException(
          ("Expected class `"
              + Constants.TEXTS7[value2]
              + "' at index "
              + value
              + " and got "
              + constant));
    } else {
      return constant;
    }
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visitConstantPool(this);
  }

  public void setConstant(int value, Constant constant) {
    this.constant_pool[value] = constant;
  }

  public Constant getConstant(int value) throws ClassFormatException {
    if (value < this.constant_pool.length && value >= 0) {
      return this.constant_pool[value];
    } else {
      throw new ClassFormatException(
          ("Invalid constant pool reference: "
              + value
              + ". Constant pool size is: "
              + this.constant_pool.length));
    }
  }

  public int getLength() {
    return this.constant_pool_count;
  }

  public String constantToString(Constant constant) throws IllegalArgumentException {
    byte value;
    switch (value = constant.getTag()) {
      case 1:
        return ((ConstantUtf8) constant).getBytes();
      case 2:
      default:
        throw new RuntimeException(("Unknown constant type " + value));
      case 3:
        return "" + ((ConstantInteger) constant).getBytes();
      case 4:
        return "" + ((ConstantFloat) constant).getBytes();
      case 5:
        return "" + ((ConstantLong) constant).getBytes();
      case 6:
        return "" + ((ConstantDouble) constant).getBytes();
      case 7:
        int nameIndex = ((ConstantClass) constant).getNameIndex();
        return Utility.getText(
            ((ConstantUtf8) this.getConstant(nameIndex, (byte) 1)).getBytes(), false);
      case 8:
        int stringIndex = ((ConstantString) constant).getStringIndex();
        Constant constant2 = this.getConstant(stringIndex, (byte) 1);
        return "\"" + escape(((ConstantUtf8) constant2).getBytes()) + "\"";
      case 9:
      case 10:
      case 11:
        return this.getText(((ConstantCP) constant).getClass_index(), (byte) 7)
            + "."
            + this.getText(((ConstantCP) constant).getName_and_type_index(), (byte) 12);
      case 12:
        return this.getText(((ConstantNameAndType) constant).getName_index(), (byte) 1)
            + " "
            + this.getText(((ConstantNameAndType) constant).getSignature_index(), (byte) 1);
    }
  }

  public Constant[] getConstantPool() {
    return this.constant_pool;
  }

  public void setConstantPool(Constant[] constants) {
    this.constant_pool = constants;
    this.constant_pool_count = constants == null ? 0 : constants.length;
  }

  public ConstantPool copy() {
    ConstantPool constantPool = null;
    try {
      (constantPool = (ConstantPool) this.clone()).constant_pool =
          new Constant[this.constant_pool_count];
      for (int index = 1; index < this.constant_pool_count; index++) {
        if (this.constant_pool[index] != null) {
          constantPool.constant_pool[index] = this.constant_pool[index].copy();
        }
      }
    } catch (CloneNotSupportedException cloneNotSupportedException) {
    }
    return constantPool;
  }

  public void dump(DataOutputStream dataOutputStream) throws IOException {
    int value = 1;
    dataOutputStream.writeShort(this.constant_pool_count);
    for (int index = 1; value < this.constant_pool_count; value = ++index) {
      if (this.constant_pool[index] != null) {
        this.constant_pool[index].dump(dataOutputStream);
      }
    }
  }

  public ConstantPool(Constant[] constants) {
    super();
    this.setConstantPool(constants);
  }
}
