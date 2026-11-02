package com.allatori.lib.asm;

public final class SymbolTable {

  public int count;

  public int count2;

  public int constantPoolCount;

  public SymbolTable$Entry[] entries;

  public final ClassReader sourceClassReader;

  public ByteVector constantPool;

  public final ClassWriter classWriter;

  public SymbolTable$Entry[] entrys;

  public String className;

  public int count3;

  public SymbolTable$LabelEntry[] labelEntrys;

  public int count4;

  public ByteVector byteVector;

  public int count5;

  public SymbolTable$LabelEntry[] labelEntrys2;

  public int addConstantUtf8(String text) {
    int value2 = hash(1, text);
    SymbolTable$Entry symbolTable$Entry;
    for (SymbolTable$Entry symbolTable$Entry2 = symbolTable$Entry = this.get(value2);
        symbolTable$Entry2 != null;
        symbolTable$Entry2 = symbolTable$Entry = symbolTable$Entry.next) {
      if (symbolTable$Entry.tag == 1
          && symbolTable$Entry.hashCode == value2
          && symbolTable$Entry.value.equals(text)) {
        return symbolTable$Entry.index;
      }
    }
    this.constantPool.putByte(1).putUTF8(text);
    return this.put(new SymbolTable$Entry(this.constantPoolCount++, 1, text, value2)).index;
  }

  public SymbolTable(ClassWriter classWriter2, ClassReader classReader) {
    super();
    this.classWriter = classWriter2;
    this.sourceClassReader = classReader;
    byte[] values = classReader.classFileBuffer;
    int value = classReader.getItem(1) - 1;
    int value2 = classReader.header - value;
    this.constantPoolCount = classReader.getItemCount();
    this.constantPool = new ByteVector(value2);
    this.constantPool.putByteArray(values, value, value2);
    this.entries = new SymbolTable$Entry[this.constantPoolCount * 2];
    char[] values2 = new char[classReader.getMaxStringLength()];
    byte value3 = 0;
    int value4;
    int value5 = value4 = 1;
    while (value5 < this.constantPoolCount) {
      int value6;
      byte value7;
      switch (value7 = values[(value6 = classReader.getItem(value4)) - 1]) {
        case 1:
          this.addConstantUtf8(value4, classReader.getText4(value4, values2));
          value5 = value4;
          break;
        case 2:
        case 13:
        case 14:
        default:
          throw new IllegalArgumentException();
        case 3:
        case 4:
          this.addConstantIntegerOrFloat(value4, value7, classReader.readInt(value6));
          value5 = value4;
          break;
        case 5:
        case 6:
          this.addConstantLongOrDouble(value4, value7, classReader.readLong(value6));
          value5 = value4;
          break;
        case 7:
        case 8:
        case 16:
        case 19:
        case 20:
          this.addConstantUtf8Reference(value4, value7, classReader.getText2(value6, values2));
          value5 = value4;
          break;
        case 9:
        case 10:
        case 11:
          int item = classReader.getItem(classReader.readUnsignedShort(value6 + 2));
          value5 = value4;
          this.addConstantMemberReference(
              value4,
              value7,
              classReader.getText5(value6, values2),
              classReader.getText2(item, values2),
              classReader.getText2(item + 2, values2));
          break;
        case 12:
          this.addConstantNameAndType(
              value4,
              classReader.getText2(value6, values2),
              classReader.getText2(value6 + 2, values2));
          value5 = value4;
          break;
        case 15:
          int value8;
          int item2 =
              classReader.getItem(
                  classReader.readUnsignedShort(
                      (value8 = classReader.getItem(classReader.readUnsignedShort(value6 + 1)))
                          + 2));
          this.addConstantMethodHandle(
              value4,
              classReader.readByte(value6),
              classReader.getText5(value8, values2),
              classReader.getText2(item2, values2),
              classReader.getText2(item2 + 2, values2),
              classReader.readByte(value8 - 1) == 11);
          value5 = value4;
          break;
        case 17:
        case 18:
          value3 = 1;
          int item3 = classReader.getItem(classReader.readUnsignedShort(value6 + 2));
          this.addConstantDynamicOrInvokeDynamicReference(
              value7,
              value4,
              classReader.getText2(item3, values2),
              classReader.getText2(item3 + 2, values2),
              classReader.readUnsignedShort(value6));
          value5 = value4;
      }
      value5 = value4 = value5 + (value7 != 5 && value7 != 6 ? 1 : 2);
    }
    if (value3 != 0) {
      this.copyBootstrapMethods(classReader, values2);
    }
  }

