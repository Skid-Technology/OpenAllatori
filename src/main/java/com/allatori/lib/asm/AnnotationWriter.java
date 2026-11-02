package com.allatori.lib.asm;

public final class AnnotationWriter extends AnnotationVisitor {

  public int count;

  public AnnotationWriter nextAnnotation;

  public final AnnotationWriter previousAnnotation;

  public final int numElementValuePairsOffset;

  public final boolean useNamedValues;

  public final SymbolTable symbolTable;

  public final ByteVector annotation;

  @Override
  public AnnotationVisitor visitAnnotation(String text, String text2) {
    this.count++;
    if (this.useNamedValues) {
      this.annotation.putShort(this.symbolTable.addConstantUtf8(text));
    }
    this.annotation.put12(64, this.symbolTable.addConstantUtf8(text2)).putShort(0);
    return new AnnotationWriter(this.symbolTable, true, this.annotation, null);
  }

  @Override
  public void visit(String text, Object result) {
    this.count++;
    if (this.useNamedValues) {
      this.annotation.putShort(this.symbolTable.addConstantUtf8(text));
    }
    if (result instanceof String) {
      this.annotation.put12(115, this.symbolTable.addConstantUtf8((String) result));
    } else if (result instanceof Byte) {
      this.annotation.put12(66, this.symbolTable.getSymbol((Byte) result).index);
    } else if (result instanceof Boolean) {
      int value = (Boolean) result ? 1 : 0;
      this.annotation.put12(90, this.symbolTable.getSymbol(value).index);
    } else if (result instanceof Character) {
      this.annotation.put12(67, this.symbolTable.getSymbol((Character) result).index);
    } else if (result instanceof Short) {
      this.annotation.put12(83, this.symbolTable.getSymbol((Short) result).index);
    } else if (result instanceof Type) {
      this.annotation.put12(99, this.symbolTable.addConstantUtf8(((Type) result).getDescriptor()));
    } else {
      if (result instanceof byte[]) {
        byte[] values = (byte[]) result;
        this.annotation.put12(91, values.length);
        int value2 = values.length;
        int value3;
        for (int value4 = value3 = 0; value4 < value2; value4 = value3) {
          byte value5 = values[value3];
          SymbolTable symbolTable2 = this.symbolTable;
          ByteVector byteVector = this.annotation;
          value3++;
          byteVector.put12((byte) 66, symbolTable2.getSymbol(value5).index);
        }
      } else if (result instanceof boolean[]) {
        boolean[] matchess = (boolean[]) result;
        this.annotation.put12(91, matchess.length);
        int value6 = matchess.length;
        int value7;
        for (int value8 = value7 = 0; value8 < value6; value8 = value7) {
          boolean matches = matchess[value7];
          ByteVector byteVector2 = this.annotation;
          Symbol symbol = this.symbolTable.getSymbol(matches ? 1 : 0);
          value7++;
          byteVector2.put12((byte) 90, symbol.index);
        }
      } else if (result instanceof short[]) {
        short[] values2 = (short[]) result;
        this.annotation.put12(91, values2.length);
        int value9 = values2.length;
        int value10;
        for (int value11 = value10 = 0; value11 < value9; value11 = value10) {
          short value12 = values2[value10];
          SymbolTable symbolTable3 = this.symbolTable;
          ByteVector byteVector3 = this.annotation;
          value10++;
          byteVector3.put12((byte) 83, symbolTable3.getSymbol(value12).index);
        }
      } else if (result instanceof char[]) {
        char[] values3 = (char[]) result;
        this.annotation.put12(91, values3.length);
        int value13 = values3.length;
        int value14;
        for (int value15 = value14 = 0; value15 < value13; value15 = value14) {
          char value16 = values3[value14];
          SymbolTable symbolTable4 = this.symbolTable;
          ByteVector byteVector4 = this.annotation;
          value14++;
          byteVector4.put12((byte) 67, symbolTable4.getSymbol(value16).index);
        }
      } else if (result instanceof int[]) {
        int[] values4 = (int[]) result;
        this.annotation.put12(91, values4.length);
        int value17 = values4.length;
        int value18;
        for (int value19 = value18 = 0; value19 < value17; value19 = value18) {
          int value20 = values4[value18];
          SymbolTable symbolTable5 = this.symbolTable;
          ByteVector byteVector5 = this.annotation;
          value18++;
          byteVector5.put12((byte) 73, symbolTable5.getSymbol(value20).index);
        }
      } else if (result instanceof long[]) {
        long[] values5 = (long[]) result;
        this.annotation.put12(91, values5.length);
        int value21 = values5.length;
        int value22;
        for (int value23 = value22 = 0; value23 < value21; value23 = value22) {
          long value24 = values5[value22];
          SymbolTable symbolTable6 = this.symbolTable;
          ByteVector byteVector6 = this.annotation;
          value22++;
          byteVector6.put12((byte) 74, symbolTable6.addConstantLong(value24).index);
        }
      } else if (result instanceof float[]) {
        float[] values6 = (float[]) result;
        this.annotation.put12(91, values6.length);
        int value25 = values6.length;
        int value26;
        for (int value27 = value26 = 0; value27 < value25; value27 = value26) {
          float value28 = values6[value26];
          SymbolTable symbolTable7 = this.symbolTable;
          ByteVector byteVector7 = this.annotation;
          value26++;
          byteVector7.put12((byte) 70, symbolTable7.addConstantFloat(value28).index);
        }
      } else if (result instanceof double[]) {
        double[] values7 = (double[]) result;
        this.annotation.put12(91, values7.length);
        int value29 = values7.length;
        int value30;
        for (int value31 = value30 = 0; value31 < value29; value31 = value30) {
          double value32 = values7[value30];
          SymbolTable symbolTable8 = this.symbolTable;
          ByteVector byteVector8 = this.annotation;
          value30++;
          byteVector8.put12((byte) 68, symbolTable8.addConstantDouble(value32).index);
        }
      } else {
        Symbol symbol2 = this.symbolTable.addConstant(result);
        this.annotation.put12(".s.IFJDCS".charAt(symbol2.tag), symbol2.index);
      }
    }
  }

