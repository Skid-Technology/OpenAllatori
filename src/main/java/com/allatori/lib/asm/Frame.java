package com.allatori.lib.asm;

public class Frame {

  public static final int NULL = 4194309;

  public static final int STACK_KIND = 25165824;

  public static final int COUNT = 20;

  public static final int UNINITIALIZED_KIND = 12582912;

  public static final int ITEM_ASM_CHAR = 11;

  public static final int DOUBLE = 4194307;

  public static final int ITEM_DOUBLE = 3;

  public int[] counts;

  public static final int COUNT2 = 20;

  public static final int ARRAY_OF = 67108864;

  public static final int SHORT = 4194316;

  public static final int BOOLEAN = 4194313;

  public static final int INTEGER = 4194305;

  public static final int ITEM_INTEGER = 1;

  public static final int UNINITIALIZED_THIS = 4194310;

  public static final int SAME_FRAME_EXTENDED = 251;

  public static final int RESERVED = 128;

  public static final int COUNT3 = 6;

  public static final int KIND_MASK = 62914560;

  public int[] counts2;

  public static final int FULL_FRAME = 255;

  public static final int KIND_SHIFT = 22;

  public int initializationCount;

  public static final int ITEM_NULL = 5;

  public static final int APPEND_FRAME = 252;

  public static final int LONG = 4194308;

  public static final int COUNT4 = 0;

  public static final int FLOAT = 4194306;

  public static final int COUNT5 = 4;

  public static final int COUNT6 = -67108864;

  public static final int REFERENCE_KIND = 8388608;

  public static final int ITEM_ASM_SHORT = 12;

  public static final int COUNT7 = 4;

  public static final int COUNT8 = 4194304;

  public static final int ITEM_ASM_BOOLEAN = 9;

  public static final int ITEM_UNINITIALIZED = 8;

  public static final int COUNT9 = 0;

  public static final int VALUE_MASK = 1048575;

  public int[] counts3;

  public static final int ITEM_OBJECT = 7;

  public static final int FORWARD_UNINITIALIZED_KIND = 16777216;

  public static final int DIM_SHIFT = 26;

  public short value;

  public static final int TOP_IF_LONG_OR_DOUBLE_FLAG = 1048576;

  public static final int COUNT10 = 2;

  public static final int COUNT11 = 6;

  public static final int SAME_LOCALS_1_STACK_ITEM_FRAME_EXTENDED = 247;

  public static final int CHOP_FRAME = 248;

  public static final int BYTE = 4194314;

  public static final int LOCAL_KIND = 20971520;

  public static final int COUNT12 = 2;

  public int[] counts4;

  public static final int CHAR = 4194315;

  public Label owner;

  public short value2;

  public static final int COUNT13 = 4194304;

  public static final int SAME_LOCALS_1_STACK_ITEM_FRAME = 64;

  public int[] counts5;

  public static final int ITEM_ASM_BYTE = 10;

  public static final int COUNT14 = -67108864;

  public static String createText(String text) {
    return "Invalid descriptor fragment: " + text;
  }

  public void processCount(int value3) {
    if (this.value >= value3) {
      this.value = (short) (this.value - value3);
    } else {
      this.value2 = (short) (this.value2 - (value3 - this.value));
      this.value = 0;
    }
  }

  public Frame(Label label) {
    super();
    this.owner = label;
  }

  public static String createText2(String text) {
    return "Invalid descriptor: " + text;
  }