  public int getConstantPoolCount() {
    return this.constantPoolCount;
  }

  public Symbol getSymbol(String text) {
    return this.addConstantUtf8Reference(16, text);
  }

  public Symbol getSymbol(int value) {
    return this.addConstantIntegerOrFloat(3, value);
  }

  public int addForwardUninitializedType(String text, Label label) {
    int value2 = this.getOrAddLabelEntry(label).index;
    int value3;
    SymbolTable$Entry symbolTable$Entry;
    for (SymbolTable$Entry symbolTable$Entry2 =
            symbolTable$Entry = this.get(value3 = hash(130, text, value2));
        symbolTable$Entry2 != null;
        symbolTable$Entry2 = symbolTable$Entry = symbolTable$Entry.next) {
      if (symbolTable$Entry.tag == 130
          && symbolTable$Entry.hashCode == value3
          && symbolTable$Entry.data == value2
          && symbolTable$Entry.value.equals(text)) {
        return symbolTable$Entry.index;
      }
    }
    return this.addTypeInternal(new SymbolTable$Entry(this.count5, 130, text, value2, value3));
  }

  public void addConstantNameAndType(int value, String text, String text2) {
    this.add(new SymbolTable$Entry(value, 12, text, text2, hash(12, text, text2)));
  }

  public Symbol addConstantDynamicOrInvokeDynamicReference(
      int value2, String text, String text2, int value3) {
    int value4 = hash(value2, text, text2, value3);
    SymbolTable$Entry symbolTable$Entry;
    for (SymbolTable$Entry symbolTable$Entry2 = symbolTable$Entry = this.get(value4);
        symbolTable$Entry2 != null;
        symbolTable$Entry2 = symbolTable$Entry = symbolTable$Entry.next) {
      if (symbolTable$Entry.tag == value2
          && symbolTable$Entry.hashCode == value4
          && symbolTable$Entry.data == value3
          && symbolTable$Entry.name.equals(text)
          && symbolTable$Entry.value.equals(text2)) {
        return symbolTable$Entry;
      }
    }
    this.constantPool.put122(value2, value3, this.addConstantNameAndType(text, text2));
    return this.put(
        new SymbolTable$Entry(this.constantPoolCount++, value2, null, text, text2, value3, value4));
  }

  public int addTypeInternal(SymbolTable$Entry symbolTable$Entry) {
    if (this.entrys == null) {
      this.entrys = new SymbolTable$Entry[16];
    }
    if (this.count5 == this.entrys.length) {
      SymbolTable$Entry[] symbolTable$Entrys = new SymbolTable$Entry[2 * this.entrys.length];
      System.arraycopy(this.entrys, 0, symbolTable$Entrys, 0, this.entrys.length);
      this.entrys = symbolTable$Entrys;
    }
    this.entrys[this.count5++] = symbolTable$Entry;
    return this.put(symbolTable$Entry).index;
  }

  public Symbol addConstantMethodHandle(
      int value2, String text, String text2, String text3, boolean matches) {
    int constantMethodHandleSymbolData = getConstantMethodHandleSymbolData(value2, matches);
    int value3 = hash(15, text, text2, text3, constantMethodHandleSymbolData);
    SymbolTable$Entry symbolTable$Entry;
    for (SymbolTable$Entry symbolTable$Entry2 = symbolTable$Entry = this.get(value3);
        symbolTable$Entry2 != null;
        symbolTable$Entry2 = symbolTable$Entry = symbolTable$Entry.next) {
      if (symbolTable$Entry.tag == 15
          && symbolTable$Entry.hashCode == value3
          && symbolTable$Entry.data == constantMethodHandleSymbolData
          && symbolTable$Entry.owner.equals(text)
          && symbolTable$Entry.name.equals(text2)
          && symbolTable$Entry.value.equals(text3)) {
        return symbolTable$Entry;
      }
    }
    SymbolTable symbolTable;
    if (value2 <= 4) {
      symbolTable = this;
      this.constantPool.put112(15, value2, this.addConstantFieldref(text, text2, text3).index);
    } else {
      symbolTable = this;
      this.constantPool.put112(
          15, value2, this.addConstantMethodref(text, text2, text3, matches).index);
    }
    return symbolTable.put(
        new SymbolTable$Entry(
            this.constantPoolCount++,
            15,
            text,
            text2,
            text3,
            constantMethodHandleSymbolData,
            value3));
  }