  public static void putParameterAnnotations(
      int value, AnnotationWriter[] annotationWriters, int value2, ByteVector byteVector) {
    int value3 = 1 + 2 * value2;
    int value4;
    for (int value5 = value4 = 0; value5 < value2; value5 = value4) {
      AnnotationWriter annotationWriter = annotationWriters[value4];
      int value6 =
          value3
              + (annotationWriter == null ? 0 : annotationWriter.computeAnnotationsSize(null) - 8);
      value4++;
      value3 = value6;
    }
    byteVector.putShort(value);
    byteVector.putInt(value3);
    byteVector.putByte(value2);
    int value7 = 0;
    for (int value8 = value7; value8 < value2; value8 = ++value7) {
      AnnotationWriter annotationWriter2 = annotationWriters[value7];
      AnnotationWriter annotationWriter3 = null;
      int value9 = 0;
      AnnotationWriter annotationWriter4 = annotationWriter2;
      while (annotationWriter4 != null) {
        AnnotationWriter annotationWriter5 = annotationWriter2;
        value9++;
        annotationWriter5.visitEnd();
        annotationWriter3 = annotationWriter5;
        annotationWriter4 = annotationWriter2 = annotationWriter5.previousAnnotation;
      }
      byteVector.putShort(value9);
      AnnotationWriter annotationWriter6 = annotationWriter3;
      for (AnnotationWriter annotationWriter7 = annotationWriter6;
          annotationWriter7 != null;
          annotationWriter7 = annotationWriter6) {
        byteVector.putByteArray(
            annotationWriter6.annotation.data, 0, annotationWriter6.annotation.length);
        annotationWriter6 = annotationWriter6.nextAnnotation;
      }
    }
  }

  public static void putAnnotations(
      SymbolTable symbolTable,
      AnnotationWriter annotationWriter,
      AnnotationWriter annotationWriter2,
      AnnotationWriter annotationWriter3,
      AnnotationWriter annotationWriter4,
      ByteVector byteVector) {
    if (annotationWriter != null) {
      annotationWriter.putAnnotations(
          symbolTable.addConstantUtf8("RuntimeVisibleAnnotations"), byteVector);
    }
    if (annotationWriter2 != null) {
      annotationWriter2.putAnnotations(
          symbolTable.addConstantUtf8("RuntimeInvisibleAnnotations"), byteVector);
    }
    if (annotationWriter3 != null) {
      annotationWriter3.putAnnotations(
          symbolTable.addConstantUtf8("RuntimeVisibleTypeAnnotations"), byteVector);
    }
    if (annotationWriter4 != null) {
      annotationWriter4.putAnnotations(
          symbolTable.addConstantUtf8("RuntimeInvisibleTypeAnnotations"), byteVector);
    }
  }

  public static AnnotationWriter create(
      SymbolTable symbolTable, String text, AnnotationWriter annotationWriter) {
    ByteVector byteVector;
    (byteVector = new ByteVector()).putShort(symbolTable.addConstantUtf8(text)).putShort(0);
    return new AnnotationWriter(symbolTable, true, byteVector, annotationWriter);
  }

  public int computeAnnotationsSize(String text) {
    if (text != null) {
      this.symbolTable.addConstantUtf8(text);
    }
    int value = 8;
    AnnotationWriter annotationWriter;
    for (AnnotationWriter annotationWriter2 = annotationWriter = this;
        annotationWriter2 != null;
        annotationWriter2 = annotationWriter = annotationWriter.previousAnnotation) {
      value += annotationWriter.annotation.length;
    }
    return value;
  }

