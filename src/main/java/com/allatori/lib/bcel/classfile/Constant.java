package com.allatori.lib.bcel.classfile;

import com.allatori.lib.bcel.util.BCELComparator;
import com.allatori.lib.bcel.util.comparator.ConstantComparator;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;

public abstract class Constant implements Cloneable, Node, Serializable {

  public byte tag;

  public static BCELComparator _cmp = new ConstantComparator();

  @Override
  public int hashCode() {
    return _cmp.hashCode(this);
  }

  public Constant copy() {
    try {
      return (Constant) super.clone();
    } catch (CloneNotSupportedException cloneNotSupportedException) {
      return null;
    }
  }

  public static final Constant readConstant(DataInputStream dataInputStream)
      throws IOException, ClassFormatException {
    byte value;
    switch (value = dataInputStream.readByte()) {
      case 1:
        return new ConstantUtf8(dataInputStream);
      case 2:
      case 13:
      case 14:
      case 17:
      default:
        throw new ClassFormatException(("Invalid byte tag in constant pool: " + value));
      case 3:
        return new ConstantInteger(dataInputStream);
      case 4:
        return new ConstantFloat(dataInputStream);
      case 5:
        return new ConstantLong(dataInputStream);
      case 6:
        return new ConstantDouble(dataInputStream);
      case 7:
        return new ConstantClass(dataInputStream);
      case 8:
        return new ConstantString(dataInputStream);
      case 9:
        return new ConstantFieldref(dataInputStream);
      case 10:
        return new ConstantMethodref(dataInputStream);
      case 11:
        return new ConstantInterfaceMethodref(dataInputStream);
      case 12:
        return new ConstantNameAndType(dataInputStream);
      case 15:
        return new ConstantMethodHandle(dataInputStream);
      case 16:
        return new ConstantMethodType(dataInputStream);
      case 18:
        return new ConstantInvokeDynamic(dataInputStream);
    }
  }

  public abstract void dump(DataOutputStream dataOutputStream) throws IOException;

  public static BCELComparator getComparator() {
    return _cmp;
  }

  @Override
  public Object clone() throws CloneNotSupportedException {
    return super.clone();
  }

  public static void setComparator(BCELComparator bCELComparator) {
    _cmp = bCELComparator;
  }

  public Constant(byte tag) {
    super();
    this.tag = tag;
  }

  @Override
  public boolean equals(Object result) {
    return _cmp.equals(this, result);
  }

  public final byte getTag() {
    return this.tag;
  }

  @Override
  public abstract void accept(Visitor visitor);
}