  public static int getConstantMethodHandleSymbolData(int value, boolean matches) {
    return value > 4 && matches ? value << 8 : value;
  }

  public Symbol addConstantLongOrDouble(int value, long value2) {
    int value3 = hash(value, value2);
    SymbolTable$Entry symbolTable$Entry;
    for (SymbolTable$Entry symbolTable$Entry2 = symbolTable$Entry = this.get(value3);
        symbolTable$Entry2 != null;
        symbolTable$Entry2 = symbolTable$Entry = symbolTable$Entry.next) {
      if (symbolTable$Entry.tag == value
          && symbolTable$Entry.hashCode == value3
          && symbolTable$Entry.data == value2) {
        return symbolTable$Entry;
      }
    }
    int value4 = this.constantPoolCount;
    this.constantPool.putByte(value).putLong(value2);
    this.constantPoolCount += 2;
    return this.put(new SymbolTable$Entry(value4, value, value2, value3));
  }

  public void addConstantUtf8Reference(int value, int value2, String text) {
    this.add(new SymbolTable$Entry(value, value2, text, hash(value2, text)));
  }

  public Symbol addConstantUtf8Reference(int value2, String text) {
    int value3 = hash(value2, text);
    SymbolTable$Entry symbolTable$Entry;
    for (SymbolTable$Entry symbolTable$Entry2 = symbolTable$Entry = this.get(value3);
        symbolTable$Entry2 != null;
        symbolTable$Entry2 = symbolTable$Entry = symbolTable$Entry.next) {
      if (symbolTable$Entry.tag == value2
          && symbolTable$Entry.hashCode == value3
          && symbolTable$Entry.value.equals(text)) {
        return symbolTable$Entry;
      }
    }
    this.constantPool.put12(value2, this.addConstantUtf8(text));
    return this.put(new SymbolTable$Entry(this.constantPoolCount++, value2, text, value3));
  }

  public ClassReader getSource() {
    return this.sourceClassReader;
  }

  public Symbol addBootstrapMethod(Handle handle, Object... result) {
    ByteVector byteVector2 = this.byteVector;
    if (this.byteVector == null) {
      byteVector2 = this.byteVector = new ByteVector();
    }
    int value;
    int[] values = new int[value = result.length];
    int value2;
    for (int value3 = value2 = 0; value3 < value; value3 = value2) {
      int value4 = this.addConstant(result[value2]).index;
      int value5 = value2;
      value2++;
      values[value5] = value4;
    }
    int value6 = byteVector2.length;
    byteVector2.putShort(
        this.addConstantMethodHandle(
                handle.getTag(),
                handle.getOwner(),
                handle.getName(),
                handle.getDescriptor(),
                handle.isInterface())
            .index);
    byteVector2.putShort(value);
    int value7 = 0;
    for (int value8 = value7; value8 < value; value8 = value7) {
      int value9 = value7;
      ByteVector byteVector3 = byteVector2;
      value7++;
      byteVector3.putShort(values[value9]);
    }
    int value10 = byteVector2.length - value6;
    int value11 = handle.hashCode();
    int value12 = result.length;
    int value13;
    for (int value14 = value13 = 0; value14 < value12; value14 = value13) {
      Object result2 = ((Object[]) result)[value13];
      int value15 = value11;
      value13++;
      value11 = value15 ^ result2.hashCode();
    }
    int value16 = value11 & 2147483647;
    return this.addBootstrapMethod(value6, value10, value16);
  }

