package com.allatori.lib.asm;

public final class ModuleWriter extends ModuleVisitor {

  public final ByteVector opens;

  public int count;

  public int count2;

  public int count3;

  public int count4;

  public final int moduleFlags;

  public final int moduleNameIndex;

  public final ByteVector provides;

  public final ByteVector exports;

  public final ByteVector packageIndex;

  public final int moduleVersionIndex;

  public int count5;

  public final ByteVector usesIndex;

  public int count6;

  public final SymbolTable symbolTable;

  public final ByteVector requires;

  public int count7;

  @Override
  public void visitRequire(String text, int value, String text2) {
    this.requires
        .putShort(this.symbolTable.getSymbol4(text).index)
        .putShort(value)
        .putShort(text2 == null ? 0 : this.symbolTable.addConstantUtf8(text2));
    this.count2++;
  }

  public ModuleWriter(
      SymbolTable symbolTable2, int moduleNameIndex, int moduleFlags, int moduleVersionIndex) {
    super(589824);
    this.symbolTable = symbolTable2;
    this.moduleNameIndex = moduleNameIndex;
    this.moduleFlags = moduleFlags;
    this.moduleVersionIndex = moduleVersionIndex;
    this.requires = new ByteVector();
    this.exports = new ByteVector();
    this.opens = new ByteVector();
    this.usesIndex = new ByteVector();
    this.provides = new ByteVector();
    this.packageIndex = new ByteVector();
  }

  @Override
  public void processTexts2(String text, int value, String... text2) {
    this.opens.putShort(this.symbolTable.getSymbol2(text).index).putShort(value);
    ModuleWriter moduleWriter;
    if (((String[]) text2) == null) {
      moduleWriter = this;
      this.opens.putShort(0);
    } else {
      this.opens.putShort(((String[]) text2).length);
      String[] texts = (String[]) text2;
      int value2 = texts.length;
      int value3;
      for (int value4 = value3 = 0; value4 < value2; value4 = value3) {
        String text3 = texts[value3];
        SymbolTable symbolTable2 = this.symbolTable;
        ByteVector byteVector = this.opens;
        value3++;
        byteVector.putShort(symbolTable2.getSymbol4(text3).index);
      }
      moduleWriter = this;
    }
    moduleWriter.count7++;
  }

  public int computeAttributesSize() {
    this.symbolTable.addConstantUtf8("Module");
    int value =
        22
            + this.requires.length
            + this.exports.length
            + this.opens.length
            + this.usesIndex.length
            + this.provides.length;
    if (this.count > 0) {
      this.symbolTable.addConstantUtf8("ModulePackages");
      value += 8 + this.packageIndex.length;
    }
    if (this.count3 > 0) {
      SymbolTable symbolTable2 = this.symbolTable;
      value += 8;
      symbolTable2.addConstantUtf8("ModuleMainClass");
    }
    return value;
  }

  @Override
  public void processText3(String text) {
    this.packageIndex.putShort(this.symbolTable.getSymbol2(text).index);
    this.count++;
  }

  @Override
  public void processText(String text) {
    this.count3 = this.symbolTable.getSymbol3(text).index;
  }

  @Override
  public void processTexts(String text, int value, String... text2) {
    this.exports.putShort(this.symbolTable.getSymbol2(text).index).putShort(value);
    ModuleWriter moduleWriter;
    if (((String[]) text2) == null) {
      moduleWriter = this;
      this.exports.putShort(0);
    } else {
      this.exports.putShort(((String[]) text2).length);
      String[] texts = (String[]) text2;
      int value2 = texts.length;
      int value3;
      for (int value4 = value3 = 0; value4 < value2; value4 = value3) {
        String text3 = texts[value3];
        SymbolTable symbolTable2 = this.symbolTable;
        ByteVector byteVector = this.exports;
        value3++;
        byteVector.putShort(symbolTable2.getSymbol4(text3).index);
      }
      moduleWriter = this;
    }
    moduleWriter.count5++;
  }

  @Override
  public void visitProvide(String text, String... text2) {
    this.provides.putShort(this.symbolTable.getSymbol3(text).index);
    this.provides.putShort(((String[]) text2).length);
    String[] texts = (String[]) text2;
    int value = texts.length;
    int value2;
    for (int value3 = value2 = 0; value3 < value; value3 = value2) {
      String text3 = texts[value2];
      SymbolTable symbolTable2 = this.symbolTable;
      ByteVector byteVector = this.provides;
      value2++;
      byteVector.putShort(symbolTable2.getSymbol3(text3).index);
    }
    this.count4++;
  }

  @Override
  public void visitEnd() {}

  public void putAttributes(ByteVector byteVector) {
    int value =
        16
            + this.requires.length
            + this.exports.length
            + this.opens.length
            + this.usesIndex.length
            + this.provides.length;
    byteVector
        .putShort(this.symbolTable.addConstantUtf8("Module"))
        .putInt(value)
        .putShort(this.moduleNameIndex)
        .putShort(this.moduleFlags)
        .putShort(this.moduleVersionIndex)
        .putShort(this.count2)
        .putByteArray(this.requires.data, 0, this.requires.length)
        .putShort(this.count5)
        .putByteArray(this.exports.data, 0, this.exports.length)
        .putShort(this.count7)
        .putByteArray(this.opens.data, 0, this.opens.length)
        .putShort(this.count6)
        .putByteArray(this.usesIndex.data, 0, this.usesIndex.length)
        .putShort(this.count4)
        .putByteArray(this.provides.data, 0, this.provides.length);
    if (this.count > 0) {
      byteVector
          .putShort(this.symbolTable.addConstantUtf8("ModulePackages"))
          .putInt(2 + this.packageIndex.length)
          .putShort(this.count)
          .putByteArray(this.packageIndex.data, 0, this.packageIndex.length);
    }
    if (this.count3 > 0) {
      byteVector
          .putShort(this.symbolTable.addConstantUtf8("ModuleMainClass"))
          .putInt(2)
          .putShort(this.count3);
    }
  }

  @Override
  public void processText2(String text) {
    this.usesIndex.putShort(this.symbolTable.getSymbol3(text).index);
    this.count6++;
  }

  public int getAttributeCount() {
    return 1 + (this.count > 0 ? 1 : 0) + (this.count3 > 0 ? 1 : 0);
  }
}