  public void execute(int value2, int value3, Symbol symbol, SymbolTable symbolTable)
      throws ClassNotFoundException {
    switch (value2) {
      case 0:
      case 116:
      case 117:
      case 118:
      case 119:
      case 145:
      case 146:
      case 147:
      case 167:
      case 177:
        return;
      case 1:
        this.processCount3(4194309);
        return;
      case 2:
      case 3:
      case 4:
      case 5:
      case 6:
      case 7:
      case 8:
      case 16:
      case 17:
      case 21:
        this.processCount3(4194305);
        return;
      case 9:
      case 10:
      case 22:
        this.processCount3(4194308);
        this.processCount3(4194304);
        return;
      case 11:
      case 12:
      case 13:
      case 23:
        this.processCount3(4194306);
        return;
      case 14:
      case 15:
      case 24:
        this.processCount3(4194307);
        this.processCount3(4194304);
        return;
      case 18:
        switch (symbol.tag) {
          case 3:
            this.processCount3(4194305);
            return;
          case 4:
            this.processCount3(4194306);
            return;
          case 5:
            this.processCount3(4194308);
            this.processCount3(4194304);
            return;
          case 6:
            this.processCount3(4194307);
            this.processCount3(4194304);
            return;
          case 7:
            this.processCount3(8388608 | symbolTable.addType("java/lang/Class"));
            return;
          case 8:
            this.processCount3(8388608 | symbolTable.addType("java/lang/String"));
            return;
          case 9:
          case 10:
          case 11:
          case 12:
          case 13:
          case 14:
          default:
            throw new AssertionError();
          case 15:
            this.processCount3(8388608 | symbolTable.addType("java/lang/invoke/MethodHandle"));
            return;
          case 16:
            this.processCount3(8388608 | symbolTable.addType("java/lang/invoke/MethodType"));
            return;
          case 17:
            this.push(symbolTable, symbol.value);
            return;
        }
      case 19:
      case 20:
      case 26:
      case 27:
      case 28:
      case 29:
      case 30:
      case 31:
      case 32:
      case 33:
      case 34:
      case 35:
      case 36:
      case 37:
      case 38:
      case 39:
      case 40:
      case 41:
      case 42:
      case 43:
      case 44:
      case 45:
      case 59:
      case 60:
      case 61:
      case 62:
      case 63:
      case 64:
      case 65:
      case 66:
      case 67:
      case 68:
      case 69:
      case 70:
      case 71:
      case 72:
      case 73:
      case 74:
      case 75:
      case 76:
      case 77:
      case 78:
      case 196:
      default:
        throw new IllegalArgumentException();
      case 25:
        this.processCount3(this.getLocal(value3));
        return;
      case 46:
      case 51:
      case 52:
      case 53:
      case 96:
      case 100:
      case 104:
      case 108:
      case 112:
      case 120:
      case 122:
      case 124:
      case 126:
      case 128:
      case 130:
      case 136:
      case 142:
      case 149:
      case 150:
        this.processCount(2);
        this.processCount3(4194305);
        return;
      case 47:
      case 143:
        this.processCount(2);
        this.processCount3(4194308);
        this.processCount3(4194304);
        return;
      case 48:
      case 98:
      case 102:
      case 106:
      case 110:
      case 114:
      case 137:
      case 144:
        this.processCount(2);
        this.processCount3(4194306);
        return;
      case 49:
      case 138:
        this.processCount(2);
        this.processCount3(4194307);
        this.processCount3(4194304);
        return;
      case 50:
        this.processCount(1);
        int value4;
        this.processCount3(
            (value4 = this.calculateCount()) == 4194309 ? value4 : -67108864 + value4);
        return;
      case 54:
      case 56:
      case 58:
        int value5 = this.calculateCount();
        this.setLocal(value3, value5);
        if (value3 > 0) {
          int value6;
          if ((value6 = this.getLocal(value3 - 1)) == 4194308 || value6 == 4194307) {
            this.setLocal(value3 - 1, 4194304);
            return;
          }
          if ((value6 & 62914560) == 20971520 || (value6 & 62914560) == 25165824) {
            this.setLocal(value3 - 1, value6 | 1048576);
            return;
          }
        }
        break;
      case 55:
      case 57:
        this.processCount(1);
        int value7 = this.calculateCount();
        this.setLocal(value3, value7);
        this.setLocal(value3 + 1, 4194304);
        if (value3 > 0) {
          int value8;
          if ((value8 = this.getLocal(value3 - 1)) == 4194308 || value8 == 4194307) {
            this.setLocal(value3 - 1, 4194304);
            return;
          }
          if ((value8 & 62914560) == 20971520 || (value8 & 62914560) == 25165824) {
            this.setLocal(value3 - 1, value8 | 1048576);
            return;
          }
        }
        break;
      case 79:
      case 81:
      case 83:
      case 84:
      case 85:
      case 86:
        this.processCount(3);
        return;
      case 80:
      case 82:
        this.processCount(4);
        return;
      case 87:
      case 153:
      case 154:
      case 155:
      case 156:
      case 157:
      case 158:
      case 170:
      case 171:
      case 172:
      case 174:
      case 176:
      case 191:
      case 194:
      case 195:
      case 198:
      case 199:
        this.processCount(1);
        return;
      case 88:
      case 159:
      case 160:
      case 161:
      case 162:
      case 163:
      case 164:
      case 165:
      case 166:
      case 173:
      case 175:
        this.processCount(2);
        return;
      case 89:
        int value9;
        this.processCount3(value9 = this.calculateCount());
        this.processCount3(value9);
        return;
      case 90:
        int value10 = this.calculateCount();
        int value11 = this.calculateCount();
        this.processCount3(value10);
        this.processCount3(value11);
        this.processCount3(value10);
        return;
      case 91:
        int value12 = this.calculateCount();
        int value13 = this.calculateCount();
        int value14 = this.calculateCount();
        this.processCount3(value12);
        this.processCount3(value14);
        this.processCount3(value13);
        this.processCount3(value12);
        return;
      case 92:
        int value15 = this.calculateCount();
        int value16;
        this.processCount3(value16 = this.calculateCount());
        this.processCount3(value15);
        this.processCount3(value16);
        this.processCount3(value15);
        return;
      case 93:
        int value17 = this.calculateCount();
        int value18 = this.calculateCount();
        int value19 = this.calculateCount();
        this.processCount3(value18);
        this.processCount3(value17);
        this.processCount3(value19);
        this.processCount3(value18);
        this.processCount3(value17);
        return;
      case 94:
        int value20 = this.calculateCount();
        int value21 = this.calculateCount();
        int value22 = this.calculateCount();
        int value23 = this.calculateCount();
        this.processCount3(value21);
        this.processCount3(value20);
        this.processCount3(value23);
        this.processCount3(value22);
        this.processCount3(value21);
        this.processCount3(value20);
        return;
      case 95:
        int value24 = this.calculateCount();
        int value25 = this.calculateCount();
        this.processCount3(value24);
        this.processCount3(value25);
        return;
      case 97:
      case 101:
      case 105:
      case 109:
      case 113:
      case 127:
      case 129:
      case 131:
        this.processCount(4);
        this.processCount3(4194308);
        this.processCount3(4194304);
        return;
      case 99:
      case 103:
      case 107:
      case 111:
      case 115:
        this.processCount(4);
        this.processCount3(4194307);
        this.processCount3(4194304);
        return;
      case 121:
      case 123:
      case 125:
        this.processCount(3);
        this.processCount3(4194308);
        this.processCount3(4194304);
        return;
      case 132:
        this.setLocal(value3, 4194305);
        return;
      case 133:
      case 140:
        this.processCount(1);
        this.processCount3(4194308);
        this.processCount3(4194304);
        return;
      case 134:
        this.processCount(1);
        this.processCount3(4194306);
        return;
      case 135:
      case 141:
        this.processCount(1);
        this.processCount3(4194307);
        this.processCount3(4194304);
        return;
      case 139:
      case 190:
      case 193:
        this.processCount(1);
        this.processCount3(4194305);
        return;
      case 148:
      case 151:
      case 152:
        this.processCount(4);
        this.processCount3(4194305);
        return;
      case 168:
      case 169:
        throw new IllegalArgumentException("JSR/RET are not supported with computeFrames option");
      case 178:
        this.push(symbolTable, symbol.value);
        return;
      case 179:
        this.pop(symbol.value);
        return;
      case 180:
        this.processCount(1);
        this.push(symbolTable, symbol.value);
        return;
      case 181:
        this.pop(symbol.value);
        this.calculateCount();
        return;
      case 182:
      case 183:
      case 184:
      case 185:
        this.pop(symbol.value);
        if (value2 != 184) {
          int value26 = this.calculateCount();
          if (value2 == 183 && symbol.name.charAt(0) == '<') {
            this.processCount2(value26);
          }
        }
        this.push(symbolTable, symbol.value);
        return;
      case 186:
        this.pop(symbol.value);
        this.push(symbolTable, symbol.value);
        return;
      case 187:
        this.processCount3(12582912 | symbolTable.addUninitializedType(symbol.value, value3));
        return;
      case 188:
        this.calculateCount();
        switch (value3) {
          case 4:
            this.processCount3(71303177);
            return;
          case 5:
            this.processCount3(71303179);
            return;
          case 6:
            this.processCount3(71303170);
            return;
          case 7:
            this.processCount3(71303171);
            return;
          case 8:
            this.processCount3(71303178);
            return;
          case 9:
            this.processCount3(71303180);
            return;
          case 10:
            this.processCount3(71303169);
            return;
          case 11:
            this.processCount3(71303172);
            return;
          default:
            throw new IllegalArgumentException();
        }
      case 189:
        String text;
        String text2 = text = symbol.value;
        this.calculateCount();
        if (text2.charAt(0) == '[') {
          this.push(symbolTable, createText3(text));
          return;
        }
        this.processCount3(75497472 | symbolTable.addType(text));
        return;
      case 192:
        String text3;
        String text4 = text3 = symbol.value;
        this.calculateCount();
        if (text4.charAt(0) == '[') {
          this.push(symbolTable, text3);
          return;
        }
        this.processCount3(8388608 | symbolTable.addType(text3));
        return;
      case 197:
        this.processCount(value3);
        this.push(symbolTable, symbol.value);
        return;
    }
  }