  public void processByteVector(ByteVector byteVector) {
    byteVector
        .putShort(this.constantPoolCount)
        .putByteArray(this.constantPool.data, 0, this.constantPool.length);
  }

  public static int hash(int value, String text) {
    return 2147483647 & value + text.hashCode();
  }

  public int addMergedType(int value2, int value3) throws ClassNotFoundException {
    long value4 = value2 < value3 ? value2 | (long) value3 << 32 : value3 | (long) value2 << 32;
    int value5 = hash(131, value2 + value3);
    SymbolTable$Entry symbolTable$Entry;
    for (SymbolTable$Entry symbolTable$Entry2 = symbolTable$Entry = this.get(value5);
        symbolTable$Entry2 != null;
        symbolTable$Entry2 = symbolTable$Entry = symbolTable$Entry.next) {
      if (symbolTable$Entry.tag == 131
          && symbolTable$Entry.hashCode == value5
          && symbolTable$Entry.data == value4) {
        return symbolTable$Entry.count;
      }
    }
    String text = this.entrys[value2].value;
    String text2 = this.entrys[value3].value;
    int value6 = this.addType(this.classWriter.getCommonSuperClass(text, text2));
    this.put(new SymbolTable$Entry(this.count5, 131, value4, value5)).count = value6;
    return value6;
  }

  public Symbol getSymbol2(int value) {
    return this.entrys[value];
  }

  public static int hash(int value, String text, String text2) {
    return 2147483647 & value + text.hashCode() * text2.hashCode();
  }

  public SymbolTable(ClassWriter classWriter2) {
    super();
    this.classWriter = classWriter2;
    this.sourceClassReader = null;
    this.entries = new SymbolTable$Entry[256];
    this.constantPoolCount = 1;
    this.constantPool = new ByteVector();
  }

  public int addType(String text) {
    int value2 = hash(128, text);
    SymbolTable$Entry symbolTable$Entry;
    for (SymbolTable$Entry symbolTable$Entry2 = symbolTable$Entry = this.get(value2);
        symbolTable$Entry2 != null;
        symbolTable$Entry2 = symbolTable$Entry = symbolTable$Entry.next) {
      if (symbolTable$Entry.tag == 128
          && symbolTable$Entry.hashCode == value2
          && symbolTable$Entry.value.equals(text)) {
        return symbolTable$Entry.index;
      }
    }
    return this.addTypeInternal(new SymbolTable$Entry(this.count5, 128, text, value2));
  }

  public int setMajorVersionAndClassName(int count, String className) {
    this.count = count;
    this.className = className;
    return this.getSymbol3(className).index;
  }

  public Symbol addConstantIntegerOrFloat(int value, int value2) {
    int value3 = hash(value, value2);
    SymbolTable$Entry symbolTable$Entry;
    for (SymbolTable$Entry symbolTable$Entry2 = symbolTable$Entry = this.get(value3);
        symbolTable$Entry2 != null;
        symbolTable$Entry2 = symbolTable$Entry = symbolTable$Entry.next) {
      if (symbolTable$Entry.tag == value
          && symbolTable$Entry.hashCode == value3
          && symbolTable$Entry.data == value2) {
        return symbolTable$Entry;
      }
    }
    this.constantPool.putByte(value).putInt(value2);
    return this.put(new SymbolTable$Entry(this.constantPoolCount++, value, value2, value3));
  }

  public void addConstantLongOrDouble(int value, int value2, long value3) {
    this.add(new SymbolTable$Entry(value, value2, value3, hash(value2, value3)));
  }

  public SymbolTable$Entry addConstantMemberReference(
      int value2, String text, String text2, String text3) {
    int value3 = hash(value2, text, text2, text3);
    SymbolTable$Entry symbolTable$Entry;
    for (SymbolTable$Entry symbolTable$Entry2 = symbolTable$Entry = this.get(value3);
        symbolTable$Entry2 != null;
        symbolTable$Entry2 = symbolTable$Entry = symbolTable$Entry.next) {
      if (symbolTable$Entry.tag == value2
          && symbolTable$Entry.hashCode == value3
          && symbolTable$Entry.owner.equals(text)
          && symbolTable$Entry.name.equals(text2)
          && symbolTable$Entry.value.equals(text3)) {
        return symbolTable$Entry;
      }
    }
    this.constantPool.put122(
        value2, this.getSymbol3(text).index, this.addConstantNameAndType(text2, text3));
    return this.put(
        new SymbolTable$Entry(this.constantPoolCount++, value2, text, text2, text3, 0L, value3));
  }

