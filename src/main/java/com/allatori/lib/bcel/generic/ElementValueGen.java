package com.allatori.lib.bcel.generic;

import com.allatori.lib.bcel.classfile.AnnotationElementValue;
import com.allatori.lib.bcel.classfile.AnnotationEntry;
import com.allatori.lib.bcel.classfile.ArrayElementValue;
import com.allatori.lib.bcel.classfile.ClassElementValue;
import com.allatori.lib.bcel.classfile.ElementValue;
import com.allatori.lib.bcel.classfile.EnumElementValue;
import com.allatori.lib.bcel.classfile.SimpleElementValue;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public abstract class ElementValueGen {

  public static final int ANNOTATION = 64;

  public static final int PRIMITIVE_SHORT = 83;

  public static final int ARRAY = 91;

  public static final int PRIMITIVE_BOOLEAN = 90;

  public static final int PRIMITIVE_FLOAT = 70;

  public static final int PRIMITIVE_DOUBLE = 68;

  public ConstantPoolGen cpGen;

  public static final int ENUM_CONSTANT = 101;

  public static final int PRIMITIVE_CHAR = 67;

  public static final int STRING = 115;

  public int type;

  public static final int PRIMITIVE_INT = 73;

  public static final int PRIMITIVE_LONG = 74;

  public static final int CLASS = 99;

  public static final int PRIMITIVE_BYTE = 66;

  public abstract ElementValue getElementValue();

  public ElementValueGen(int type, ConstantPoolGen constantPoolGen) {
    super();
    this.type = type;
    this.cpGen = constantPoolGen;
  }

  public int getElementValueType() {
    return this.type;
  }

  public ConstantPoolGen getConstantPool() {
    return this.cpGen;
  }

  public static ElementValueGen createElementValueGen(
      DataInputStream dataInputStream, ConstantPoolGen constantPoolGen) throws IOException {
    int value;
    switch (value = dataInputStream.readUnsignedByte()) {
      case 64:
        return new AnnotationElementValueGen(
            64,
            new AnnotationEntryGen(
                AnnotationEntry.createAnnotationEntry(
                    dataInputStream, constantPoolGen.getConstantPool(), true),
                constantPoolGen,
                false),
            constantPoolGen);
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
        return new SimpleElementValueGen(66, dataInputStream.readUnsignedShort(), constantPoolGen);
      case 67:
        return new SimpleElementValueGen(67, dataInputStream.readUnsignedShort(), constantPoolGen);
      case 68:
        return new SimpleElementValueGen(68, dataInputStream.readUnsignedShort(), constantPoolGen);
      case 70:
        return new SimpleElementValueGen(70, dataInputStream.readUnsignedShort(), constantPoolGen);
      case 73:
        return new SimpleElementValueGen(73, dataInputStream.readUnsignedShort(), constantPoolGen);
      case 74:
        return new SimpleElementValueGen(74, dataInputStream.readUnsignedShort(), constantPoolGen);
      case 83:
        return new SimpleElementValueGen(83, dataInputStream.readUnsignedShort(), constantPoolGen);
      case 90:
        return new SimpleElementValueGen(90, dataInputStream.readUnsignedShort(), constantPoolGen);
      case 91:
        int value2;
        ElementValue[] elementValues =
            new ElementValue[value2 = dataInputStream.readUnsignedShort()];
        int value3;
        for (int value4 = value3 = 0; value4 < value2; value4 = value3) {
          ElementValue elementValue =
              ElementValue.createElementValue(dataInputStream, constantPoolGen.getConstantPool());
          int value5 = value3;
          value3++;
          elementValues[value5] = elementValue;
        }
        return new ArrayElementValueGen(91, elementValues, constantPoolGen);
      case 99:
        return new ClassElementValueGen(dataInputStream.readUnsignedShort(), constantPoolGen);
      case 101:
        return new EnumElementValueGen(
            dataInputStream.readUnsignedShort(),
            dataInputStream.readUnsignedShort(),
            constantPoolGen);
      case 115:
        return new SimpleElementValueGen(115, dataInputStream.readUnsignedShort(), constantPoolGen);
    }
  }

  public static ElementValueGen copy(
      ElementValue elementValue, ConstantPoolGen constantPoolGen, boolean matches) {
    switch (elementValue.getType()) {
      case 64:
        return new AnnotationElementValueGen(
            (AnnotationElementValue) elementValue, constantPoolGen, matches);
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
        throw new RuntimeException(("Not implemented yet! (" + elementValue.getType() + ")"));
      case 66:
      case 67:
      case 68:
      case 70:
      case 73:
      case 74:
      case 83:
      case 90:
      case 115:
        return new SimpleElementValueGen(
            (SimpleElementValue) elementValue, constantPoolGen, matches);
      case 91:
        return new ArrayElementValueGen((ArrayElementValue) elementValue, constantPoolGen, matches);
      case 99:
        return new ClassElementValueGen((ClassElementValue) elementValue, constantPoolGen, matches);
      case 101:
        return new EnumElementValueGen((EnumElementValue) elementValue, constantPoolGen, matches);
    }
  }

  public abstract void dump(DataOutputStream dataOutputStream) throws IOException;

  public abstract String stringifyValue();
}