  public final void setInputFrameFromApiFormat(
      SymbolTable symbolTable, int value2, Object[] results, int value3, Object[] results2) {
    int value4 = 0;
    int value5;
    for (int value6 = value5 = 0; value6 < value2; value6 = ++value5) {
      int value7 = value5;
      int value8 = value4;
      int[] values = this.counts4;
      value4++;
      values[value8] = getAbstractTypeFromApiFormat(symbolTable, results[value7]);
      if (results[value5] == Opcodes.INTEGER5 || results[value5] == Opcodes.INTEGER) {
        int value9 = 4194304;
        int value10 = value4;
        int[] values2 = this.counts4;
        value4++;
        values2[value10] = value9;
      }
    }
    for (int value11 = value4; value11 < this.counts4.length; value11 = value4) {
      int value12 = 4194304;
      int value13 = value4;
      int[] values3 = this.counts4;
      value4++;
      values3[value13] = value12;
    }
    int value14 = 0;
    int value15;
    for (int value16 = value15 = 0; value16 < value3; value16 = ++value15) {
      if (results2[value15] == Opcodes.INTEGER5 || results2[value15] == Opcodes.INTEGER) {
        value14++;
      }
    }
    this.counts5 = new int[value3 + value14];
    int value17 = 0;
    int value18;
    for (int value19 = value18 = 0; value19 < value3; value19 = ++value18) {
      int value20 = value18;
      int value21 = value17;
      int[] values4 = this.counts5;
      value17++;
      values4[value21] = getAbstractTypeFromApiFormat(symbolTable, results2[value20]);
      if (results2[value18] == Opcodes.INTEGER5 || results2[value18] == Opcodes.INTEGER) {
        int value22 = 4194304;
        int value23 = value17;
        int[] values5 = this.counts5;
        value17++;
        values5[value23] = value22;
      }
    }
    this.initializationCount = this.value = 0;
  }