  public void addConstantIntegerOrFloat(int value, int value2, int value3) {
    this.add(new SymbolTable$Entry(value, value2, value3, hash(value2, value3)));
  }

  public static int hash(int value, String text, String text2, String text3) {
    return 2147483647 & value + text.hashCode() * text2.hashCode() * text3.hashCode();
  }

  public static int hash(int value, String text, int value2) {
    return 2147483647 & value + text.hashCode() + value2;
  }

  public int addUninitializedType(String text, int value2) {
    int value3 = hash(129, text, value2);
    SymbolTable$Entry symbolTable$Entry;
    for (SymbolTable$Entry symbolTable$Entry2 = symbolTable$Entry = this.get(value3);
        symbolTable$Entry2 != null;
        symbolTable$Entry2 = symbolTable$Entry = symbolTable$Entry.next) {
      if (symbolTable$Entry.tag == 129
          && symbolTable$Entry.hashCode == value3
          && symbolTable$Entry.data == value2
          && symbolTable$Entry.value.equals(text)) {
        return symbolTable$Entry.index;
      }
    }
    return this.addTypeInternal(new SymbolTable$Entry(this.count5, 129, text, value2, value3));
  }

  public Label getForwardUninitializedLabel(int value) {
    return this.labelEntrys2[(int) this.entrys[value].data].label;
  }

  public int addConstantNameAndType(String text, String text2) {
    int value2 = hash(12, text, text2);
    SymbolTable$Entry symbolTable$Entry;
    for (SymbolTable$Entry symbolTable$Entry2 = symbolTable$Entry = this.get(value2);
        symbolTable$Entry2 != null;
        symbolTable$Entry2 = symbolTable$Entry = symbolTable$Entry.next) {
      if (symbolTable$Entry.tag == 12
          && symbolTable$Entry.hashCode == value2
          && symbolTable$Entry.name.equals(text)
          && symbolTable$Entry.value.equals(text2)) {
        return symbolTable$Entry.index;
      }
    }
    this.constantPool.put122(12, this.addConstantUtf8(text), this.addConstantUtf8(text2));
    return this.put(new SymbolTable$Entry(this.constantPoolCount++, 12, text, text2, value2)).index;
  }

  public Symbol getSymbol2(String text) {
    return this.addConstantUtf8Reference(20, text);
  }

  public void addConstantDynamicOrInvokeDynamicReference(
      int value, int value2, String text, String text2, int value3) {
    int value4 = hash(value, text, text2, value3);
    this.add(new SymbolTable$Entry(value2, value, null, text, text2, value3, value4));
  }

  public Symbol addConstantFieldref(String text, String text2, String text3) {
    return this.addConstantMemberReference(9, text, text2, text3);
  }

  public static int hash(int value, long value2) {
    return 2147483647 & value + (int) value2 + (int) (value2 >>> 32);
  }

  public int getCount() {
    return this.count;
  }

  public Symbol addConstantInvokeDynamic(
      String text, String text2, Handle handle, Object... result) {
    Symbol symbol = this.addBootstrapMethod(handle, result);
    return this.addConstantDynamicOrInvokeDynamicReference(18, text, text2, symbol.index);
  }

  public static int hash(int value, int value2) {
    return 2147483647 & value + value2;
  }

  public Symbol getSymbol3(String text) {
    return this.addConstantUtf8Reference(7, text);
  }

