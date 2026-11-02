package com.allatori.lib.asm;

public class Attribute {

  public Attribute nextAttribute;

  public final String type;

  public ByteVector cachedContent;

  public static Attribute read(
      Attribute attribute,
      ClassReader classReader,
      int value,
      int value2,
      char[] values,
      int value3,
      Label[] labels) {
    return attribute.read(classReader, value, value2, values, value3, labels);
  }

  public final int computeAttributesSize(SymbolTable symbolTable) {
    return this.computeAttributesSize(symbolTable, (byte[]) (Object) null, 0, -1, -1);
  }

  public boolean isEnabled() {
    return true;
  }

  @Deprecated
  public Label[] getLabels() {
    return new Label[0];
  }

  public final void putAttributes(SymbolTable symbolTable, ByteVector byteVector) {
    this.putAttributes(symbolTable, (byte[]) (Object) null, 0, -1, -1, byteVector);
  }

  public static int computeAttributesSize(SymbolTable symbolTable, int value, int value2) {
    int value3 = 0;
    if ((value & 4096) != 0 && symbolTable.getCount() < 49) {
      value3 += 6;
      symbolTable.addConstantUtf8("Synthetic");
    }
    if (value2 != 0) {
      value3 += 8;
      symbolTable.addConstantUtf8("Signature");
    }
    if ((value & 131072) != 0) {
      value3 += 6;
      symbolTable.addConstantUtf8("Deprecated");
    }
    return value3;
  }

  public Attribute read(
      ClassReader classReader, int value, int value2, char[] values, int value3, Label[] labels) {
    Attribute attribute = new Attribute(this.type);
    attribute.cachedContent = new ByteVector(classReader.readBytes(value, value2));
    return attribute;
  }

  public final int getAttributeCount() {
    int value = 0;
    Attribute attribute;
    Attribute attribute2 = attribute = this;
    while (attribute2 != null) {
      Attribute attribute3 = attribute.nextAttribute;
      Attribute attribute4 = attribute.nextAttribute;
      value++;
      attribute2 = attribute4;
      attribute = attribute3;
    }
    return value;
  }

  public ByteVector getByteVector(
      ClassWriter classWriter, byte[] values, int value, int value2, int value3) {
    if (this.cachedContent == null) {
      this.cachedContent = this.getByteVector2(classWriter, values, value, value2, value3);
    }
    return this.cachedContent;
  }

  public boolean isEnabled2() {
    return false;
  }

  public final int computeAttributesSize(
      SymbolTable symbolTable, byte[] values, int value, int value2, int value3) {
    ClassWriter classWriter2 = symbolTable.classWriter;
    int value4 = 0;
    Attribute attribute;
    for (Attribute attribute2 = attribute = this; attribute2 != null; attribute2 = attribute) {
      symbolTable.addConstantUtf8(attribute.type);
      value4 += 6 + attribute.getByteVector(classWriter2, values, value, value2, value3).length;
      attribute = attribute.nextAttribute;
    }
    return value4;
  }

  public ByteVector getByteVector2(
      ClassWriter classWriter, byte[] values, int value, int value2, int value3) {
    return this.cachedContent;
  }

  public static byte[] write(
      Attribute attribute,
      ClassWriter classWriter,
      byte[] values,
      int value,
      int value2,
      int value3) {
    ByteVector byteVector;
    ByteVector byteVector2 =
        byteVector = attribute.getByteVector(classWriter, values, value, value2, value3);
    byte[] values2 = new byte[byteVector2.length];
    System.arraycopy(byteVector2.data, 0, values2, 0, byteVector.length);
    return values2;
  }

  public static void putAttributes(
      SymbolTable symbolTable, int value, int value2, ByteVector byteVector) {
    if ((value & 4096) != 0 && symbolTable.getCount() < 49) {
      byteVector.putShort(symbolTable.addConstantUtf8("Synthetic")).putInt(0);
    }
    if (value2 != 0) {
      byteVector.putShort(symbolTable.addConstantUtf8("Signature")).putInt(2).putShort(value2);
    }
    if ((value & 131072) != 0) {
      byteVector.putShort(symbolTable.addConstantUtf8("Deprecated")).putInt(0);
    }
  }

  public static Label readLabel(ClassReader classReader, int value, Label[] labels) {
    return classReader.createLabel(value, labels);
  }

  public Attribute(String type) {
    super();
    this.type = type;
  }

  public final void putAttributes(
      SymbolTable symbolTable,
      byte[] values,
      int value,
      int value2,
      int value3,
      ByteVector byteVector) {
    ClassWriter classWriter2 = symbolTable.classWriter;
    Attribute attribute;
    for (Attribute attribute2 = attribute = this; attribute2 != null; attribute2 = attribute) {
      ByteVector byteVector2 = attribute.getByteVector(classWriter2, values, value, value2, value3);
      byteVector.putShort(symbolTable.addConstantUtf8(attribute.type)).putInt(byteVector2.length);
      byteVector.putByteArray(byteVector2.data, 0, byteVector2.length);
      attribute = attribute.nextAttribute;
    }
  }
}