  public int getLocal(int value) {
    if (this.counts3 != null && value < this.counts3.length) {
      int value2;
      if ((value2 = this.counts3[value]) == 0) {
        value2 = this.counts3[value] = 20971520 | value;
      }
      return value2;
    } else {
      return 20971520 | value;
    }
  }

  public int getConcreteOutputType(int value, int value2) {
    int value3 = value & -67108864;
    int value4;
    if ((value4 = value & 62914560) == 20971520) {
      int value5 = value3 + this.counts4[value & 1048575];
      if ((value & 1048576) != 0 && (value5 == 4194308 || value5 == 4194307)) {
        value5 = 4194304;
      }
      return value5;
    } else {
      if (value4 != 25165824) {
        return value;
      }
      int value6 = value3 + this.counts5[value2 - (value & 1048575)];
      if ((value & 1048576) != 0 && (value6 == 4194308 || value6 == 4194307)) {
        value6 = 4194304;
      }
      return value6;
    }
  }

  public final int getCounts5() {
    return this.counts5.length;
  }

  public void push(SymbolTable symbolTable, String text) {
    int value = text.charAt(0) == '(' ? Type.getReturnTypeOffset(text) : 0;
    int value2;
    if ((value2 = getAbstractTypeFromDescriptor(symbolTable, text, value)) != 0) {
      this.processCount3(value2);
      if (value2 == 4194308 || value2 == 4194307) {
        this.processCount3(4194304);
      }
    }
  }