  public SymbolTable$Entry put(SymbolTable$Entry symbolTable$Entry) {
    if (this.count2 > this.entries.length * 3 / 4) {
      int value;
      int value2;
      SymbolTable$Entry[] symbolTable$Entrys =
          new SymbolTable$Entry[value = (value2 = this.entries.length) * 2 + 1];
      int value3;
      for (int value4 = value3 = value2 - 1; value4 >= 0; value4 = --value3) {
        SymbolTable$Entry symbolTable$Entry2;
        SymbolTable$Entry symbolTable$Entry3 = symbolTable$Entry2 = this.entries[value3];
        while (symbolTable$Entry3 != null) {
          int value5 = symbolTable$Entry2.hashCode % value;
          SymbolTable$Entry symbolTable$Entry4 = symbolTable$Entry2.next;
          symbolTable$Entry2.next = symbolTable$Entrys[value5];
          symbolTable$Entry3 = symbolTable$Entry4;
          symbolTable$Entrys[value5] = symbolTable$Entry2;
          symbolTable$Entry2 = symbolTable$Entry4;
        }
      }
      this.entries = symbolTable$Entrys;
    }
    this.count2++;
    int value6 = symbolTable$Entry.hashCode % this.entries.length;
    symbolTable$Entry.next = this.entries[value6];
    return this.entries[value6] = symbolTable$Entry;
  }

  public void copyBootstrapMethods(ClassReader classReader, char[] values) {
    SymbolTable symbolTable;
    byte[] values2 = classReader.classFileBuffer;
    int value;
    int value2;
    int value3 = value2 = classReader.readUnsignedShort((value = classReader.calculateCount()) - 2);
    while (true) {
      if (value3 <= 0) {
        symbolTable = this;
        break;
      }
      String text2 = classReader.getText2(value, values);
      if ("BootstrapMethods".equals(text2)) {
        symbolTable = this;
        this.count4 = classReader.readUnsignedShort(value + 6);
        break;
      }
      int value4 = classReader.readInt(value + 2);
      int value5 = value;
      value2--;
      value = value5 + (byte) 6 + value4;
      value3 = value2;
    }
    if (symbolTable.count4 > 0) {
      int value6 = value + 8;
      int value7 = classReader.readInt(value + 2) - 2;
      this.byteVector = new ByteVector(value7);
      this.byteVector.putByteArray(values2, value6, value7);
      int value8 = value6;
      int value9 = 0;
      for (value3 = value9; value3 < this.count4; value3 = value9) {
        int value10 = value8 - value6;
        int value11 = value8;
        value8 += 2;
        int value12 = classReader.readUnsignedShort(value11);
        int value13 = classReader.readUnsignedShort(value8);
        Object result = classReader.readConst(value12, values);
        value8 += 2;
        int value14 = result.hashCode();
        value3 = value13;
        while (true) {
          int value15 = value3;
          value13--;
          if (value15 <= 0) {
            int value16 = value9;
            value9++;
            this.add(new SymbolTable$Entry(value16, 64, ((long) value10), value14 & 2147483647));
            break;
          }
          int value17 = classReader.readUnsignedShort(value8);
          int value18 = value14;
          value8 += 2;
          value14 = value18 ^ classReader.readConst(value17, values).hashCode();
          value3 = value13;
        }
      }
    }
  }

  public void addConstantUtf8(int value, String text) {
    this.add(new SymbolTable$Entry(value, 1, text, hash(1, text)));
  }

  public Symbol addConstantDynamic(String text, String text2, Handle handle, Object... result) {
    Symbol symbol = this.addBootstrapMethod(handle, result);
    return this.addConstantDynamicOrInvokeDynamicReference(17, text, text2, symbol.index);
  }

  public void addConstantMemberReference(
      int value, int value2, String text, String text2, String text3) {
    this.add(
        new SymbolTable$Entry(
            value, value2, text, text2, text3, 0L, hash(value2, text, text2, text3)));
  }

  public void addConstantMethodHandle(
      int value, int value2, String text, String text2, String text3, boolean matches) {
    int constantMethodHandleSymbolData = getConstantMethodHandleSymbolData(value2, matches);
    int value3 = hash(15, text, text2, text3, constantMethodHandleSymbolData);
    this.add(
        new SymbolTable$Entry(
            value, 15, text, text2, text3, constantMethodHandleSymbolData, value3));
  }

