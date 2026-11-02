package com.allatori.lib.bcel.classfile;

import com.allatori.lib.bcel.Constants;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public abstract class Attribute implements Cloneable, Node, Serializable {

  public static Map readers = new HashMap();

  public ConstantPool constant_pool;

  public int length;

  public byte tag;

  public int name_index;

  public final byte getTag() {
    return this.tag;
  }

  public Attribute(byte tag, int name_index, int length, ConstantPool constantPool) {
    super();
    this.tag = tag;
    this.name_index = name_index;
    this.length = length;
    this.constant_pool = constantPool;
  }

  @Override
  public Object clone() {
    try {
      return super.clone();
    } catch (CloneNotSupportedException cloneNotSupportedException) {
      cloneNotSupportedException.printStackTrace();
      return (Object) null;
    }
  }

  public static void addAttributeReader(String text, AttributeReader attributeReader) {
    readers.put(text, attributeReader);
  }

  public final void setConstantPool(ConstantPool constantPool) {
    this.constant_pool = constantPool;
  }

  public final int getName_index() {
    return this.name_index;
  }

  public final int getLength() {
    return this.length;
  }

  public final void setName_index(int name_index) {
    this.name_index = name_index;
  }

  public final ConstantPool getConstantPool() {
    return this.constant_pool;
  }

  public static void removeAttributeReader(String text) {
    readers.remove(text);
  }

  public static final Attribute readAttribute(
      DataInputStream dataInputStream, ConstantPool constantPool) throws IOException {
    byte value = -1;
    int value2;
    String bytes =
        ((ConstantUtf8)
                constantPool.getConstant(value2 = dataInputStream.readUnsignedShort(), (byte) 1))
            .getBytes();
    int value3 = dataInputStream.readInt();
    byte value4;
    byte value5 = value4 = 0;
    while (true) {
      if (value5 >= 24) {
        value5 = value;
        break;
      }
      if (bytes.equals(Constants.TEXTS4[value4])) {
        value5 = value4;
        break;
      }
      value5 = ++value4;
    }
    switch (value5) {
      case -1:
        AttributeReader attributeReader;
        if ((attributeReader = (AttributeReader) readers.get(bytes)) != null) {
          return attributeReader.createAttribute(value2, value3, dataInputStream, constantPool);
        }
        return new Unknown(value2, value3, dataInputStream, constantPool);
      case 0:
        return new SourceFile(value2, value3, dataInputStream, constantPool);
      case 1:
        return new ConstantValue(value2, value3, dataInputStream, constantPool);
      case 2:
        return new Code(value2, value3, dataInputStream, constantPool);
      case 3:
        return new ExceptionTable(value2, value3, dataInputStream, constantPool);
      case 4:
        return new LineNumberTable(value2, value3, dataInputStream, constantPool);
      case 5:
        return new LocalVariableTable(value2, value3, dataInputStream, constantPool);
      case 6:
        return new InnerClasses(value2, value3, dataInputStream, constantPool);
      case 7:
        return new Synthetic(value2, value3, dataInputStream, constantPool);
      case 8:
        return new Deprecated(value2, value3, dataInputStream, constantPool);
      case 9:
        return new PMGClass(value2, value3, dataInputStream, constantPool);
      case 10:
        return new Signature(value2, value3, dataInputStream, constantPool);
      case 11:
        return new StackMap(value2, value3, dataInputStream, constantPool);
      case 12:
        return new RuntimeVisibleAnnotations(value2, value3, dataInputStream, constantPool);
      case 13:
        return new RuntimeInvisibleAnnotations(value2, value3, dataInputStream, constantPool);
      case 14:
        return new RuntimeVisibleParameterAnnotations(
            value2, value3, dataInputStream, constantPool);
      case 15:
        return new RuntimeInvisibleParameterAnnotations(
            value2, value3, dataInputStream, constantPool);
      case 16:
        return new AnnotationDefault(value2, value3, dataInputStream, constantPool);
      case 17:
        return new Unknown(value2, value3, dataInputStream, constantPool);
      case 18:
        return new EnclosingMethod(value2, value3, dataInputStream, constantPool);
      case 19:
        return new Unknown(value2, value3, dataInputStream, constantPool);
      case 20:
        return new BootstrapMethods(value2, value3, dataInputStream, constantPool);
      case 21:
        return new MethodParameters(value2, value3, dataInputStream, constantPool);
      case 22:
        return new NestHost(value2, value3, dataInputStream, constantPool);
      case 23:
        return new NestMembers(value2, value3, dataInputStream, constantPool);
      default:
        throw new IllegalStateException("Ooops! default case reached.");
    }
  }

  public final void setLength(int length) {
    this.length = length;
  }

  @Override
  public abstract void accept(Visitor visitor);

  public abstract Attribute copy(ConstantPool constantPool);

  @Override
  public String toString() {
    return ((ConstantUtf8) this.constant_pool.getConstant(this.name_index, (byte) 1)).getBytes();
  }

  public void dump(DataOutputStream dataOutputStream) throws IOException {
    dataOutputStream.writeShort(this.name_index);
    dataOutputStream.writeInt(this.length);
  }
}