  public final void setInputFrameFromDescriptor(
      SymbolTable symbolTable, int value, String text, int value2) {
    int value3;
    String text2;
    label39:
    {
      this.counts4 = new int[value2];
      this.counts5 = new int[0];
      value3 = 0;
      if ((value & 8) == 0) {
        if ((value & 262144) == 0) {
          int value4 = value3;
          int[] values = this.counts4;
          value3++;
          text2 = text;
          values[value4] = (int) 8388608 | symbolTable.addType(symbolTable.getClassName());
          break label39;
        }
        int value5 = 4194310;
        int value6 = value3;
        int[] values2 = this.counts4;
        value3++;
        values2[value6] = value5;
      }
      text2 = text;
    }
    Type[] types;
    int value7 = (types = Type.getArgumentTypes(text2)).length;
    int value8;
    for (int value9 = value8 = 0; value9 < value7; value9 = ++value8) {
      Type type = types[value8];
      int abstractTypeFromDescriptor =
          getAbstractTypeFromDescriptor(symbolTable, type.getDescriptor(), 0);
      int value10 = value3;
      int[] values3 = this.counts4;
      value3++;
      values3[value10] = abstractTypeFromDescriptor;
      if (abstractTypeFromDescriptor == 4194308 || abstractTypeFromDescriptor == 4194307) {
        int value11 = 4194304;
        int value12 = value3;
        int[] values4 = this.counts4;
        value3++;
        values4[value12] = value11;
      }
    }
    for (int value13 = value3; value13 < value2; value13 = value3) {
      int value14 = 4194304;
      int value15 = value3;
      int[] values5 = this.counts4;
      value3++;
      values5[value15] = value14;
    }
  }

  public static void putAbstractType(SymbolTable symbolTable, int value2, ByteVector byteVector) {
    int value3;
    if ((value3 = (value2 & -67108864) >> 26) == 0) {
      int value4 = value2 & 1048575;
      switch (value2 & 62914560) {
        case 4194304:
          byteVector.putByte(value4);
          return;
        case 8388608:
          byteVector
              .putByte(7)
              .putShort(symbolTable.getSymbol3(symbolTable.getSymbol2(value4).value).index);
          return;
        case 12582912:
          byteVector.putByte(8).putShort((int) symbolTable.getSymbol2(value4).data);
          return;
        case 16777216:
          byteVector.putByte(8);
          symbolTable.getForwardUninitializedLabel(value4).put(byteVector);
          return;
        default:
          throw new AssertionError();
      }
    } else {
      StringBuilder stringBuilder = new StringBuilder();
      int value5 = value3;
      while (true) {
        int value6 = value5;
        value3--;
        if (value6 <= 0) {
          ByteVector byteVector2;
          if ((value2 & 62914560) == 8388608) {
            byteVector2 = byteVector;
            stringBuilder
                .append('L')
                .append(symbolTable.getSymbol2(value2 & 1048575).value)
                .append(';');
          } else {
            switch (value2 & 1048575) {
              case 1:
                stringBuilder.append('I');
                byteVector2 = byteVector;
                break;
              case 2:
                stringBuilder.append('F');
                byteVector2 = byteVector;
                break;
              case 3:
                stringBuilder.append('D');
                byteVector2 = byteVector;
                break;
              case 4:
                stringBuilder.append('J');
                byteVector2 = byteVector;
                break;
              case 5:
              case 6:
              case 7:
              case 8:
              default:
                throw new AssertionError();
              case 9:
                stringBuilder.append('Z');
                byteVector2 = byteVector;
                break;
              case 10:
                stringBuilder.append('B');
                byteVector2 = byteVector;
                break;
              case 11:
                stringBuilder.append('C');
                byteVector2 = byteVector;
                break;
              case 12:
                stringBuilder.append('S');
                byteVector2 = byteVector;
            }
          }
          byteVector2.putByte(7).putShort(symbolTable.getSymbol3(stringBuilder.toString()).index);
          return;
        }
        value5 = value3;
        stringBuilder.append('[');
      }
    }
  }