  public Symbol addConstantDouble(double value) {
    return this.addConstantLongOrDouble(6, Double.doubleToRawLongBits(value));
  }

  public Symbol addConstant(Object result) {
    if (result instanceof Integer) {
      return this.getSymbol((Integer) result);
    }
    if (result instanceof Byte) {
      return this.getSymbol(((Byte) result).intValue());
    }
    if (result instanceof Character) {
      return this.getSymbol((Character) result);
    }
    if (result instanceof Short) {
      return this.getSymbol(((Short) result).intValue());
    }
    if (result instanceof Boolean) {
      return this.getSymbol((Boolean) result ? 1 : 0);
    }
    if (result instanceof Float) {
      return this.addConstantFloat((Float) result);
    }
    if (result instanceof Long) {
      return this.addConstantLong((Long) result);
    }
    if (result instanceof Double) {
      return this.addConstantDouble((Double) result);
    }
    if (result instanceof String) {
      return this.getSymbol5((String) result);
    }
    if (result instanceof Type) {
      Type type;
      int value;
      if ((value = (type = (Type) result).getSort()) == 10) {
        return this.getSymbol3(type.getInternalName());
      } else {
        return value == 11
            ? this.getSymbol(type.getDescriptor())
            : this.getSymbol3(type.getDescriptor());
      }
    } else if (result instanceof Handle) {
      Handle handle = (Handle) result;
      return this.addConstantMethodHandle(
          handle.getTag(),
          handle.getOwner(),
          handle.getName(),
          handle.getDescriptor(),
          handle.isInterface());
    } else if (result instanceof ConstantDynamic) {
      ConstantDynamic constantDynamic = (ConstantDynamic) result;
      return this.addConstantDynamic(
          constantDynamic.getName(),
          constantDynamic.getDescriptor(),
          constantDynamic.getBootstrapMethod(),
          constantDynamic.getBootstrapMethodArgumentsUnsafe());
    } else {
      throw new IllegalArgumentException(stringConcat$0(String.valueOf(result)));
    }
  }

  public Symbol addConstantLong(long value) {
    return this.addConstantLongOrDouble(5, value);
  }

  public Symbol addConstantFloat(float value) {
    return this.addConstantIntegerOrFloat(4, Float.floatToRawIntBits(value));
  }

  public Symbol getSymbol4(String text) {
    return this.addConstantUtf8Reference(19, text);
  }

  public String getClassName() {
    return this.className;
  }

  public void processByteVector2(ByteVector byteVector2) {
    if (this.byteVector != null) {
      byteVector2
          .putShort(this.addConstantUtf8("BootstrapMethods"))
          .putInt(this.byteVector.length + 2)
          .putShort(this.count4)
          .putByteArray(this.byteVector.data, 0, this.byteVector.length);
    }
  }

  public Symbol getSymbol5(String text) {
    return this.addConstantUtf8Reference(8, text);
  }

  public SymbolTable$Entry get(int value) {
    return this.entries[value % this.entries.length];
  }

