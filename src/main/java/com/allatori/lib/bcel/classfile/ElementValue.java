package com.allatori.lib.bcel.classfile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public abstract class ElementValue {

  public static final int COUNT = 68;

  public static final int COUNT2 = 101;

  public static final int COUNT3 = 83;

  public int type;

  public static final int COUNT4 = 70;

  public static final int COUNT5 = 66;

  public static final int COUNT6 = 67;

  public static final int COUNT7 = 74;

  public static final int COUNT8 = 90;

  public static final int COUNT9 = 91;

  public static final int COUNT10 = 115;

  public static final int COUNT11 = 73;

  public static final int COUNT12 = 64;

  public ConstantPool cpool;

  public static final int COUNT13 = 99;

  public int getType() {
    return this.type;
  }

  public static ElementValue createElementValue(
      DataInputStream dataInputStream, ConstantPool constantPool) throws IOException {
    byte value;
    switch (value = dataInputStream.readByte()) {
      case 64:
        return new AnnotationElementValue(
            64,
            AnnotationEntry.createAnnotationEntry(dataInputStream, constantPool, false),
            constantPool);
      case 65:
      case 69:
      case 71:
      case 72:
      case 75:
      case 76:
      case 77:
      case 78:
      case 79:
      case 80:
      case 81:
      case 82:
      case 84:
      case 85:
      case 86:
      case 87:
      case 88:
      case 89:
      case 92:
      case 93:
      case 94:
      case 95:
      case 96:
      case 97:
      case 98:
      case 100:
      case 102:
      case 103:
      case 104:
      case 105:
      case 106:
      case 107:
      case 108:
      case 109:
      case 110:
      case 111:
      case 112:
      case 113:
      case 114:
      default:
        throw new RuntimeException(("Unexpected element value kind in annotation: " + value));
      case 66:
        return new SimpleElementValue(66, dataInputStream.readUnsignedShort(), constantPool);
      case 67:
        return new SimpleElementValue(67, dataInputStream.readUnsignedShort(), constantPool);
      case 68:
        return new SimpleElementValue(68, dataInputStream.readUnsignedShort(), constantPool);
      case 70:
        return new SimpleElementValue(70, dataInputStream.readUnsignedShort(), constantPool);
      case 73:
        return new SimpleElementValue(73, dataInputStream.readUnsignedShort(), constantPool);
      case 74:
        return new SimpleElementValue(74, dataInputStream.readUnsignedShort(), constantPool);
      case 83:
        return new SimpleElementValue(83, dataInputStream.readUnsignedShort(), constantPool);
      case 90:
        return new SimpleElementValue(90, dataInputStream.readUnsignedShort(), constantPool);
      case 91:
        int value2;
        ElementValue[] elementValues =
            new ElementValue[value2 = dataInputStream.readUnsignedShort()];
        int value3;
        for (int value4 = value3 = 0; value4 < value2; value4 = value3) {
          ElementValue elementValue = createElementValue(dataInputStream, constantPool);
          int value5 = value3;
          value3++;
          elementValues[value5] = elementValue;
        }
        return new ArrayElementValue(91, elementValues, constantPool);
      case 99:
        return new ClassElementValue(99, dataInputStream.readUnsignedShort(), constantPool);
      case 101:
        return new EnumElementValue(
            101,
            dataInputStream.readUnsignedShort(),
            dataInputStream.readUnsignedShort(),
            constantPool);
      case 115:
        return new SimpleElementValue(115, dataInputStream.readUnsignedShort(), constantPool);
    }
  }

  public abstract void dump(DataOutputStream dataOutputStream) throws IOException;

  public String createText() {
    StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(this.getText());
    return stringBuffer.toString();
  }

  public abstract String getText();

  public ElementValue(int type, ConstantPool constantPool) {
    super();
    this.type = type;
    this.cpool = constantPool;
  }
}