  public void processCount2(int value) {
    if (this.counts == null) {
      this.counts = new int[2];
    }
    int value2 = this.counts.length;
    if (this.initializationCount >= value2) {
      int[] values = new int[Math.max(this.initializationCount + 1, 2 * value2)];
      System.arraycopy(this.counts, 0, values, 0, value2);
      this.counts = values;
    }
    this.counts[this.initializationCount++] = value;
  }

  public static boolean merge(SymbolTable symbolTable, int value, int[] values, int value2)
      throws ClassNotFoundException {
    int value3;
    if ((value3 = values[value2]) == value) {
      return false;
    }
    int value4 = value;
    if ((value & 67108863) == 4194309) {
      if (value3 == 4194309) {
        return false;
      }
      value4 = 4194309;
    }
    if (value3 == 0) {
      values[value2] = value4;
      return true;
    }
    int value5;
    int value6;
    if ((value3 & -67108864) == 0 && (value3 & 62914560) != 8388608) {
      if (value3 == 4194309) {
        value5 = (value4 & -67108864) == 0 && (value4 & 62914560) != 8388608 ? 4194304 : value4;
        value6 = value5;
      } else {
        value6 = value5 = 4194304;
      }
    } else {
      if (value4 == 4194309) {
        return false;
      }
      if ((value4 & -4194304) == (value3 & -4194304)) {
        value6 =
            (value3 & 62914560) == 8388608
                ? (value5 =
                    value4 & -67108864
                        | 8388608
                        | symbolTable.addMergedType(value4 & 1048575, value3 & 1048575))
                : (value5 =
                    -67108864 + (value4 & -67108864)
                        | 8388608
                        | symbolTable.addType("java/lang/Object"));
      } else if ((value4 & -67108864) == 0 && (value4 & 62914560) != 8388608) {
        value6 = value5 = 4194304;
      } else {
        int value7;
        if ((value7 = value4 & -67108864) != 0 && (value4 & 62914560) != 8388608) {
          value7 += -67108864;
        }
        int value8;
        if ((value8 = value3 & -67108864) != 0 && (value3 & 62914560) != 8388608) {
          value8 += -67108864;
        }
        value6 =
            value5 = Math.min(value7, value8) | 8388608 | symbolTable.addType("java/lang/Object");
      }
    }
    if (value6 != value3) {
      values[value2] = value5;
      return true;
    } else {
      return false;
    }
  }

  public final void accept(MethodWriter methodWriter) {
    int[] values = this.counts4;
    int value = 0;
    int value2 = 0;
    int value3 = 0;
    while (value3 < values.length) {
      int value4 = values[value3];
      value3 += value4 != 4194308 && value4 != 4194307 ? 1 : 2;
      if (value4 == 4194304) {
        value2++;
      } else {
        value += value2 + 1;
        value2 = 0;
      }
    }
    int[] values2 = this.counts5;
    int value5 = 0;
    int value6;
    for (int value7 = value6 = 0; value7 < values2.length; value7 = value6) {
      int value8 = values2[value6];
      int value9 = value6 + (value8 != 4194308 && value8 != 4194307 ? 1 : 2);
      value5++;
      value6 = value9;
    }
    int value10 = methodWriter.visitFrameStart(this.owner.bytecodeOffset, value, value5);
    int value11 = 0;
    int value12 = value;
    while (true) {
      int value13 = value12;
      value--;
      if (value13 <= 0) {
        int value14 = 0;
        value12 = value5;
        while (true) {
          int value15 = value12;
          value5--;
          if (value15 <= 0) {
            methodWriter.update4();
            return;
          }
          int value16 = values2[value14];
          value14 += value16 != 4194308 && value16 != 4194307 ? 1 : 2;
          methodWriter.processCount6(value10, value16);
          int value17 = value5;
          value10++;
          value12 = value17;
        }
      }
      int value18 = values[value11];
      value11 += value18 != 4194308 && value18 != 4194307 ? 1 : 2;
      methodWriter.processCount6(value10, value18);
      int value19 = value;
      value10++;
      value12 = value19;
    }
  }

  public static int getAbstractTypeFromInternalName(SymbolTable symbolTable, String text) {
    return 8388608 | symbolTable.addType(text);
  }