  public SymbolTable$LabelEntry getOrAddLabelEntry(Label label2) {
    if (this.labelEntrys == null) {
      this.labelEntrys = new SymbolTable$LabelEntry[16];
      this.labelEntrys2 = new SymbolTable$LabelEntry[16];
    }
    int value = System.identityHashCode(label2);
    SymbolTable$LabelEntry symbolTable$LabelEntry;
    SymbolTable$LabelEntry symbolTable$LabelEntry2 =
        symbolTable$LabelEntry = this.labelEntrys[value % this.labelEntrys.length];
    while (symbolTable$LabelEntry2 != null && symbolTable$LabelEntry.label != label2) {
      symbolTable$LabelEntry2 = symbolTable$LabelEntry = symbolTable$LabelEntry.next;
    }
    if (symbolTable$LabelEntry != null) {
      return symbolTable$LabelEntry;
    }
    if (this.count3 > this.labelEntrys.length * 3 / 4) {
      int value2;
      int value3;
      SymbolTable$LabelEntry[] symbolTable$LabelEntrys =
          new SymbolTable$LabelEntry[value3 = (value2 = this.labelEntrys.length) * 2 + 1];
      int value4;
      for (int value5 = value4 = value2 - 1; value5 >= 0; value5 = --value4) {
        SymbolTable$LabelEntry symbolTable$LabelEntry3;
        symbolTable$LabelEntry2 = symbolTable$LabelEntry3 = this.labelEntrys[value4];
        while (symbolTable$LabelEntry2 != null) {
          int value6 = System.identityHashCode(symbolTable$LabelEntry3.label) % value3;
          SymbolTable$LabelEntry symbolTable$LabelEntry4 = symbolTable$LabelEntry3.next;
          symbolTable$LabelEntry3.next = symbolTable$LabelEntrys[value6];
          symbolTable$LabelEntry2 = symbolTable$LabelEntry4;
          symbolTable$LabelEntrys[value6] = symbolTable$LabelEntry3;
          symbolTable$LabelEntry3 = symbolTable$LabelEntry4;
        }
      }
      this.labelEntrys = symbolTable$LabelEntrys;
    }
    if (this.count3 == this.labelEntrys2.length) {
      SymbolTable$LabelEntry[] symbolTable$LabelEntrys2 =
          new SymbolTable$LabelEntry[2 * this.labelEntrys2.length];
      System.arraycopy(this.labelEntrys2, 0, symbolTable$LabelEntrys2, 0, this.labelEntrys2.length);
      this.labelEntrys2 = symbolTable$LabelEntrys2;
    }
    SymbolTable$LabelEntry symbolTable$LabelEntry5 =
        new SymbolTable$LabelEntry(this.count3, label2);
    int value7 = value % this.labelEntrys.length;
    symbolTable$LabelEntry5.next = this.labelEntrys[value7];
    this.labelEntrys[value7] = symbolTable$LabelEntry5;
    this.labelEntrys2[this.count3++] = symbolTable$LabelEntry5;
    return symbolTable$LabelEntry5;
  }

  public Symbol addConstantMethodref(String text, String text2, String text3, boolean matches) {
    int value = matches ? 11 : 10;
    return this.addConstantMemberReference(value, text, text2, text3);
  }

  public int computeBootstrapMethodsSize() {
    if (this.byteVector != null) {
      this.addConstantUtf8("BootstrapMethods");
      return 8 + this.byteVector.length;
    } else {
      return 0;
    }
  }

  public static int hash(int value, String text, String text2, int value2) {
    return 2147483647 & value + text.hashCode() * text2.hashCode() * (value2 + 1);
  }

  public void add(SymbolTable$Entry symbolTable$Entry) {
    this.count2++;
    int value = symbolTable$Entry.hashCode % this.entries.length;
    symbolTable$Entry.next = this.entries[value];
    this.entries[value] = symbolTable$Entry;
  }

  public int calculateCount() {
    return this.constantPool.length;
  }

  public Symbol addBootstrapMethod(int value, int value2, int value3) {
    int value4;
    byte[] values = this.byteVector.data;
    SymbolTable$Entry symbolTable$Entry;
    for (SymbolTable$Entry symbolTable$Entry2 = symbolTable$Entry = this.get(value3);
        symbolTable$Entry2 != null;
        symbolTable$Entry2 = symbolTable$Entry = symbolTable$Entry.next) {
      if (symbolTable$Entry.tag == 64 && symbolTable$Entry.hashCode == value3) {
        int value5 = (int) symbolTable$Entry.data;
        byte value6 = 1;
        int value7;
        int value8 = value7 = 0;
        while (true) {
          if (value8 >= value2) {
            value4 = value6;
            break;
          }
          if (values[value + value7] != values[value5 + value7]) {
            value4 = 0;
            break;
          }
          value8 = ++value7;
        }
        if (value4 != 0) {
          this.byteVector.length = value;
          return symbolTable$Entry;
        }
      }
    }
    return this.put(new SymbolTable$Entry(this.count4++, 64, value, value3));
  }

  public static String stringConcat$0(String text) {
    return "value " + text;
  }

  public static int hash(int value, String text, String text2, String text3, int value2) {
    return 2147483647 & value + text.hashCode() * text2.hashCode() * text3.hashCode() * value2;
  }
}