  public static AnnotationWriter create(
      SymbolTable symbolTable,
      int value,
      TypePath typePath,
      String text,
      AnnotationWriter annotationWriter) {
    ByteVector byteVector = new ByteVector();
    TypeReference.putTarget(value, byteVector);
    TypePath.put(typePath, byteVector);
    byteVector.putShort(symbolTable.addConstantUtf8(text)).putShort(0);
    return new AnnotationWriter(symbolTable, true, byteVector, annotationWriter);
  }

  @Override
  public void visitEnum(String text, String text2, String text3) {
    this.count++;
    if (this.useNamedValues) {
      this.annotation.putShort(this.symbolTable.addConstantUtf8(text));
    }
    this.annotation
        .put12(101, this.symbolTable.addConstantUtf8(text2))
        .putShort(this.symbolTable.addConstantUtf8(text3));
  }

  public static int computeParameterAnnotationsSize(
      String text, AnnotationWriter[] annotationWriters, int value) {
    int value2 = 7 + 2 * value;
    int value3;
    for (int value4 = value3 = 0; value4 < value; value4 = value3) {
      AnnotationWriter annotationWriter = annotationWriters[value3];
      int value5 =
          value2
              + (annotationWriter == null ? 0 : annotationWriter.computeAnnotationsSize(text) - 8);
      value3++;
      value2 = value5;
    }
    return value2;
  }

  @Override
  public AnnotationVisitor visitArray(String text) {
    this.count++;
    if (this.useNamedValues) {
      this.annotation.putShort(this.symbolTable.addConstantUtf8(text));
    }
    this.annotation.put12(91, 0);
    return new AnnotationWriter(this.symbolTable, false, this.annotation, null);
  }

  public static int computeAnnotationsSize(
      AnnotationWriter annotationWriter,
      AnnotationWriter annotationWriter2,
      AnnotationWriter annotationWriter3,
      AnnotationWriter annotationWriter4) {
    int value = 0;
    if (annotationWriter != null) {
      value += annotationWriter.computeAnnotationsSize("RuntimeVisibleAnnotations");
    }
    if (annotationWriter2 != null) {
      value += annotationWriter2.computeAnnotationsSize("RuntimeInvisibleAnnotations");
    }
    if (annotationWriter3 != null) {
      value += annotationWriter3.computeAnnotationsSize("RuntimeVisibleTypeAnnotations");
    }
    if (annotationWriter4 != null) {
      value += annotationWriter4.computeAnnotationsSize("RuntimeInvisibleTypeAnnotations");
    }
    return value;
  }

  public AnnotationWriter(
      SymbolTable symbolTable2,
      boolean useNamedValues,
      ByteVector byteVector,
      AnnotationWriter annotationWriter) {
    super(589824);
    this.symbolTable = symbolTable2;
    this.useNamedValues = useNamedValues;
    this.annotation = byteVector;
    this.numElementValuePairsOffset = byteVector.length == 0 ? -1 : byteVector.length - 2;
    if ((this.previousAnnotation = annotationWriter) != null) {
      annotationWriter.nextAnnotation = this;
    }
  }

  public void putAnnotations(int value, ByteVector byteVector) {
    int value2 = 2;
    int value3 = 0;
    AnnotationWriter annotationWriter = this;
    AnnotationWriter annotationWriter2 = null;
    AnnotationWriter annotationWriter3 = this;
    while (annotationWriter3 != null) {
      annotationWriter.visitEnd();
      ByteVector byteVector2 = annotationWriter.annotation;
      int value4 = value2;
      AnnotationWriter annotationWriter4 = annotationWriter;
      AnnotationWriter annotationWriter5 = annotationWriter;
      value3++;
      value2 = value4 + byteVector2.length;
      annotationWriter2 = annotationWriter4;
      annotationWriter3 = annotationWriter = annotationWriter5.previousAnnotation;
    }
    byteVector.putShort(value);
    byteVector.putInt(value2);
    byteVector.putShort(value3);
    AnnotationWriter annotationWriter6 = annotationWriter2;
    for (AnnotationWriter annotationWriter7 = annotationWriter6;
        annotationWriter7 != null;
        annotationWriter7 = annotationWriter6) {
      byteVector.putByteArray(
          annotationWriter6.annotation.data, 0, annotationWriter6.annotation.length);
      annotationWriter6 = annotationWriter6.nextAnnotation;
    }
  }

  @Override
  public void visitEnd() {
    if (this.numElementValuePairsOffset != -1) {
      byte[] values = this.annotation.data;
      values[this.numElementValuePairsOffset] = (byte) (this.count >>> 8);
      values[this.numElementValuePairsOffset + 1] = (byte) this.count;
    }
  }
}