  public static int getAbstractTypeFromApiFormat(SymbolTable symbolTable, Object result) {
    if (result instanceof Integer) {
      return 4194304 | (Integer) result;
    } else if (result instanceof String) {
      String descriptor = Type.getObjectType((String) result).getDescriptor();
      return getAbstractTypeFromDescriptor(symbolTable, descriptor, 0);
    } else {
      Label label;
      return ((label = (Label) result).value3 & 4) != 0
          ? 12582912 | symbolTable.addUninitializedType("", label.bytecodeOffset)
          : 16777216 | symbolTable.addForwardUninitializedType("", label);
    }
  }

  public void processCount3(int value3) {
    if (this.counts2 == null) {
      this.counts2 = new int[10];
    }
    int value5 = this.counts2.length;
    if (this.value >= value5) {
      int[] values = new int[Math.max(this.value + 1, 2 * value5)];
      System.arraycopy(this.counts2, 0, values, 0, value5);
      this.counts2 = values;
    }
    this.counts2[this.value++] = value3;
    short value6;
    if ((value6 = (short) (this.value2 + this.value)) > this.owner.value4) {
      this.owner.value4 = value6;
    }
  }

  public static String createText3(String text) {
    return "[" + text;
  }

  public int getInitializedType(SymbolTable symbolTable, int value2) {
    int value3;
    if (value2 == 4194310 || (value2 & -4194304) == 12582912 || (value2 & -4194304) == 16777216) {
      for (int value4 = value3 = 0; value4 < this.initializationCount; value4 = ++value3) {
        int value5;
        int value6 = (value5 = this.counts[value3]) & -67108864;
        int value7 = value5 & 62914560;
        int value8 = value5 & 1048575;
        if (value7 == 20971520) {
          value5 = value6 + this.counts4[value8];
          value4 = value2;
        } else {
          if (value7 == 25165824) {
            value5 = value6 + this.counts5[this.counts5.length - value8];
          }
          value4 = value2;
        }
        if (value4 == value5) {
          if (value2 == 4194310) {
            return 8388608 | symbolTable.addType(symbolTable.getClassName());
          }
          return 8388608 | symbolTable.addType(symbolTable.getSymbol2(value2 & 1048575).value);
        }
      }
    }
    return value2;
  }

  public static int getAbstractTypeFromDescriptor(SymbolTable symbolTable, String text, int value) {
    switch (text.charAt(value)) {
      case 'B':
      case 'C':
      case 'I':
      case 'S':
      case 'Z':
        return 4194305;
      case 'D':
        return 4194307;
      case 'E':
      case 'G':
      case 'H':
      case 'K':
      case 'M':
      case 'N':
      case 'O':
      case 'P':
      case 'Q':
      case 'R':
      case 'T':
      case 'U':
      case 'W':
      case 'X':
      case 'Y':
      default:
        throw new IllegalArgumentException(createText2(text.substring(value)));
      case 'F':
        return 4194306;
      case 'J':
        return 4194308;
      case 'L':
        String text2 = text.substring(value + 1, text.length() - 1);
        return 8388608 | symbolTable.addType(text2);
      case 'V':
        return 0;
      case '[':
        int value2 = value + 1;
        String text3 = text;
        while (text3.charAt(value2) == '[') {
          value2++;
          text3 = text;
        }
        int value3;
        int value4;
        switch (text.charAt(value2)) {
          case 'B':
            value3 = 4194314;
            value4 = value2;
            break;
          case 'C':
            value3 = 4194315;
            value4 = value2;
            break;
          case 'D':
            value3 = 4194307;
            value4 = value2;
            break;
          case 'E':
          case 'G':
          case 'H':
          case 'K':
          case 'M':
          case 'N':
          case 'O':
          case 'P':
          case 'Q':
          case 'R':
          case 'T':
          case 'U':
          case 'V':
          case 'W':
          case 'X':
          case 'Y':
          default:
            throw new IllegalArgumentException(createText(text.substring(value2)));
          case 'F':
            value3 = 4194306;
            value4 = value2;
            break;
          case 'I':
            value3 = 4194305;
            value4 = value2;
            break;
          case 'J':
            value3 = 4194308;
            value4 = value2;
            break;
          case 'L':
            String text4 = text.substring(value2 + 1, text.length() - 1);
            value3 = 8388608 | symbolTable.addType(text4);
            value4 = value2;
            break;
          case 'S':
            value3 = 4194316;
            value4 = value2;
            break;
          case 'Z':
            value3 = 4194313;
            value4 = value2;
        }
        return value4 - value << 26 | value3;
    }
  }

  public final void copyFrom(Frame frame) {
    this.counts4 = frame.counts4;
    this.counts5 = frame.counts5;
    this.value2 = 0;
    this.counts3 = frame.counts3;
    this.counts2 = frame.counts2;
    this.value = frame.value;
    this.initializationCount = frame.initializationCount;
    this.counts = frame.counts;
  }

  public final boolean merge(SymbolTable symbolTable, Frame frame, int value3)
      throws ClassNotFoundException {
    boolean matches = false;
    int value4 = this.counts4.length;
    int value5 = this.counts5.length;
    if (frame.counts4 == null) {
      int[] values = new int[value4];
      frame.counts4 = values;
      matches = true;
    }
    int value6;
    for (int value7 = value6 = 0; value7 < value4; value7 = value6) {
      int value8;
      if (this.counts3 != null && value6 < this.counts3.length) {
        int value9;
        if ((value9 = this.counts3[value6]) == 0) {
          value8 = this.counts4[value6];
        } else {
          value8 = this.getConcreteOutputType(value9, value5);
        }
      } else {
        value8 = this.counts4[value6];
      }
      if (this.counts != null) {
        value8 = this.getInitializedType(symbolTable, value8);
      }
      int value10 = value6;
      int[] values2 = frame.counts4;
      int value11 = value8;
      boolean matches2 = matches;
      value6++;
      matches = matches2 | merge(symbolTable, value11, values2, value10);
    }
    if (value3 <= 0) {
      int value12 = this.counts5.length + this.value2;
      if (frame.counts5 == null) {
        int[] values3 = new int[value12 + this.value];
        frame.counts5 = values3;
        matches = true;
      }
      int value13;
      for (int value14 = value13 = 0; value14 < value12; value14 = value13) {
        int value15 = this.counts5[value13];
        if (this.counts != null) {
          value15 = this.getInitializedType(symbolTable, value15);
        }
        int value16 = value13;
        int[] values4 = frame.counts5;
        int value17 = value15;
        boolean matches3 = matches;
        value13++;
        matches = matches3 | merge(symbolTable, value17, values4, value16);
      }
      int value18;
      for (int value19 = value18 = 0; value19 < this.value; value19 = value18) {
        int concreteOutputType = this.getConcreteOutputType(this.counts2[value18], value5);
        if (this.counts != null) {
          concreteOutputType = this.getInitializedType(symbolTable, concreteOutputType);
        }
        int value20 = value12 + value18;
        int[] values5 = frame.counts5;
        int value21 = concreteOutputType;
        boolean matches4 = matches;
        value18++;
        matches = matches4 | merge(symbolTable, value21, values5, value20);
      }
      return matches;
    } else {
      int value22;
      for (int value23 = value22 = 0; value23 < value4; value23 = value22) {
        int value24 = this.counts4[value22];
        int value25 = value22;
        int[] values6 = frame.counts4;
        boolean matches5 = matches;
        value22++;
        matches = matches5 | merge(symbolTable, value24, values6, value25);
      }
      if (frame.counts5 == null) {
        int[] values7 = new int[1];
        frame.counts5 = values7;
        matches = true;
      }
      return matches | merge(symbolTable, value3, frame.counts5, 0);
    }
  }

  public void setLocal(int value, int value2) {
    if (this.counts3 == null) {
      this.counts3 = new int[10];
    }
    int value3 = this.counts3.length;
    if (value >= value3) {
      int[] values;
      int[] values2 = values = new int[Math.max(value + 1, 2 * value3)];
      System.arraycopy(this.counts3, 0, values, 0, value3);
      this.counts3 = values2;
    }
    this.counts3[value] = value2;
  }

  public void pop(String text) {
    char value;
    if ((value = text.charAt(0)) == '(') {
      this.processCount((Type.getArgumentsAndReturnSizes(text) >> 2) - 1);
    } else if (value != 'J' && value != 'D') {
      this.processCount(1);
    } else {
      this.processCount(2);
    }
  }

  public int calculateCount() {
    if (this.value > 0) {
      return this.counts2[--this.value];
    }
    this.value2--;
    return 25165824 | -this.value2;
  }
}
