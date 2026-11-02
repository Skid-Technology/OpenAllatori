package com.allatori.lib.asm;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ClassReader {

  public final String[] constantUtf8Values;

  public final byte[] classFileBuffer;

  public final int[] cpInfoOffsets;

  public final int header;

  public static final int SKIP_FRAMES = 4;

  public final int maxStringLength;

  public static final int EXPAND_FRAMES = 8;

  public static final int SKIP_DEBUG = 2;

  public static final int INPUT_STREAM_DATA_CHUNK_SIZE = 4096;

  public static final int SKIP_CODE = 1;

  public final ConstantDynamic[] constantDynamicValues;

  public static final int EXPAND_ASM_INSNS = 256;

  public static final int MAX_BUFFER_SIZE = 1048576;

  public final int[] bootstrapMethodOffsets;

  @Deprecated public final byte[] b;

  public int readMethod(ClassVisitor classVisitor, Context context, int value)
      throws ClassNotFoundException {
    char[] values = context.charBuffer;
    int value2 = value;
    context.count7 = this.readUnsignedShort(value2);
    context.text2 = this.getText2(value2 + 2, values);
    int value3 = value2 + 4;
    value2 += 6;
    context.text = this.getText2(value3, values);
    int value4 = 0;
    int value5 = 0;
    String[] texts = null;
    byte value6 = 0;
    int value7 = 0;
    int value8 = 0;
    int value9 = 0;
    int value10 = 0;
    int value11 = 0;
    int value12 = 0;
    int value13 = 0;
    int value14 = 0;
    int value15 = 0;
    Attribute attribute = null;
    int value16 = value2;
    value2 += 2;
    int value17 = this.readUnsignedShort(value16);
    int value18 = value17;
    while (true) {
      int value19 = value18;
      value17--;
      if (value19 <= 0) {
        String text3;
        String[] texts2;
        if (value7 == 0) {
          text3 = null;
          texts2 = texts;
        } else {
          text3 = this.getText4(value7, values);
          texts2 = texts;
        }
        MethodVisitor methodVisitor;
        if ((methodVisitor =
                classVisitor.visitMethod(
                    context.count7, context.text2, context.text, text3, texts2))
            == null) {
          return value2;
        }
        if (methodVisitor instanceof MethodWriter) {
          MethodWriter methodWriter;
          MethodWriter methodWriter2 = methodWriter = (MethodWriter) methodVisitor;
          ClassReader classReader;
          byte value20;
          if ((context.count7 & 131072) != 0) {
            value20 = 1;
            classReader = this;
          } else {
            value20 = 0;
            classReader = this;
          }
          if (methodWriter2.canCopyMethodAttributes(
              this,
              value6 != 0,
              value20 != 0,
              classReader.readUnsignedShort(value + 4),
              value7,
              value5)) {
            methodWriter.processCount7(value, value2 - value);
            return value2;
          }
        }
        if (value15 != 0 && (context.count2 & 2) == 0) {
          int value21 = this.readByte(value15);
          int value22 = value15 + 1;
          value18 = value21;
          while (true) {
            int value23 = value18;
            value21--;
            if (value23 <= 0) {
              break;
            }
            String text22 = this.getText2(value22, values);
            int value24 = value22;
            MethodVisitor methodVisitor2 = methodVisitor;
            value22 += 4;
            methodVisitor2.visitParameter(text22, this.readUnsignedShort(value24 + (byte) 2));
            value18 = value21;
          }
        }
        if (value14 != 0) {
          AnnotationVisitor annotationVisitor = methodVisitor.visitAnnotationDefault();
          this.readElementValue(annotationVisitor, value14, null, values);
          if (annotationVisitor != null) {
            annotationVisitor.visitEnd();
          }
        }
        if (value8 != 0) {
          int value25 = this.readUnsignedShort(value8);
          int value26 = value8 + 2;
          value18 = value25;
          while (true) {
            int value27 = value18;
            value25--;
            if (value27 <= 0) {
              break;
            }
            int value28 = value26;
            value26 += 2;
            String text23 = this.getText2(value28, values);
            value26 =
                this.readElementValues(
                    methodVisitor.visitAnnotation(text23, true), value26, true, values);
            value18 = value25;
          }
        }
        if (value9 != 0) {
          int value29 = this.readUnsignedShort(value9);
          int value30 = value9 + 2;
          value18 = value29;
          while (true) {
            int value31 = value18;
            value29--;
            if (value31 <= 0) {
              break;
            }
            int value32 = value30;
            value30 += 2;
            String text24 = this.getText2(value32, values);
            value30 =
                this.readElementValues(
                    methodVisitor.visitAnnotation(text24, false), value30, true, values);
            value18 = value29;
          }
        }
        if (value12 != 0) {
          int value33 = this.readUnsignedShort(value12);
          int value34 = value12 + 2;
          value18 = value33;
          while (true) {
            int value35 = value18;
            value33--;
            if (value35 <= 0) {
              break;
            }
            int value36;
            int value37 = value36 = this.readTypeAnnotationTarget(context, value34);
            value36 += 2;
            String text25 = this.getText2(value37, values);
            value34 =
                this.readElementValues(
                    methodVisitor.createAnnotationVisitor2(
                        context.count6, context.currentTypeAnnotationTargetPath, text25, true),
                    value36,
                    true,
                    values);
            value18 = value33;
          }
        }
        if (value13 != 0) {
          int value38 = this.readUnsignedShort(value13);
          int value39 = value13 + 2;
          value18 = value38;
          while (true) {
            int value40 = value18;
            value38--;
            if (value40 <= 0) {
              break;
            }
            int value41;
            int value42 = value41 = this.readTypeAnnotationTarget(context, value39);
            value41 += 2;
            String text26 = this.getText2(value42, values);
            value39 =
                this.readElementValues(
                    methodVisitor.createAnnotationVisitor2(
                        context.count6, context.currentTypeAnnotationTargetPath, text26, false),
                    value41,
                    true,
                    values);
            value18 = value38;
          }
        }
        if (value10 != 0) {
          this.readParameterAnnotations(methodVisitor, context, value10, true);
        }
        if (value11 != 0) {
          this.readParameterAnnotations(methodVisitor, context, value11, false);
        }
        Attribute attribute2 = attribute;
        while (attribute2 != null) {
          Attribute attribute3 = attribute.nextAttribute;
          attribute.nextAttribute = null;
          attribute2 = attribute3;
          methodVisitor.visitAttribute(attribute);
          attribute = attribute3;
        }
        if (value4 != 0) {
          methodVisitor.visitCode();
          this.readCode(methodVisitor, context, value4);
        }
        methodVisitor.visitEnd();
        return value2;
      }
      int value43;
      label175:
      {
        String text27 = this.getText2(value2, values);
        int value44 = value2;
        value2 += 6;
        value43 = this.readInt(value44 + (byte) 2);
        if ("Code".equals(text27)) {
          if ((context.count2 & 1) == 0) {
            value4 = value2;
            value18 = value2;
            break label175;
          }
        } else if ("Exceptions".equals(text27)) {
          value5 = value2;
          texts = new String[this.readUnsignedShort(value5)];
          int value45 = value5 + 2;
          int value46;
          for (int value47 = value46 = 0; value47 < texts.length; value47 = ++value46) {
            String text5 = this.getText5(value45, values);
            int value48 = value46;
            String[] texts3 = texts;
            value45 += 2;
            texts3[value48] = text5;
          }
        } else {
          if ("Signature".equals(text27)) {
            value7 = this.readUnsignedShort(value2);
            value18 = value2;
            break label175;
          }
          if ("Deprecated".equals(text27)) {
            value18 = value2;
            context.count7 |= 131072;
            break label175;
          }
          if ("RuntimeVisibleAnnotations".equals(text27)) {
            value8 = value2;
            value18 = value2;
            break label175;
          }
          if ("RuntimeVisibleTypeAnnotations".equals(text27)) {
            value12 = value2;
            value18 = value2;
            break label175;
          }
          if ("AnnotationDefault".equals(text27)) {
            value14 = value2;
            value18 = value2;
            break label175;
          }
          if ("Synthetic".equals(text27)) {
            value6 = 1;
            value18 = value2;
            context.count7 |= 4096;
            break label175;
          }
          if ("RuntimeInvisibleAnnotations".equals(text27)) {
            value9 = value2;
            value18 = value2;
            break label175;
          }
          if ("RuntimeInvisibleTypeAnnotations".equals(text27)) {
            value13 = value2;
            value18 = value2;
            break label175;
          }
          if ("RuntimeVisibleParameterAnnotations".equals(text27)) {
            value10 = value2;
            value18 = value2;
            break label175;
          }
          if ("RuntimeInvisibleParameterAnnotations".equals(text27)) {
            value11 = value2;
            value18 = value2;
            break label175;
          }
          if ("MethodParameters".equals(text27)) {
            value15 = value2;
            value18 = value2;
            break label175;
          }
          Attribute attribute4 =
              this.readAttribute(
                  context.attributePrototypes, text27, value2, value43, values, -1, null);
          attribute4.nextAttribute = attribute;
          attribute = attribute4;
        }
        value18 = value2;
      }
      value2 = value18 + value43;
      value18 = value17;
    }
  }

  public static byte[] readStream(InputStream inputStream, boolean matches) throws IOException {
    if (inputStream == null) {
      throw new IOException("Class not found");
    }
    int value = computeBufferSize(inputStream);
    byte[] values;
    try {
      ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
      label125:
      {
        byte[] values2;
        try {
          byte[] values3 = new byte[value];
          int value2 = 0;
          InputStream inputStream2 = inputStream;
          int value3;
          while ((value3 = inputStream2.read(values3, 0, value)) != -1) {
            value2++;
            inputStream2 = inputStream;
            byteArrayOutputStream.write(values3, 0, value3);
          }
          byteArrayOutputStream.flush();
          if (value2 == 1) {
            values = values3;
            break label125;
          }
          values2 = byteArrayOutputStream.toByteArray();
        } catch (Throwable throwable) {
          try {
            byteArrayOutputStream.close();
          } catch (Throwable throwable2) {
            throw throwable;
          }
          throw throwable;
        }
        byteArrayOutputStream.close();
        return values2;
      }
      byteArrayOutputStream.close();
    } finally {
      if (matches) {
        inputStream.close();
      }
    }
    return values;
  }

  public int getHeader() {
    return this.readUnsignedShort(this.header);
  }

  public String readUtf(int value, int value2, char[] values) {
    int value3 = value;
    int value4 = value + value2;
    int value5 = 0;
    byte[] values2 = this.classFileBuffer;
    while (value3 < value4) {
      byte value6;
      byte value7 = value6 = values2[value3];
      value3++;
      if ((value7 & 128) == 0) {
        int value8 = value6 & 127;
        int value9 = value5;
        value5++;
        values[value9] = (char) value8;
      } else if ((value6 & 224) == 192) {
        int value10 = value6 & 31;
        int value11 = value5;
        value5++;
        int value12 = value10 << 6;
        int value13 = values2[value3] & 63;
        value3++;
        values[value11] = (char) (value12 + value13);
      } else {
        int value14 = value6 & 15;
        int value15 = value5;
        value5++;
        int value16 = value14 << 12;
        int value17 = values2[value3] & 63;
        int value18 = value16;
        value3++;
        value16 = value18 + (value17 << 6);
        int value19 = values2[value3] & 63;
        int value20 = value16;
        value3++;
        values[value15] = (char) (value20 + value19);
      }
    }
    return new String(values, 0, value5);
  }

  public int[] readBootstrapMethodsAttribute(int value) {
    char[] values = new char[value];
    int value2;
    int value3;
    for (int value4 = value2 = this.readUnsignedShort((value3 = this.calculateCount()) - 2);
        value4 > 0;
        value4 = value2) {
      String text2 = this.getText2(value3, values);
      int value5 = value3;
      value3 += 6;
      int value6 = this.readInt(value5 + (byte) 2);
      if ("BootstrapMethods".equals(text2)) {
        int[] values2 = new int[this.readUnsignedShort(value3)];
        int value7 = value3 + 2;
        int value8;
        for (value4 = value8 = 0; value4 < values2.length; value4 = value8) {
          value4 = values2[value8] = value7;
          int value9 = this.readUnsignedShort(value7 + 2) * 2;
          int value10 = value4;
          value8++;
          value7 = value10 + (byte) 4 + value9;
        }
        return values2;
      }
      int value11 = value3;
      value2--;
      value3 = value11 + value6;
    }
    throw new IllegalArgumentException();
  }

  public final int calculateCount() {
    int value;
    int value2 = value = this.header + 8 + this.readUnsignedShort(this.header + 6) * 2;
    value += 2;
    int value3 = this.readUnsignedShort(value2);
    while (true) {
      int value4 = value3--;
      if (value4 <= 0) {
        int value5 = value;
        value += 2;
        int value6 = this.readUnsignedShort(value5);
        while (true) {
          int value7 = value6--;
          if (value7 <= 0) {
            return value + 2;
          }
          int value8 = value;
          value += 8;
          int value9 = this.readUnsignedShort(value8 + (byte) 6);
          int value10 = value9;
          while (true) {
            int value11 = value10;
            value9--;
            if (value11 <= 0) {
              break;
            }
            value += 6 + this.readInt(value + 2);
            value10 = value9;
          }
        }
      }
      int value12 = value;
      value += 8;
      int value13 = this.readUnsignedShort(value12 + (byte) 6);
      int value14 = value13;
      while (true) {
        int value15 = value14;
        value13--;
        if (value15 <= 0) {
          break;
        }
        value += 6 + this.readInt(value + 2);
        value14 = value13;
      }
    }
  }

  public byte[] readBytes(int value, int value2) {
    byte[] values = new byte[value2];
    System.arraycopy(this.classFileBuffer, value, values, 0, value2);
    return values;
  }

  public Label getLabel(int value, Label[] labels) {
    Label label = this.createLabel(value, labels);
    label.value3 = (short) (label.value3 & -2);
    return label;
  }

  public int readUnsignedShort(int value) {
    byte[] values = this.classFileBuffer;
    return (this.classFileBuffer[value] & 0xFF) << 8 | values[value + 1] & 0xFF;
  }

  public void accept(ClassVisitor classVisitor, int value) throws ClassNotFoundException {
    this.accept(classVisitor, new Attribute[0], value);
  }

  public int readField(ClassVisitor classVisitor, Context context, int value) {
    char[] values = context.charBuffer;
    int value2 = value;
    int value3 = this.readUnsignedShort(value2);
    String text2 = this.getText2(value2 + 2, values);
    int value4 = value2 + 4;
    value2 += 6;
    String text22 = this.getText2(value4, values);
    Object result = null;
    String text = null;
    int value5 = 0;
    int value6 = 0;
    int value7 = 0;
    int value8 = 0;
    Attribute attribute = null;
    int value9 = value2;
    value2 += 2;
    int value10 = this.readUnsignedShort(value9);
    int value11 = value10;
    while (true) {
      int value12 = value11;
      value10--;
      if (value12 <= 0) {
        FieldVisitor fieldVisitor;
        if ((fieldVisitor = classVisitor.visitField(value3, text2, text22, text, result)) == null) {
          return value2;
        }
        if (value5 != 0) {
          int value13 = this.readUnsignedShort(value5);
          int value14 = value5 + 2;
          value11 = value13;
          while (true) {
            int value15 = value11;
            value13--;
            if (value15 <= 0) {
              break;
            }
            int value16 = value14;
            value14 += 2;
            String text23 = this.getText2(value16, values);
            value14 =
                this.readElementValues(
                    fieldVisitor.visitAnnotation(text23, true), value14, true, values);
            value11 = value13;
          }
        }
        if (value6 != 0) {
          int value17 = this.readUnsignedShort(value6);
          int value18 = value6 + 2;
          value11 = value17;
          while (true) {
            int value19 = value11;
            value17--;
            if (value19 <= 0) {
              break;
            }
            int value20 = value18;
            value18 += 2;
            String text24 = this.getText2(value20, values);
            value18 =
                this.readElementValues(
                    fieldVisitor.visitAnnotation(text24, false), value18, true, values);
            value11 = value17;
          }
        }
        if (value7 != 0) {
          int value21 = this.readUnsignedShort(value7);
          int value22 = value7 + 2;
          value11 = value21;
          while (true) {
            int value23 = value11;
            value21--;
            if (value23 <= 0) {
              break;
            }
            int value24;
            int value25 = value24 = this.readTypeAnnotationTarget(context, value22);
            value24 += 2;
            String text25 = this.getText2(value25, values);
            value22 =
                this.readElementValues(
                    fieldVisitor.visitTypeAnnotation(
                        context.count6, context.currentTypeAnnotationTargetPath, text25, true),
                    value24,
                    true,
                    values);
            value11 = value21;
          }
        }
        if (value8 != 0) {
          int value26 = this.readUnsignedShort(value8);
          int value27 = value8 + 2;
          value11 = value26;
          while (true) {
            int value28 = value11;
            value26--;
            if (value28 <= 0) {
              break;
            }
            int value29;
            int value30 = value29 = this.readTypeAnnotationTarget(context, value27);
            value29 += 2;
            String text26 = this.getText2(value30, values);
            value27 =
                this.readElementValues(
                    fieldVisitor.visitTypeAnnotation(
                        context.count6, context.currentTypeAnnotationTargetPath, text26, false),
                    value29,
                    true,
                    values);
            value11 = value26;
          }
        }
        Attribute attribute2 = attribute;
        while (attribute2 != null) {
          Attribute attribute3 = attribute.nextAttribute;
          attribute.nextAttribute = null;
          attribute2 = attribute3;
          fieldVisitor.visitAttribute(attribute);
          attribute = attribute3;
        }
        fieldVisitor.visitEnd();
        return value2;
      }
      String text27 = this.getText2(value2, values);
      int value31 = value2;
      value2 += 6;
      int value32 = this.readInt(value31 + (byte) 2);
      if ("ConstantValue".equals(text27)) {
        int value33;
        result =
            (value33 = this.readUnsignedShort(value2)) == 0
                ? null
                : this.readConst(value33, values);
        value11 = value2;
      } else if ("Signature".equals(text27)) {
        text = this.getText2(value2, values);
        value11 = value2;
      } else if ("Deprecated".equals(text27)) {
        value3 |= 131072;
        value11 = value2;
      } else if ("Synthetic".equals(text27)) {
        value3 |= 4096;
        value11 = value2;
      } else if ("RuntimeVisibleAnnotations".equals(text27)) {
        value5 = value2;
        value11 = value2;
      } else if ("RuntimeVisibleTypeAnnotations".equals(text27)) {
        value7 = value2;
        value11 = value2;
      } else if ("RuntimeInvisibleAnnotations".equals(text27)) {
        value6 = value2;
        value11 = value2;
      } else if ("RuntimeInvisibleTypeAnnotations".equals(text27)) {
        value8 = value2;
        value11 = value2;
      } else {
        Attribute attribute4 =
            this.readAttribute(
                context.attributePrototypes, text27, value2, value32, values, -1, null);
        attribute4.nextAttribute = attribute;
        attribute = attribute4;
        value11 = value2;
      }
      value2 = value11 + value32;
      value11 = value10;
    }
  }

  public int readVerificationTypeInfo(
      int value, Object[] results, int value2, char[] values, Label[] labels) {
    int value3 = value;
    int value4 = this.classFileBuffer[value3] & 255;
    value3++;
    switch (value4) {
      case 0:
        results[value2] = Opcodes.INTEGER3;
        return value3;
      case 1:
        results[value2] = Opcodes.INTEGER4;
        return value3;
      case 2:
        results[value2] = Opcodes.INTEGER6;
        return value3;
      case 3:
        results[value2] = Opcodes.INTEGER;
        return value3;
      case 4:
        results[value2] = Opcodes.INTEGER5;
        return value3;
      case 5:
        results[value2] = Opcodes.INTEGER2;
        return value3;
      case 6:
        results[value2] = Opcodes.INTEGER7;
        return value3;
      case 7:
        String text5 = this.getText5(value3, values);
        value3 += 2;
        results[value2] = text5;
        return value3;
      case 8:
        Label label = this.getLabel(this.readUnsignedShort(value3), labels);
        value3 += 2;
        results[value2] = label;
        return value3;
      default:
        throw new IllegalArgumentException();
    }
  }

  public int readStackMapFrame(int value, boolean matches, boolean matches2, Context context) {
    int value2 = value;
    char[] values3 = context.charBuffer;
    Label[] labels = context.labels3;
    int value3;
    Context context2;
    if (matches) {
      int value4 = this.classFileBuffer[value2] & 255;
      value2++;
      context2 = context;
      value3 = value4;
    } else {
      value3 = 255;
      context2 = context;
      context.count = -1;
    }
    context2.count5 = 0;
    int value5;
    if (value3 < 64) {
      value5 = value3;
      context2 = context;
      context.count3 = 3;
      context.count4 = 0;
    } else if (value3 < 128) {
      value5 = value3 - 64;
      context2 = context;
      value2 = this.readVerificationTypeInfo(value2, context.values2, 0, values3, labels);
      context.count3 = 4;
      context.count4 = 1;
    } else {
      if (value3 < 247) {
        throw new IllegalArgumentException();
      }
      int value6 = value2;
      value2 += 2;
      value5 = this.readUnsignedShort(value6);
      if (value3 == 247) {
        context2 = context;
        value2 = this.readVerificationTypeInfo(value2, context.values2, 0, values3, labels);
        context.count3 = 4;
        context.count4 = 1;
      } else if (value3 >= 248 && value3 < 251) {
        context2 = context;
        context.count3 = 2;
        context.count5 = 251 - value3;
        context.count8 = context.count8 - context.count5;
        context.count4 = 0;
      } else if (value3 == 251) {
        context2 = context;
        context.count3 = 3;
        context.count4 = 0;
      } else if (value3 < 255) {
        int value7 = matches2 ? context.count8 : 0;
        int value8;
        for (int value9 = value8 = value3 - 251; value9 > 0; value9 = value8) {
          int value10 = value7;
          Object[] results = context.values;
          int value11 = value2;
          value8--;
          int value12 = this.readVerificationTypeInfo(value11, results, value10, values3, labels);
          value7++;
          value2 = value12;
        }
        context2 = context;
        context.count3 = 1;
        context.count5 = value3 - 251;
        context.count8 = context.count8 + context.count5;
        context.count4 = 0;
      } else {
        int value13 = this.readUnsignedShort(value2);
        byte value14 = 0;
        value2 += 2;
        int value15 = value14;
        context.count3 = 0;
        context.count5 = value13;
        context.count8 = value13;
        for (int value16 = value14; value15 < value13; value15 = value16) {
          int value17 = value16;
          Object[] results2 = context.values;
          int value18 = value2;
          value16++;
          value2 = this.readVerificationTypeInfo(value18, results2, value17, values3, labels);
        }
        int value19 = this.readUnsignedShort(value2);
        byte value20 = 0;
        value2 += 2;
        int value21 = value20;
        context.count4 = value19;
        for (int value22 = value20; value21 < value19; value21 = value22) {
          int value23 = value22;
          Object[] results3 = context.values2;
          int value24 = value2;
          value22++;
          value2 = this.readVerificationTypeInfo(value24, results3, value23, values3, labels);
        }
        context2 = context;
      }
    }
    context2.count += value5 + 1;
    this.getLabel(context.count, labels);
    return value2;
  }

  public int readRecordComponent(ClassVisitor classVisitor, Context context, int value) {
    char[] values = context.charBuffer;
    int value2 = value;
    String text2 = this.getText2(value2, values);
    int value3 = value2 + 2;
    value2 += 4;
    String text22 = this.getText2(value3, values);
    String text = null;
    int value4 = 0;
    int value5 = 0;
    int value6 = 0;
    int value7 = 0;
    Attribute attribute = null;
    int value8 = value2;
    value2 += 2;
    int value9 = this.readUnsignedShort(value8);
    int value10 = value9;
    while (true) {
      int value11 = value10;
      value9--;
      if (value11 <= 0) {
        RecordComponentVisitor recordComponentVisitor;
        if ((recordComponentVisitor = classVisitor.visitRecordComponent(text2, text22, text))
            == null) {
          return value2;
        }
        if (value4 != 0) {
          int value12 = this.readUnsignedShort(value4);
          int value13 = value4 + 2;
          value10 = value12;
          while (true) {
            int value14 = value10;
            value12--;
            if (value14 <= 0) {
              break;
            }
            int value15 = value13;
            value13 += 2;
            String text23 = this.getText2(value15, values);
            value13 =
                this.readElementValues(
                    recordComponentVisitor.visitAnnotation(text23, true), value13, true, values);
            value10 = value12;
          }
        }
        if (value5 != 0) {
          int value16 = this.readUnsignedShort(value5);
          int value17 = value5 + 2;
          value10 = value16;
          while (true) {
            int value18 = value10;
            value16--;
            if (value18 <= 0) {
              break;
            }
            int value19 = value17;
            value17 += 2;
            String text24 = this.getText2(value19, values);
            value17 =
                this.readElementValues(
                    recordComponentVisitor.visitAnnotation(text24, false), value17, true, values);
            value10 = value16;
          }
        }
        if (value6 != 0) {
          int value20 = this.readUnsignedShort(value6);
          int value21 = value6 + 2;
          value10 = value20;
          while (true) {
            int value22 = value10;
            value20--;
            if (value22 <= 0) {
              break;
            }
            int value23;
            int value24 = value23 = this.readTypeAnnotationTarget(context, value21);
            value23 += 2;
            String text25 = this.getText2(value24, values);
            value21 =
                this.readElementValues(
                    recordComponentVisitor.visitTypeAnnotation(
                        context.count6, context.currentTypeAnnotationTargetPath, text25, true),
                    value23,
                    true,
                    values);
            value10 = value20;
          }
        }
        if (value7 != 0) {
          int value25 = this.readUnsignedShort(value7);
          int value26 = value7 + 2;
          value10 = value25;
          while (true) {
            int value27 = value10;
            value25--;
            if (value27 <= 0) {
              break;
            }
            int value28;
            int value29 = value28 = this.readTypeAnnotationTarget(context, value26);
            value28 += 2;
            String text26 = this.getText2(value29, values);
            value26 =
                this.readElementValues(
                    recordComponentVisitor.visitTypeAnnotation(
                        context.count6, context.currentTypeAnnotationTargetPath, text26, false),
                    value28,
                    true,
                    values);
            value10 = value25;
          }
        }
        Attribute attribute2 = attribute;
        while (attribute2 != null) {
          Attribute attribute3 = attribute.nextAttribute;
          attribute.nextAttribute = null;
          attribute2 = attribute3;
          recordComponentVisitor.visitAttribute(attribute);
          attribute = attribute3;
        }
        recordComponentVisitor.visitEnd();
        return value2;
      }
      String text27 = this.getText2(value2, values);
      int value30 = value2;
      value2 += 6;
      int value31 = this.readInt(value30 + (byte) 2);
      if ("Signature".equals(text27)) {
        text = this.getText2(value2, values);
        value10 = value2;
      } else if ("RuntimeVisibleAnnotations".equals(text27)) {
        value4 = value2;
        value10 = value2;
      } else if ("RuntimeVisibleTypeAnnotations".equals(text27)) {
        value6 = value2;
        value10 = value2;
      } else if ("RuntimeInvisibleAnnotations".equals(text27)) {
        value5 = value2;
        value10 = value2;
      } else if ("RuntimeInvisibleTypeAnnotations".equals(text27)) {
        value7 = value2;
        value10 = value2;
      } else {
        Attribute attribute4 =
            this.readAttribute(
                context.attributePrototypes, text27, value2, value31, values, -1, null);
        attribute4.nextAttribute = attribute;
        attribute = attribute4;
        value10 = value2;
      }
      value2 = value10 + value31;
      value10 = value9;
    }
  }

  public Object readConst(int value, char[] values) {
    int value2 = this.cpInfoOffsets[value];
    switch (this.classFileBuffer[value2 - 1]) {
      case 3:
        return this.readInt(value2);
      case 4:
        return Float.intBitsToFloat(this.readInt(value2));
      case 5:
        return this.readLong(value2);
      case 6:
        return Double.longBitsToDouble(this.readLong(value2));
      case 7:
        return Type.getObjectType(this.getText2(value2, values));
      case 8:
        return this.getText2(value2, values);
      case 9:
      case 10:
      case 11:
      case 12:
      case 13:
      case 14:
      default:
        throw new IllegalArgumentException();
      case 15:
        int value3 = this.readByte(value2);
        int value4 = this.cpInfoOffsets[this.readUnsignedShort(value2 + 1)];
        int value5 = this.cpInfoOffsets[this.readUnsignedShort(value4 + 2)];
        String text5 = this.getText5(value4, values);
        String text2 = this.getText2(value5, values);
        String text22 = this.getText2(value5 + 2, values);
        int value6 = this.classFileBuffer[value4 - 1] == 11 ? 1 : 0;
        return new Handle(value3, text5, text2, text22, (value6 != 0));
      case 16:
        return Type.getMethodType(this.getText2(value2, values));
      case 17:
        return this.readConstantDynamic(value, values);
    }
  }

  public String getText(int value, char[] values) {
    return this.getText6(value, values);
  }

  public Attribute readAttribute(
      Attribute[] attributes,
      String text,
      int value,
      int value2,
      char[] values,
      int value3,
      Label[] labels) {
    if (value2 > this.classFileBuffer.length - value) {
      throw new IllegalArgumentException();
    }
    int value4 = attributes.length;
    int value5;
    for (int value6 = value5 = 0; value6 < value4; value6 = ++value5) {
      Attribute attribute;
      if ((attribute = attributes[value5]).type.equals(text)) {
        return attribute.read(this, value, value2, values, value3, labels);
      }
    }
    return new Attribute(text).read(this, value, value2, null, -1, null);
  }

  public void createDebugLabel(int value, Label[] labels) {
    if (labels[value] == null) {
      Label label = this.createLabel(value, labels);
      label.value3 = (short) (label.value3 | 1);
    }
  }

  public ClassReader(InputStream inputStream) throws IOException {
    this(readStream(inputStream, false));
  }

  public Label createLabel(int value, Label[] labels) {
    if (labels[value] == null) {
      labels[value] = new Label();
    }
    return labels[value];
  }

  public int[] readTypeAnnotations(
      MethodVisitor methodVisitor, Context context, int value, boolean matches) {
    ClassReader classReader;
    char[] values = context.charBuffer;
    int value2 = value;
    int[] values2 = new int[this.readUnsignedShort(value2)];
    value2 += 2;
    int value3;
    for (int value4 = value3 = 0; value4 < values2.length; value4 = ++value3) {
      int value5;
      label34:
      switch ((value5 = this.readInt(values2[value3] = value2)) >>> 24) {
        case 0:
        case 1:
        case 2:
        case 3:
        case 4:
        case 5:
        case 6:
        case 7:
        case 8:
        case 9:
        case 10:
        case 11:
        case 12:
        case 13:
        case 14:
        case 15:
        case 19:
        case 20:
        case 21:
        case 22:
        case 24:
        case 25:
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
        case 46:
        case 47:
        case 48:
        case 49:
        case 50:
        case 51:
        case 52:
        case 53:
        case 54:
        case 55:
        case 56:
        case 57:
        case 58:
        case 59:
        case 60:
        case 61:
        case 62:
        case 63:
        default:
          throw new IllegalArgumentException();
        case 16:
        case 17:
        case 18:
        case 23:
        case 66:
        case 67:
        case 68:
        case 69:
        case 70:
          value2 += 3;
          classReader = this;
          break;
        case 64:
        case 65:
          int value6 = value2;
          value2 += 3;
          int value7 = this.readUnsignedShort(value6 + (byte) 1);
          value4 = value7;
          while (true) {
            int value8 = value4;
            value7--;
            if (value8 <= 0) {
              classReader = this;
              break label34;
            }
            int value9 = this.readUnsignedShort(value2);
            int value10 = value2;
            value2 += 6;
            int value11 = this.readUnsignedShort(value10 + (byte) 2);
            this.getLabel(value9, context.labels3);
            this.getLabel(value9 + value11, context.labels3);
            value4 = value7;
          }
        case 71:
        case 72:
        case 73:
        case 74:
        case 75:
          value2 += 4;
          classReader = this;
      }
      int value12 = classReader.readByte(value2);
      if (value5 >>> 24 == 66) {
        TypePath typePath = value12 == 0 ? null : new TypePath(this.classFileBuffer, value2);
        int value13 = value2 + 1 + 2 * value12;
        int value14 = value13;
        value13 += 2;
        String text2 = this.getText2(value14, values);
        value2 =
            this.readElementValues(
                methodVisitor.createAnnotationVisitor(value5 & -256, typePath, text2, matches),
                value13,
                true,
                values);
      } else {
        int value15 = value2 + 3 + 2 * value12;
        value2 = this.readElementValues(null, value15, true, values);
      }
    }
    return values2;
  }

  public long readLong(int value) {
    long value2 = this.readInt(value);
    long value3 = this.readInt(value + 4) & 4294967295L;
    return value2 << 32 | value3;
  }

  public String getClassName() {
    return this.getText5(this.header + 2, new char[this.maxStringLength]);
  }

  public static String stringConcat$0(short value) {
    return "Unsupported class file major version " + value;
  }

  public String getText2(int value, char[] values) {
    int value2 = this.readUnsignedShort(value);
    return value != 0 && value2 != 0 ? this.getText4(value2, values) : null;
  }

  public ClassReader(String text) throws IOException {
    this(
        readStream(
            ClassLoader.getSystemResourceAsStream(stringConcat$1(text.replace('.', '/'))), true));
  }

  public String getText3(int value, char[] values) {
    return this.getText6(value, values);
  }

  public int readElementValue(
      AnnotationVisitor annotationVisitor, int value, String text, char[] values) {
    int value2 = value;
    if (annotationVisitor == null) {
      switch (this.classFileBuffer[value2] & 0xFF) {
        case 64:
          return this.readElementValues(null, value2 + 3, true, values);
        case 91:
          return this.readElementValues(null, value2 + 1, false, values);
        case 101:
          return value2 + 5;
        default:
          return value2 + 3;
      }
    } else {
      int value3 = this.classFileBuffer[value2] & 255;
      value2++;
      switch (value3) {
        case 64:
          return this.readElementValues(
              annotationVisitor.visitAnnotation(text, this.getText2(value2, values)),
              value2 + 2,
              true,
              values);
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
          throw new IllegalArgumentException();
        case 66:
          int value4 = this.readInt(this.cpInfoOffsets[this.readUnsignedShort(value2)]);
          value2 += 2;
          annotationVisitor.visit(text, (byte) value4);
          return value2;
        case 67:
          int value5 = this.readInt(this.cpInfoOffsets[this.readUnsignedShort(value2)]);
          value2 += 2;
          annotationVisitor.visit(text, (char) value5);
          return value2;
        case 68:
        case 70:
        case 73:
        case 74:
          int value6 = this.readUnsignedShort(value2);
          value2 += 2;
          annotationVisitor.visit(text, this.readConst(value6, values));
          return value2;
        case 83:
          int value7 = this.readInt(this.cpInfoOffsets[this.readUnsignedShort(value2)]);
          value2 += 2;
          annotationVisitor.visit(text, (short) value7);
          return value2;
        case 90:
          annotationVisitor.visit(
              text,
              this.readInt(this.cpInfoOffsets[this.readUnsignedShort(value2)]) == 0
                  ? Boolean.FALSE
                  : Boolean.TRUE);
          return value2 + 2;
        case 91:
          int value8 = value2;
          value2 += 2;
          int value9 = this.readUnsignedShort(value8);
          if (value9 == 0) {
            return this.readElementValues(
                annotationVisitor.visitArray(text), value2 - 2, false, values);
          } else {
            switch (this.classFileBuffer[value2] & 0xFF) {
              case 66:
                byte[] values2 = new byte[value9];
                int value10;
                for (int value11 = value10 = 0; value11 < value9; value11 = ++value10) {
                  byte value12 =
                      (byte) this.readInt(this.cpInfoOffsets[this.readUnsignedShort(value2 + 1)]);
                  int value13 = value10;
                  value2 += 3;
                  values2[value13] = value12;
                }
                annotationVisitor.visit(text, values2);
                return value2;
              case 67:
                char[] values3 = new char[value9];
                int value14;
                for (int value15 = value14 = 0; value15 < value9; value15 = ++value14) {
                  char value16 =
                      (char) this.readInt(this.cpInfoOffsets[this.readUnsignedShort(value2 + 1)]);
                  int value17 = value14;
                  value2 += 3;
                  values3[value17] = value16;
                }
                annotationVisitor.visit(text, values3);
                return value2;
              case 68:
                double[] values4 = new double[value9];
                int value18;
                for (int value19 = value18 = 0; value19 < value9; value19 = ++value18) {
                  double value20 =
                      Double.longBitsToDouble(
                          this.readLong(this.cpInfoOffsets[this.readUnsignedShort(value2 + 1)]));
                  int value21 = value18;
                  value2 += 3;
                  values4[value21] = value20;
                }
                annotationVisitor.visit(text, values4);
                return value2;
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
              default:
                return this.readElementValues(
                    annotationVisitor.visitArray(text), value2 - 2, false, values);
              case 70:
                float[] values5 = new float[value9];
                int value22;
                for (int value23 = value22 = 0; value23 < value9; value23 = ++value22) {
                  float value24 =
                      Float.intBitsToFloat(
                          this.readInt(this.cpInfoOffsets[this.readUnsignedShort(value2 + 1)]));
                  int value25 = value22;
                  value2 += 3;
                  values5[value25] = value24;
                }
                annotationVisitor.visit(text, values5);
                return value2;
              case 73:
                int[] values6 = new int[value9];
                int value26;
                for (int value27 = value26 = 0; value27 < value9; value27 = ++value26) {
                  int value28 =
                      this.readInt(this.cpInfoOffsets[this.readUnsignedShort(value2 + 1)]);
                  int value29 = value26;
                  value2 += 3;
                  values6[value29] = value28;
                }
                annotationVisitor.visit(text, values6);
                return value2;
              case 74:
                long[] values7 = new long[value9];
                int value30;
                for (int value31 = value30 = 0; value31 < value9; value31 = ++value30) {
                  long value32 =
                      this.readLong(this.cpInfoOffsets[this.readUnsignedShort(value2 + 1)]);
                  int value33 = value30;
                  value2 += 3;
                  values7[value33] = value32;
                }
                annotationVisitor.visit(text, values7);
                return value2;
              case 83:
                short[] values8 = new short[value9];
                int value34;
                for (int value35 = value34 = 0; value35 < value9; value35 = ++value34) {
                  short value36 =
                      (short) this.readInt(this.cpInfoOffsets[this.readUnsignedShort(value2 + 1)]);
                  int value37 = value34;
                  value2 += 3;
                  values8[value37] = value36;
                }
                annotationVisitor.visit(text, values8);
                return value2;
              case 90:
                boolean[] matchess = new boolean[value9];
                int value38;
                int value39 = value38 = 0;
                while (value39 < value9) {
                  matchess[value38] =
                      this.readInt(this.cpInfoOffsets[this.readUnsignedShort(value2 + 1)]) != 0;
                  int value40 = ++value38;
                  value2 += 3;
                  value39 = value40;
                }
                annotationVisitor.visit(text, matchess);
                return value2;
            }
          }
        case 99:
          int value41 = value2;
          value2 += 2;
          annotationVisitor.visit(text, Type.getType(this.getText2(value41, values)));
          return value2;
        case 101:
          String text2 = this.getText2(value2, values);
          int value42 = value2 + 2;
          value2 += 4;
          annotationVisitor.visitEnum(text, text2, this.getText2(value42, values));
          return value2;
        case 115:
          int value43 = value2;
          value2 += 2;
          annotationVisitor.visit(text, this.getText2(value43, values));
          return value2;
      }
    }
  }

  public int getItem(int value) {
    return this.cpInfoOffsets[value];
  }

  public final String getText4(int value, char[] values) {
    String text;
    if ((text = this.constantUtf8Values[value]) != null) {
      return text;
    }
    int value2 = this.cpInfoOffsets[value];
    return this.constantUtf8Values[value] =
        this.readUtf(value2 + 2, this.readUnsignedShort(value2), values);
  }

  public void readParameterAnnotations(
      MethodVisitor methodVisitor, Context context, int value, boolean matches) {
    int value2 = value;
    int value3 = this.classFileBuffer[value2] & 255;
    value2++;
    methodVisitor.visitAnnotableParameterCount(value3, matches);
    char[] values = context.charBuffer;
    int value4;
    for (int value5 = value4 = 0; value5 < value3; value5 = ++value4) {
      int value6 = value2;
      value2 += 2;
      int value7 = this.readUnsignedShort(value6);
      value5 = value7;
      while (true) {
        int value8 = value5;
        value7--;
        if (value8 <= 0) {
          break;
        }
        int value9 = value2;
        value2 += 2;
        String text2 = this.getText2(value9, values);
        value2 =
            this.readElementValues(
                methodVisitor.visitParameterAnnotation(value4, text2, matches),
                value2,
                true,
                values);
        value5 = value7;
      }
    }
  }

  public String getText2() {
    return this.getText5(this.header + 4, new char[this.maxStringLength]);
  }

  public void readBytecodeInstructionOffset(int value) {}

  public int readByte(int value) {
    return this.classFileBuffer[value] & 0xFF;
  }

  public String[] getInterfaces() {
    int value;
    int value2;
    String[] texts = new String[value2 = this.readUnsignedShort(value = this.header + 6)];
    if (value2 > 0) {
      char[] values = new char[this.maxStringLength];
      int value3;
      for (int value4 = value3 = 0; value4 < value2; value4 = value3) {
        int value5 = value3;
        value += 2;
        String text5 = this.getText5(value, values);
        value3++;
        texts[value5] = text5;
      }
    }
    return texts;
  }

  public ConstantDynamic readConstantDynamic(int value, char[] values) {
    ConstantDynamic constantDynamic;
    if ((constantDynamic = this.constantDynamicValues[value]) != null) {
      return constantDynamic;
    }
    int value2 = this.cpInfoOffsets[value];
    int value3;
    String text2 =
        this.getText2(value3 = this.cpInfoOffsets[this.readUnsignedShort(value2 + 2)], values);
    String text22 = this.getText2(value3 + 2, values);
    int value4;
    Handle handle =
        (Handle)
            this.readConst(
                this.readUnsignedShort(
                    value4 = this.bootstrapMethodOffsets[this.readUnsignedShort(value2)]),
                values);
    Object[] results = new Object[this.readUnsignedShort(value4 + 2)];
    value4 += 4;
    int value5;
    for (int value6 = value5 = 0; value6 < results.length; value6 = ++value5) {
      Object result = this.readConst(this.readUnsignedShort(value4), values);
      int value7 = value5;
      value4 += 2;
      results[value7] = result;
    }
    return this.constantDynamicValues[value] = new ConstantDynamic(text2, text22, handle, results);
  }

  public short readShort(int value) {
    byte[] values = this.classFileBuffer;
    return (short) ((this.classFileBuffer[value] & 255) << 8 | values[value + 1] & 0xFF);
  }

  public ClassReader(byte[] values, int value, int value2) {
    this(values, value, true);
  }

  public String getText5(int value, char[] values) {
    return this.getText6(value, values);
  }

  public void accept(ClassVisitor classVisitor, Attribute[] attributes, int value)
      throws ClassNotFoundException {
    Context context = new Context();
    context.attributePrototypes = attributes;
    context.count2 = value;
    context.charBuffer = new char[this.maxStringLength];
    char[] values = context.charBuffer;
    int value2 = this.header;
    int value3 = this.readUnsignedShort(this.header);
    String text5 = this.getText5(value2 + 2, values);
    String text52 = this.getText5(value2 + 4, values);
    String[] texts = new String[this.readUnsignedShort(value2 + 6)];
    value2 += 8;
    int value4;
    for (int value5 = value4 = 0; value5 < texts.length; value5 = ++value4) {
      String text53 = this.getText5(value2, values);
      int value6 = value4;
      value2 += 2;
      texts[value6] = text53;
    }
    int value7 = 0;
    int value8 = 0;
    String text = null;
    String text2 = null;
    String text3 = null;
    int value9 = 0;
    int value10 = 0;
    int value11 = 0;
    int value12 = 0;
    int value13 = 0;
    int value14 = 0;
    String text4 = null;
    String text6 = null;
    int value15 = 0;
    int value16 = 0;
    int value17 = 0;
    Attribute attribute = null;
    int value18;
    int value19;
    for (int value20 = value19 = this.readUnsignedShort((value18 = this.calculateCount()) - 2);
        value20 > 0;
        value20 = value19) {
      String text22 = this.getText2(value18, values);
      int value21 = value18;
      value18 += 6;
      int value22 = this.readInt(value21 + (byte) 2);
      if ("SourceFile".equals(text22)) {
        text2 = this.getText2(value18, values);
        value20 = value18;
      } else if ("InnerClasses".equals(text22)) {
        value7 = value18;
        value20 = value18;
      } else if ("EnclosingMethod".equals(text22)) {
        value8 = value18;
        value20 = value18;
      } else if ("NestHost".equals(text22)) {
        text6 = this.getText5(value18, values);
        value20 = value18;
      } else if ("NestMembers".equals(text22)) {
        value15 = value18;
        value20 = value18;
      } else if ("PermittedSubclasses".equals(text22)) {
        value16 = value18;
        value20 = value18;
      } else if ("Signature".equals(text22)) {
        text = this.getText2(value18, values);
        value20 = value18;
      } else if ("RuntimeVisibleAnnotations".equals(text22)) {
        value9 = value18;
        value20 = value18;
      } else if ("RuntimeVisibleTypeAnnotations".equals(text22)) {
        value11 = value18;
        value20 = value18;
      } else if ("Deprecated".equals(text22)) {
        value3 |= 131072;
        value20 = value18;
      } else if ("Synthetic".equals(text22)) {
        value3 |= 4096;
        value20 = value18;
      } else if ("SourceDebugExtension".equals(text22)) {
        if (value22 > this.classFileBuffer.length - value18) {
          throw new IllegalArgumentException();
        }
        text3 = this.readUtf(value18, value22, new char[value22]);
        value20 = value18;
      } else if ("RuntimeInvisibleAnnotations".equals(text22)) {
        value10 = value18;
        value20 = value18;
      } else if ("RuntimeInvisibleTypeAnnotations".equals(text22)) {
        value12 = value18;
        value20 = value18;
      } else if ("Record".equals(text22)) {
        value17 = value18;
        value3 |= 65536;
        value20 = value18;
      } else if ("Module".equals(text22)) {
        value13 = value18;
        value20 = value18;
      } else if ("ModuleMainClass".equals(text22)) {
        text4 = this.getText5(value18, values);
        value20 = value18;
      } else if ("ModulePackages".equals(text22)) {
        value14 = value18;
        value20 = value18;
      } else {
        if (!"BootstrapMethods".equals(text22)) {
          Attribute attribute2 =
              this.readAttribute(attributes, text22, value18, value22, values, -1, null);
          attribute2.nextAttribute = attribute;
          attribute = attribute2;
        }
        value20 = value18;
      }
      int value23 = value20;
      value19--;
      value18 = value23 + value22;
    }
    classVisitor.visit(this.readInt(this.cpInfoOffsets[1] - 7), value3, text5, text, text52, texts);
    if ((value & 2) == 0 && (text2 != null || text3 != null)) {
      classVisitor.visitSource(text2, text3);
    }
    if (value13 != 0) {
      this.readModuleAttributes(classVisitor, context, value13, value14, text4);
    }
    if (text6 != null) {
      classVisitor.visitNestHost(text6);
    }
    if (value8 != 0) {
      String text54 = this.getText5(value8, values);
      int value24;
      String text7 =
          (value24 = this.readUnsignedShort(value8 + 2)) == 0
              ? null
              : this.getText2(this.cpInfoOffsets[value24], values);
      String text8 = value24 == 0 ? null : this.getText2(this.cpInfoOffsets[value24] + 2, values);
      classVisitor.visitOuterClass(text54, text7, text8);
    }
    if (value9 != 0) {
      int value25 = this.readUnsignedShort(value9);
      int value26 = value9 + 2;
      int value27 = value25;
      while (true) {
        int value28 = value27;
        value25--;
        if (value28 <= 0) {
          break;
        }
        int value29 = value26;
        value26 += 2;
        String text23 = this.getText2(value29, values);
        value26 =
            this.readElementValues(
                classVisitor.visitAnnotation(text23, true), value26, true, values);
        value27 = value25;
      }
    }
    if (value10 != 0) {
      int value30 = this.readUnsignedShort(value10);
      int value31 = value10 + 2;
      int value32 = value30;
      while (true) {
        int value33 = value32;
        value30--;
        if (value33 <= 0) {
          break;
        }
        int value34 = value31;
        value31 += 2;
        String text24 = this.getText2(value34, values);
        value31 =
            this.readElementValues(
                classVisitor.visitAnnotation(text24, false), value31, true, values);
        value32 = value30;
      }
    }
    if (value11 != 0) {
      int value35 = this.readUnsignedShort(value11);
      int value36 = value11 + 2;
      int value37 = value35;
      while (true) {
        int value38 = value37;
        value35--;
        if (value38 <= 0) {
          break;
        }
        int value39;
        int value40 = value39 = this.readTypeAnnotationTarget(context, value36);
        value39 += 2;
        String text25 = this.getText2(value40, values);
        value36 =
            this.readElementValues(
                classVisitor.visitTypeAnnotation(
                    context.count6, context.currentTypeAnnotationTargetPath, text25, true),
                value39,
                true,
                values);
        value37 = value35;
      }
    }
    if (value12 != 0) {
      int value41 = this.readUnsignedShort(value12);
      int value42 = value12 + 2;
      int value43 = value41;
      while (true) {
        int value44 = value43;
        value41 -= 1;
        if (value44 <= 0) {
          break;
        }
        int value45;
        int value46 = value45 = this.readTypeAnnotationTarget(context, value42);
        value45 += 2;
        String text26 = this.getText2(value46, values);
        value42 =
            this.readElementValues(
                classVisitor.visitTypeAnnotation(
                    context.count6, context.currentTypeAnnotationTargetPath, text26, false),
                value45,
                true,
                values);
        value43 = value41;
      }
    }
    Attribute attribute3 = attribute;
    while (attribute3 != null) {
      Attribute attribute4 = attribute.nextAttribute;
      attribute.nextAttribute = null;
      attribute3 = attribute4;
      classVisitor.visitAttribute(attribute);
      attribute = attribute4;
    }
    if (value15 != 0) {
      int value47 = this.readUnsignedShort(value15);
      int value48 = value15 + 2;
      int value49 = value47;
      while (true) {
        int value50 = value49;
        value47--;
        if (value50 <= 0) {
          break;
        }
        int value51 = value48;
        value48 += 2;
        classVisitor.visitNestMember(this.getText5(value51, values));
        value49 = value47;
      }
    }
    if (value16 != 0) {
      int value52 = this.readUnsignedShort(value16);
      int value53 = value16 + 2;
      int value54 = value52;
      while (true) {
        int value55 = value54;
        value52--;
        if (value55 <= 0) {
          break;
        }
        int value56 = value53;
        value53 += 2;
        classVisitor.visitPermittedSubclass(this.getText5(value56, values));
        value54 = value52;
      }
    }
    if (value7 != 0) {
      int value57 = this.readUnsignedShort(value7);
      int value58 = value7 + 2;
      int value59 = value57;
      while (true) {
        int value60 = value59;
        value57--;
        if (value60 <= 0) {
          break;
        }
        String text55 = this.getText5(value58, values);
        String text56 = this.getText5(value58 + 2, values);
        String text27 = this.getText2(value58 + 4, values);
        int value61 = value58;
        value58 += 8;
        classVisitor.visitInnerClass(
            text55, text56, text27, this.readUnsignedShort(value61 + (byte) 6));
        value59 = value57;
      }
    }
    if (value17 != 0) {
      int value62 = value17;
      value17 += 2;
      int value63 = this.readUnsignedShort(value62);
      int value64 = value63;
      while (true) {
        int value65 = value64;
        value63--;
        if (value65 <= 0) {
          break;
        }
        value17 = this.readRecordComponent(classVisitor, context, value17);
        value64 = value63;
      }
    }
    int value66 = value2;
    value2 += 2;
    int value67 = this.readUnsignedShort(value66);
    int value68 = value67;
    while (true) {
      int value69 = value68;
      value67--;
      if (value69 <= 0) {
        int value70 = value2;
        value2 += 2;
        int value71 = this.readUnsignedShort(value70);
        int value72 = value71;
        while (true) {
          int value73 = value72;
          value71 -= 1;
          if (value73 <= 0) {
            classVisitor.visitEnd();
            return;
          }
          value2 = this.readMethod(classVisitor, context, value2);
          value72 = value71;
        }
      }
      value2 = this.readField(classVisitor, context, value2);
      value68 = value67;
    }
  }

  public int readTypeAnnotationTarget(Context context, int value) {
    int value2 = value;
    int value3;
    int value4;
    Context context2;
    switch ((value4 = this.readInt(value)) >>> 24) {
      case 0:
      case 1:
      case 22:
        int value5 = value4;
        value2 += 2;
        value3 = value5 & -65536;
        context2 = context;
        break;
      case 2:
      case 3:
      case 4:
      case 5:
      case 6:
      case 7:
      case 8:
      case 9:
      case 10:
      case 11:
      case 12:
      case 13:
      case 14:
      case 15:
      case 24:
      case 25:
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
      case 46:
      case 47:
      case 48:
      case 49:
      case 50:
      case 51:
      case 52:
      case 53:
      case 54:
      case 55:
      case 56:
      case 57:
      case 58:
      case 59:
      case 60:
      case 61:
      case 62:
      case 63:
      default:
        throw new IllegalArgumentException();
      case 16:
      case 17:
      case 18:
      case 23:
      case 66:
        int value6 = value4;
        value2 += 3;
        value3 = value6 & -256;
        context2 = context;
        break;
      case 19:
      case 20:
      case 21:
        int value7 = value4;
        value2++;
        value3 = value7 & 0xFF000000;
        context2 = context;
        break;
      case 64:
      case 65:
        value3 = value4 & 0xFF000000;
        int value8 = value2;
        value2 += 3;
        int value9 = this.readUnsignedShort(value8 + (byte) 1);
        context.labels2 = new Label[value9];
        context.labels = new Label[value9];
        int value10 = 0;
        context.currentLocalVariableAnnotationRangeIndices = new int[value9];
        for (int index = 0; value10 < value9; value10 = index) {
          int value11 = this.readUnsignedShort(value2);
          int value12 = this.readUnsignedShort(value2 + 2);
          int value13 = value2;
          value2 += 6;
          int value14 = this.readUnsignedShort(value13 + (byte) 4);
          context.labels2[index] = this.getLabel(value11, context.labels3);
          context.labels[index] = this.getLabel(value11 + value12, context.labels3);
          int value15 = index;
          int[] values = context.currentLocalVariableAnnotationRangeIndices;
          index++;
          values[value15] = value14;
        }
        context2 = context;
        break;
      case 67:
      case 68:
      case 69:
      case 70:
        int value16 = value4;
        value2 += 3;
        value3 = value16 & 0xFF000000;
        context2 = context;
        break;
      case 71:
      case 72:
      case 73:
      case 74:
      case 75:
        int value17 = value4;
        value2 += 4;
        value3 = value17 & -16776961;
        context2 = context;
    }
    context2.count6 = value3;
    int value18;
    context.currentTypeAnnotationTargetPath =
        (value18 = this.readByte(value2)) == 0 ? null : new TypePath(this.classFileBuffer, value2);
    return value2 + 1 + 2 * value18;
  }

  public static int computeBufferSize(InputStream inputStream) throws IOException {
    int value;
    return (value = inputStream.available()) < 256 ? 4096 : Math.min(value, 1048576);
  }

  public void computeImplicitFrame(Context context) {
    String text3 = context.text;
    Object[] results = context.values;
    int value = 0;
    if ((context.count7 & 8) == 0) {
      if ("<init>".equals(context.text2)) {
        Integer value2 = Opcodes.INTEGER7;
        int value3 = value;
        value++;
        results[value3] = value2;
      } else {
        int value4 = this.header;
        int value5 = value;
        value++;
        results[value5] = this.getText5(value4 + 2, context.charBuffer);
      }
    }
    int value6 = 1;
    while (true) {
      int value7 = value6;
      char value8 = text3.charAt(value6);
      value6++;
      switch (value8) {
        case 'B':
        case 'C':
        case 'I':
        case 'S':
        case 'Z':
          Integer value9 = Opcodes.INTEGER4;
          int value10 = value;
          value++;
          results[value10] = value9;
          break;
        case 'D':
          Integer value11 = Opcodes.INTEGER;
          int value12 = value;
          value++;
          results[value12] = value11;
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
          context.count8 = value;
          return;
        case 'F':
          Integer value13 = Opcodes.INTEGER6;
          int value14 = value;
          value++;
          results[value14] = value13;
          break;
        case 'J':
          Integer value15 = Opcodes.INTEGER5;
          int value16 = value;
          value++;
          results[value16] = value15;
          break;
        case 'L':
          String text4 = text3;
          while (text4.charAt(value6) != ';') {
            value6++;
            text4 = text3;
          }
          int value17 = value;
          value++;
          String text5 = text3.substring(value7 + (byte) 1, value6);
          value6++;
          results[value17] = text5;
          break;
        case '[':
          String text6 = text3;
          while (text6.charAt(value6) == '[') {
            value6++;
            text6 = text3;
          }
          if (text3.charAt(value6) == 'L') {
            value6++;
            text6 = text3;
            while (text6.charAt(value6) != ';') {
              value6++;
              text6 = text3;
            }
          }
          int value18 = value;
          value6++;
          value++;
          results[value18] = text3.substring(value7, value6);
      }
    }
  }

  public ClassReader(byte[] values) {
    this(values, 0, values.length);
  }

  public int readInt(int value) {
    byte[] values = this.classFileBuffer;
    return (this.classFileBuffer[value] & 0xFF) << 24
        | (values[value + 1] & 0xFF) << 16
        | (values[value + 2] & 0xFF) << 8
        | values[value + 3] & 0xFF;
  }

  public String getText6(int value, char[] values) {
    return this.getText2(this.cpInfoOffsets[this.readUnsignedShort(value)], values);
  }

  public void readModuleAttributes(
      ClassVisitor classVisitor, Context context, int value, int value2, String text) {
    char[] values = context.charBuffer;
    int value3 = value;
    String text2 = this.getText(value3, values);
    int value4 = this.readUnsignedShort(value3 + 2);
    int value5 = value3 + 4;
    value3 += 6;
    String text22 = this.getText2(value5, values);
    ModuleVisitor moduleVisitor;
    if ((moduleVisitor = classVisitor.visitModule(text2, value4, text22)) != null) {
      if (text != null) {
        moduleVisitor.processText(text);
      }
      if (value2 != 0) {
        int value6 = this.readUnsignedShort(value2);
        int value7 = value2 + 2;
        int value8 = value6;
        while (true) {
          int value9 = value8;
          value6--;
          if (value9 <= 0) {
            break;
          }
          int value10 = value7;
          ModuleVisitor moduleVisitor2 = moduleVisitor;
          value7 += 2;
          moduleVisitor2.processText3(this.getText3(value10, values));
          value8 = value6;
        }
      }
      int value11 = value3;
      value3 += 2;
      int value12 = this.readUnsignedShort(value11);
      int value13 = value12;
      while (true) {
        int value14 = value13;
        value12--;
        if (value14 <= 0) {
          int value15 = value3;
          value3 += 2;
          int value16 = this.readUnsignedShort(value15);
          value13 = value16;
          while (true) {
            int value17 = value13;
            value16--;
            if (value17 <= 0) {
              int value18 = value3;
              value3 += 2;
              int value19 = this.readUnsignedShort(value18);
              value13 = value19;
              while (true) {
                int value20 = value13;
                value19--;
                if (value20 <= 0) {
                  int value21 = value3;
                  value3 += 2;
                  int value22 = this.readUnsignedShort(value21);
                  value13 = value22;
                  while (true) {
                    int value23 = value13;
                    value22--;
                    if (value23 <= 0) {
                      int value24 = value3;
                      value3 += 2;
                      int value25 = this.readUnsignedShort(value24);
                      value13 = value25;
                      while (true) {
                        int value26 = value13;
                        value25--;
                        if (value26 <= 0) {
                          moduleVisitor.visitEnd();
                          return;
                        }
                        String text5 = this.getText5(value3, values);
                        int value27 = value3;
                        value3 += 4;
                        int value28 = this.readUnsignedShort(value27 + (byte) 2);
                        String[] texts = new String[value28];
                        int value29;
                        for (int value30 = value29 = 0; value30 < value28; value30 = ++value29) {
                          String text52 = this.getText5(value3, values);
                          int value31 = value29;
                          value3 += 2;
                          texts[value31] = text52;
                        }
                        moduleVisitor.visitProvide(text5, texts);
                        value13 = value25;
                      }
                    }
                    int value32 = value3;
                    ModuleVisitor moduleVisitor3 = moduleVisitor;
                    value3 += 2;
                    moduleVisitor3.processText2(this.getText5(value32, values));
                    value13 = value22;
                  }
                }
                String text3 = this.getText3(value3, values);
                int value33 = this.readUnsignedShort(value3 + 2);
                int value34 = value3;
                value3 += 6;
                int value35 = this.readUnsignedShort(value34 + (byte) 4);
                String[] texts2 = null;
                if (value35 != 0) {
                  texts2 = new String[value35];
                  int value36;
                  for (int value37 = value36 = 0; value37 < value35; value37 = ++value36) {
                    String text4 = this.getText(value3, values);
                    int value38 = value36;
                    String[] texts3 = texts2;
                    value3 += 2;
                    texts3[value38] = text4;
                  }
                }
                moduleVisitor.processTexts2(text3, value33, texts2);
                value13 = value19;
              }
            }
            String text32 = this.getText3(value3, values);
            int value39 = this.readUnsignedShort(value3 + 2);
            int value40 = value3;
            value3 += 6;
            int value41 = this.readUnsignedShort(value40 + (byte) 4);
            String[] texts4 = null;
            if (value41 != 0) {
              texts4 = new String[value41];
              int value42;
              for (int value43 = value42 = 0; value43 < value41; value43 = ++value42) {
                String text6 = this.getText(value3, values);
                int value44 = value42;
                String[] texts5 = texts4;
                value3 += 2;
                texts5[value44] = text6;
              }
            }
            moduleVisitor.processTexts(text32, value39, texts4);
            value13 = value16;
          }
        }
        String text7 = this.getText(value3, values);
        int value45 = this.readUnsignedShort(value3 + 2);
        int value46 = value3 + 4;
        value3 += 6;
        String text23 = this.getText2(value46, values);
        value13 = value12;
        moduleVisitor.visitRequire(text7, value45, text23);
      }
    }
  }

  public int readElementValues(
      AnnotationVisitor annotationVisitor, int value, boolean matches, char[] values) {
    int value2 = value;
    int value3 = value2;
    value2 += 2;
    int value4 = this.readUnsignedShort(value3);
    if (matches) {
      int value5 = value4;
      while (true) {
        int value6 = value5;
        value4--;
        if (value6 <= 0) {
          break;
        }
        String text2 = this.getText2(value2, values);
        value2 = this.readElementValue(annotationVisitor, value2 + 2, text2, values);
        value5 = value4;
      }
    } else {
      int value7 = value4;
      while (true) {
        int value8 = value7;
        value4--;
        if (value8 <= 0) {
          break;
        }
        value2 = this.readElementValue(annotationVisitor, value2, null, values);
        value7 = value4;
      }
    }
    if (annotationVisitor != null) {
      annotationVisitor.visitEnd();
    }
    return value2;
  }

  public ClassReader(byte[] values, int value, boolean matches) {
    super();
    this.classFileBuffer = values;
    this.b = values;
    if (matches && this.readShort(value + 6) > 71) {
      throw new IllegalArgumentException(stringConcat$0(this.readShort(value + 6)));
    }
    int value2;
    this.cpInfoOffsets = new int[value2 = this.readUnsignedShort(value + 8)];
    this.constantUtf8Values = new String[value2];
    int value3 = 1;
    int value4 = value + 10;
    int value5 = 0;
    byte value6 = 0;
    byte value7 = 0;
    for (int value8 = value3; value8 < value2; value8 = value3) {
      int value9 = value4 + 1;
      int value10 = value3;
      int[] values2 = this.cpInfoOffsets;
      value3++;
      values2[value10] = value9;
      int value11;
      switch (values[value4]) {
        case 1:
          if ((value11 = 3 + this.readUnsignedShort(value4 + 1)) > value5) {
            value5 = value11;
            value8 = value4;
          } else {
            value8 = value4;
          }
          break;
        case 2:
        case 13:
        case 14:
        default:
          throw new IllegalArgumentException();
        case 3:
        case 4:
        case 9:
        case 10:
        case 11:
        case 12:
          value11 = 5;
          value8 = value4;
          break;
        case 5:
        case 6:
          byte value12 = 9;
          value3++;
          value11 = value12;
          value8 = value4;
          break;
        case 7:
        case 8:
        case 16:
        case 19:
        case 20:
          value11 = 3;
          value8 = value4;
          break;
        case 15:
          value11 = 4;
          value8 = value4;
          break;
        case 17:
          value11 = 5;
          value6 = 1;
          value7 = 1;
          value8 = value4;
          break;
        case 18:
          value11 = 5;
          value6 = 1;
          value8 = value4;
      }
      value4 = value8 + value11;
    }
    this.maxStringLength = value5;
    this.header = value4;
    this.constantDynamicValues = value7 != 0 ? new ConstantDynamic[value2] : null;
    this.bootstrapMethodOffsets = value6 != 0 ? this.readBootstrapMethodsAttribute(value5) : null;
  }

  public static String stringConcat$1(String text) {
    return "" + text + ".class";
  }

  public void readCode(MethodVisitor methodVisitor, Context context, int value)
      throws ClassNotFoundException {
    MethodVisitor methodVisitor2;
    int value2 = value;
    byte[] values3 = this.classFileBuffer;
    char[] values4 = context.charBuffer;
    int value3 = this.readUnsignedShort(value2);
    int value4 = this.readUnsignedShort(value2 + 2);
    int value5 = value2;
    value2 += 8;
    int value6 = this.readInt(value5 + (byte) 4);
    if (value6 > this.classFileBuffer.length - value2) {
      throw new IllegalArgumentException();
    }
    int value7 = value2;
    int value8 = value2 + value6;
    Label[] labels4 = context.labels3 = new Label[value6 + 1];
    label428:
    while (value2 < value8) {
      int value9 = value2 - value7;
      switch (values3[value2] & 0xFF) {
        case 0:
        case 1:
        case 2:
        case 3:
        case 4:
        case 5:
        case 6:
        case 7:
        case 8:
        case 9:
        case 10:
        case 11:
        case 12:
        case 13:
        case 14:
        case 15:
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
        case 46:
        case 47:
        case 48:
        case 49:
        case 50:
        case 51:
        case 52:
        case 53:
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
        case 79:
        case 80:
        case 81:
        case 82:
        case 83:
        case 84:
        case 85:
        case 86:
        case 87:
        case 88:
        case 89:
        case 90:
        case 91:
        case 92:
        case 93:
        case 94:
        case 95:
        case 96:
        case 97:
        case 98:
        case 99:
        case 100:
        case 101:
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
        case 115:
        case 116:
        case 117:
        case 118:
        case 119:
        case 120:
        case 121:
        case 122:
        case 123:
        case 124:
        case 125:
        case 126:
        case 127:
        case 128:
        case 129:
        case 130:
        case 131:
        case 133:
        case 134:
        case 135:
        case 136:
        case 137:
        case 138:
        case 139:
        case 140:
        case 141:
        case 142:
        case 143:
        case 144:
        case 145:
        case 146:
        case 147:
        case 148:
        case 149:
        case 150:
        case 151:
        case 152:
        case 172:
        case 173:
        case 174:
        case 175:
        case 176:
        case 177:
        case 190:
        case 191:
        case 194:
        case 195:
          value2++;
          break;
        case 16:
        case 18:
        case 21:
        case 22:
        case 23:
        case 24:
        case 25:
        case 54:
        case 55:
        case 56:
        case 57:
        case 58:
        case 169:
        case 188:
          value2 += 2;
          break;
        case 17:
        case 19:
        case 20:
        case 132:
        case 178:
        case 179:
        case 180:
        case 181:
        case 182:
        case 183:
        case 184:
        case 187:
        case 189:
        case 192:
        case 193:
          value2 += 3;
          break;
        case 153:
        case 154:
        case 155:
        case 156:
        case 157:
        case 158:
        case 159:
        case 160:
        case 161:
        case 162:
        case 163:
        case 164:
        case 165:
        case 166:
        case 167:
        case 168:
        case 198:
        case 199:
          short value10 = this.readShort(value2 + 1);
          value2 += 3;
          this.getLabel(value9 + value10, labels4);
          break;
        case 170:
          value2 += 4 - (value9 & 3);
          this.getLabel(value9 + this.readInt(value2), labels4);
          int value11 = this.readInt(value2 + 8) - this.readInt(value2 + 4);
          value2 += 12;
          int value12 = value11 + 1;
          int value13 = value12;
          while (true) {
            int value14 = value13;
            value12--;
            if (value14 <= 0) {
              continue label428;
            }
            int value15 = this.readInt(value2);
            value2 += 4;
            this.getLabel(value9 + value15, labels4);
            value13 = value12;
          }
        case 171:
          value2 += 4 - (value9 & 3);
          this.getLabel(value9 + this.readInt(value2), labels4);
          int value16 = value2;
          value2 += 8;
          int value17 = this.readInt(value16 + (byte) 4);
          int value18 = value17;
          while (true) {
            int value19 = value18;
            value17 -= 1;
            if (value19 <= 0) {
              continue label428;
            }
            int value20 = this.readInt(value2 + 4);
            value2 += 8;
            this.getLabel(value9 + value20, labels4);
            value18 = value17;
          }
        case 185:
        case 186:
          value2 += 5;
          break;
        case 196:
          switch (values3[value2 + 1] & 0xFF) {
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 54:
            case 55:
            case 56:
            case 57:
            case 58:
            case 169:
              value2 += 4;
              continue;
            case 132:
              value2 += 6;
              continue;
            default:
              throw new IllegalArgumentException();
          }
        case 197:
          value2 += 4;
          break;
        case 200:
        case 201:
        case 220:
          int value21 = this.readInt(value2 + 1);
          value2 += 5;
          this.getLabel(value9 + value21, labels4);
          break;
        case 202:
        case 203:
        case 204:
        case 205:
        case 206:
        case 207:
        case 208:
        case 209:
        case 210:
        case 211:
        case 212:
        case 213:
        case 214:
        case 215:
        case 216:
        case 217:
        case 218:
        case 219:
          int value22 = this.readUnsignedShort(value2 + 1);
          value2 += 3;
          this.getLabel(value9 + value22, labels4);
          break;
        default:
          throw new IllegalArgumentException();
      }
    }
    int value23 = value2;
    value2 += 2;
    int value24 = this.readUnsignedShort(value23);
    int value25 = value24;
    while (true) {
      int value26 = value25;
      value24 -= 1;
      if (value26 <= 0) {
        int value27 = 0;
        int value28 = 0;
        byte value29 = 1;
        int value30 = 0;
        int value31 = 0;
        int[] values5 = null;
        int[] values6 = null;
        Attribute attribute = null;
        int value32 = value2;
        value2 += 2;
        int value33 = this.readUnsignedShort(value32);
        value25 = value33;
        while (true) {
          int value34 = value25;
          value33--;
          if (value34 <= 0) {
            int value35 = (context.count2 & 8) != 0 ? 1 : 0;
            if (value27 != 0) {
              context.count = -1;
              context.count3 = 0;
              context.count8 = 0;
              context.count5 = 0;
              context.values = new Object[value4];
              context.count4 = 0;
              context.values2 = new Object[value3];
              if (value35 != 0) {
                this.computeImplicitFrame(context);
              }
              int value36;
              for (int value37 = value36 = value27; value37 < value28 - 2; value37 = value36) {
                int value38;
                if (values3[value36] == 8
                    && (value38 = this.readUnsignedShort(value36 + 1)) >= 0
                    && value38 < value6
                    && (values3[value7 + value38] & 255) == 187) {
                  this.getLabel(value38, labels4);
                }
                value36 += 1;
              }
            }
            if (value35 != 0 && (context.count2 & 256) != 0) {
              methodVisitor.visitFrame(-1, value4, null, 0, null);
            }
            int value39 = 0;
            int typeAnnotationBytecodeOffset = this.getTypeAnnotationBytecodeOffset(values5, 0);
            int value40 = 0;
            int typeAnnotationBytecodeOffset2 = this.getTypeAnnotationBytecodeOffset(values6, 0);
            boolean matches = false;
            int value41 = (context.count2 & 256) == 0 ? 33 : 0;
            int value42 = value7;
            while (value42 < value8) {
              int value43 = value42 - value7;
              this.readBytecodeInstructionOffset(value43);
              Label label;
              if ((label = labels4[value43]) != null) {
                label.accept(methodVisitor, (context.count2 & 2) == 0);
              }
              for (int value44 = value27;
                  value44 != 0 && (context.count == value43 || context.count == -1);
                  value44 =
                      value27 < value28
                          ? (value27 =
                              this.readStackMapFrame(value27, value29 != 0, value35 != 0, context))
                          : (value27 = 0)) {
                if (context.count != -1) {
                  if (value29 != 0 && value35 == 0) {
                    methodVisitor.visitFrame(
                        context.count3,
                        context.count5,
                        context.values,
                        context.count4,
                        context.values2);
                  } else {
                    methodVisitor.visitFrame(
                        -1, context.count8, context.values, context.count4, context.values2);
                  }
                  matches = false;
                }
              }
              if (matches) {
                if ((context.count2 & 8) != 0) {
                  methodVisitor.visitFrame(256, 0, null, 0, null);
                }
                matches = false;
              }
              int value45;
              int[] values7;
              switch (value45 = values3[value42] & 0xFF) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                case 46:
                case 47:
                case 48:
                case 49:
                case 50:
                case 51:
                case 52:
                case 53:
                case 79:
                case 80:
                case 81:
                case 82:
                case 83:
                case 84:
                case 85:
                case 86:
                case 87:
                case 88:
                case 89:
                case 90:
                case 91:
                case 92:
                case 93:
                case 94:
                case 95:
                case 96:
                case 97:
                case 98:
                case 99:
                case 100:
                case 101:
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
                case 115:
                case 116:
                case 117:
                case 118:
                case 119:
                case 120:
                case 121:
                case 122:
                case 123:
                case 124:
                case 125:
                case 126:
                case 127:
                case 128:
                case 129:
                case 130:
                case 131:
                case 133:
                case 134:
                case 135:
                case 136:
                case 137:
                case 138:
                case 139:
                case 140:
                case 141:
                case 142:
                case 143:
                case 144:
                case 145:
                case 146:
                case 147:
                case 148:
                case 149:
                case 150:
                case 151:
                case 152:
                case 172:
                case 173:
                case 174:
                case 175:
                case 176:
                case 177:
                case 190:
                case 191:
                case 194:
                case 195:
                  value42++;
                  methodVisitor.visitInsn(value45);
                  values7 = values5;
                  break;
                case 16:
                case 188:
                  int value46 = value42 + 1;
                  int value47 = value45;
                  value42 += 2;
                  methodVisitor.visitIntInsn(value47, values3[value46]);
                  values7 = values5;
                  break;
                case 17:
                  int value48 = value42;
                  int value49 = value45;
                  value42 += 3;
                  methodVisitor.visitIntInsn(value49, this.readShort(value48 + (byte) 1));
                  values7 = values5;
                  break;
                case 18:
                  int value50 = values3[value42 + 1] & 255;
                  value42 += 2;
                  methodVisitor.visitLdcInsn(this.readConst(value50, values4));
                  values7 = values5;
                  break;
                case 19:
                case 20:
                  int value51 = this.readUnsignedShort(value42 + 1);
                  value42 += 3;
                  methodVisitor.visitLdcInsn(this.readConst(value51, values4));
                  values7 = values5;
                  break;
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                case 54:
                case 55:
                case 56:
                case 57:
                case 58:
                case 169:
                  byte value52 = values3[value42 + 1];
                  int value53 = value45;
                  value42 += 2;
                  methodVisitor.visitVarInsn(value53, value52 & 255);
                  values7 = values5;
                  break;
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
                  value45 -= 26;
                  int value54 = 21 + (value45 >> 2);
                  int value55 = value45;
                  int[] values8 = values5;
                  value42++;
                  values7 = values8;
                  methodVisitor.visitVarInsn(value54, value55 & 3);
                  break;
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
                  value45 -= 59;
                  int value56 = 54 + (value45 >> 2);
                  int value57 = value45;
                  int[] values9 = values5;
                  value42++;
                  values7 = values9;
                  methodVisitor.visitVarInsn(value56, value57 & 3);
                  break;
                case 132:
                  int value58 = values3[value42 + 1] & 255;
                  int value59 = value42 + 2;
                  value42 += 3;
                  methodVisitor.visitIincInsn(value58, values3[value59]);
                  values7 = values5;
                  break;
                case 153:
                case 154:
                case 155:
                case 156:
                case 157:
                case 158:
                case 159:
                case 160:
                case 161:
                case 162:
                case 163:
                case 164:
                case 165:
                case 166:
                case 167:
                case 168:
                case 198:
                case 199:
                  int value60 = value43 + this.readShort(value42 + 1);
                  int value61 = value45;
                  value42 += 3;
                  methodVisitor.visitJumpInsn(value61, labels4[value60]);
                  values7 = values5;
                  break;
                case 170:
                  value42 += 4 - (value43 & 3);
                  Label label2 = labels4[value43 + this.readInt(value42)];
                  int value62 = this.readInt(value42 + 4);
                  int value63 = value42;
                  value42 += 12;
                  int value64 = this.readInt(value63 + (byte) 8);
                  Label[] labels5 = new Label[value64 - value62 + 1];
                  int value65;
                  for (int value66 = value65 = 0; value66 < labels5.length; value66 = ++value65) {
                    Label label3 = labels4[value43 + this.readInt(value42)];
                    int value67 = value65;
                    value42 += 4;
                    labels5[value67] = label3;
                  }
                  methodVisitor.visitTableSwitchInsn(value62, value64, label2, labels5);
                  values7 = values5;
                  break;
                case 171:
                  value42 += 4 - (value43 & 3);
                  Label label4 = labels4[value43 + this.readInt(value42)];
                  int value68 = value42;
                  value42 += 8;
                  int value69 = this.readInt(value68 + (byte) 4);
                  int[] values10 = new int[value69];
                  Label[] labels6 = new Label[value69];
                  int value70;
                  for (int value71 = value70 = 0; value71 < value69; value71 = value70) {
                    values10[value70] = this.readInt(value42);
                    Label label5 = labels4[value43 + this.readInt(value42 + 4)];
                    int value72 = value70;
                    value42 += 8;
                    labels6[value72] = label5;
                    value70 += 1;
                  }
                  methodVisitor.visitLookupSwitchInsn(label4, values10, labels6);
                  values7 = values5;
                  break;
                case 178:
                case 179:
                case 180:
                case 181:
                case 182:
                case 183:
                case 184:
                case 185:
                  int value73 = this.cpInfoOffsets[this.readUnsignedShort(value42 + 1)];
                  int value74 = this.cpInfoOffsets[this.readUnsignedShort(value73 + 2)];
                  String text5 = this.getText5(value73, values4);
                  String text2 = this.getText2(value74, values4);
                  String text22 = this.getText2(value74 + 2, values4);
                  if (value45 < 182) {
                    value25 = value45;
                    methodVisitor.visitFieldInsn(value45, text5, text2, text22);
                  } else {
                    boolean matches2 = values3[value73 - 1] == 11;
                    value25 = value45;
                    methodVisitor.visitMethodInsn(value45, text5, text2, text22, matches2);
                  }
                  if (value25 == 185) {
                    int[] values11 = values5;
                    value42 += 5;
                    values7 = values11;
                  } else {
                    value42 += 3;
                    values7 = values5;
                  }
                  break;
                case 186:
                  int value75 = this.cpInfoOffsets[this.readUnsignedShort(value42 + 1)];
                  int value76;
                  String text23 =
                      this.getText2(
                          value76 = this.cpInfoOffsets[this.readUnsignedShort(value75 + 2)],
                          values4);
                  String text24 = this.getText2(value76 + 2, values4);
                  int value77;
                  Handle handle =
                      (Handle)
                          this.readConst(
                              this.readUnsignedShort(
                                  value77 =
                                      this.bootstrapMethodOffsets[this.readUnsignedShort(value75)]),
                              values4);
                  Object[] results = new Object[this.readUnsignedShort(value77 + 2)];
                  value77 += 4;
                  int value78;
                  for (int value79 = value78 = 0; value79 < results.length; value79 = value78) {
                    Object result = this.readConst(this.readUnsignedShort(value77), values4);
                    int value80 = value78;
                    value77 += 2;
                    results[value80] = result;
                    value78 += 1;
                  }
                  value42 += 5;
                  methodVisitor.visitInvokeDynamicInsn(text23, text24, handle, results);
                  values7 = values5;
                  break;
                case 187:
                case 189:
                case 192:
                case 193:
                  int value81 = value42 + 1;
                  int value82 = value45;
                  value42 += 3;
                  methodVisitor.visitTypeInsn(value82, this.getText5(value81, values4));
                  values7 = values5;
                  break;
                case 196:
                  int value83;
                  if ((value83 = values3[value42 + 1] & 255) == 132) {
                    int value84 = this.readUnsignedShort(value42 + 2);
                    int value85 = value42;
                    int[] values12 = values5;
                    value42 += 6;
                    values7 = values12;
                    methodVisitor.visitIincInsn(value84, this.readShort(value85 + (byte) 4));
                  } else {
                    int value86 = value42;
                    int value87 = value83;
                    value42 += 4;
                    methodVisitor.visitVarInsn(value87, this.readUnsignedShort(value86 + (byte) 2));
                    values7 = values5;
                  }
                  break;
                case 197:
                  String text52 = this.getText5(value42 + 1, values4);
                  byte value88 = values3[value42 + 3];
                  value42 += 4;
                  methodVisitor.visitMultiANewArrayInsn(text52, value88 & 255);
                  values7 = values5;
                  break;
                case 200:
                case 201:
                  int value89 = value45 - value41;
                  int value90 = value43 + this.readInt(value42 + 1);
                  value42 += 5;
                  methodVisitor.visitJumpInsn(value89, labels4[value90]);
                  values7 = values5;
                  break;
                case 202:
                case 203:
                case 204:
                case 205:
                case 206:
                case 207:
                case 208:
                case 209:
                case 210:
                case 211:
                case 212:
                case 213:
                case 214:
                case 215:
                case 216:
                case 217:
                case 218:
                case 219:
                  int value91 = value45 < 218 ? value45 - 49 : value45 - 20;
                  Label label6 = labels4[value43 + this.readUnsignedShort(value42 + 1)];
                  if (value91 != 167 && value91 != 168) {
                    int value92 = value91 < 167 ? (value91 + 1 ^ 1) - 1 : value91 ^ 1;
                    Label label7 = this.getLabel(value43 + 3, labels4);
                    methodVisitor.visitJumpInsn(value92, label7);
                    methodVisitor.visitJumpInsn(200, label6);
                    matches = true;
                  } else {
                    methodVisitor.visitJumpInsn(value91 + 33, label6);
                  }
                  value42 += 3;
                  values7 = values5;
                  break;
                case 220:
                  Label label8 = labels4[value43 + this.readInt(value42 + 1)];
                  value42 += 5;
                  methodVisitor.visitJumpInsn((short) 200, label8);
                  matches = true;
                  values7 = values5;
                  break;
                default:
                  throw new AssertionError();
              }
              while (values7 != null
                  && value39 < values5.length
                  && typeAnnotationBytecodeOffset <= value43) {
                if (typeAnnotationBytecodeOffset == value43) {
                  int value93;
                  int value94 = value93 = this.readTypeAnnotationTarget(context, values5[value39]);
                  value93 += 2;
                  String text25 = this.getText2(value94, values4);
                  this.readElementValues(
                      methodVisitor.createAnnotationVisitor3(
                          context.count6, context.currentTypeAnnotationTargetPath, text25, true),
                      value93,
                      true,
                      values4);
                }
                typeAnnotationBytecodeOffset =
                    this.getTypeAnnotationBytecodeOffset(values5, ++value39);
                values7 = values5;
              }
              for (int[] values13 = values6;
                  values13 != null
                      && value40 < values6.length
                      && typeAnnotationBytecodeOffset2 <= value43;
                  values13 = values6) {
                if (typeAnnotationBytecodeOffset2 == value43) {
                  int value95;
                  int value96 = value95 = this.readTypeAnnotationTarget(context, values6[value40]);
                  value95 += 2;
                  String text26 = this.getText2(value96, values4);
                  this.readElementValues(
                      methodVisitor.createAnnotationVisitor3(
                          context.count6, context.currentTypeAnnotationTargetPath, text26, false),
                      value95,
                      true,
                      values4);
                }
                value40 += 1;
                typeAnnotationBytecodeOffset2 =
                    this.getTypeAnnotationBytecodeOffset(values6, value40);
              }
            }
            if (labels4[value6] != null) {
              methodVisitor.visitLabel(labels4[value6]);
            }
            if (value30 != 0 && (context.count2 & 2) == 0) {
              int[] values14 = null;
              if (value31 != 0) {
                values14 = new int[this.readUnsignedShort(value31) * 3];
                int value97 = value31 + 2;
                int value98;
                for (int value99 = value98 = values14.length; value99 > 0; value99 = value98) {
                  int value100 = value98 - 1;
                  int value101 = value97 + 6;
                  int[] values15 = values14;
                  int[] values16 = values14;
                  int[] values17 = values14;
                  int value102 = value100 - 1;
                  values15[value100] = value101;
                  int value103 = this.readUnsignedShort(value97 + 8);
                  value98 = value102 - 1;
                  values16[value102] = value103;
                  int value104 = this.readUnsignedShort(value97);
                  int value105 = value98;
                  value97 += 10;
                  values17[value105] = value104;
                }
              }
              int value106 = this.readUnsignedShort(value30);
              int value107 = value30 + 2;
              value25 = value106;
              while (true) {
                int value108 = value25;
                value106 -= 1;
                if (value108 <= 0) {
                  break;
                }
                int value109;
                int value110;
                String text;
                int value111;
                String text3;
                String text4;
                label285:
                {
                  value111 = this.readUnsignedShort(value107);
                  value110 = this.readUnsignedShort(value107 + 2);
                  text4 = this.getText2(value107 + 4, values4);
                  text3 = this.getText2(value107 + 6, values4);
                  int value112 = value107;
                  value107 += 10;
                  value109 = this.readUnsignedShort(value112 + (byte) 8);
                  text = null;
                  int value113;
                  if (values14 != null) {
                    for (int value114 = value113 = 0;
                        value114 < values14.length;
                        value114 = value113) {
                      if (values14[value113] == value111 && values14[value113 + 1] == value109) {
                        text = this.getText2(values14[value113 + 2], values4);
                        methodVisitor2 = methodVisitor;
                        break label285;
                      }
                      value113 += 3;
                    }
                  }
                  methodVisitor2 = methodVisitor;
                }
                methodVisitor2.visitLocalVariable(
                    text4, text3, text, labels4[value111], labels4[value111 + value110], value109);
                value25 = value106;
              }
            }
            if (values5 != null) {
              int[] values18 = values5;
              int value115 = values5.length;
              int value116;
              for (int value117 = value116 = 0; value117 < value115; value117 = ++value116) {
                int value118;
                int value119;
                if ((value118 = this.readByte(value119 = values18[value116])) == 64
                    || value118 == 65) {
                  int value120;
                  int value121 = value120 = this.readTypeAnnotationTarget(context, value119);
                  value120 += 2;
                  String text27 = this.getText2(value121, values4);
                  this.readElementValues(
                      methodVisitor.visitLocalVariableAnnotation(
                          context.count6,
                          context.currentTypeAnnotationTargetPath,
                          context.labels2,
                          context.labels,
                          context.currentLocalVariableAnnotationRangeIndices,
                          text27,
                          true),
                      value120,
                      true,
                      values4);
                }
              }
            }
            if (values6 != null) {
              int[] values19 = values6;
              int value122 = values6.length;
              int value123;
              for (int value124 = value123 = 0; value124 < value122; value124 = value123) {
                int value125;
                int value126;
                if ((value126 = this.readByte(value125 = values19[value123])) == 64
                    || value126 == 65) {
                  int value127;
                  int value128 = value127 = this.readTypeAnnotationTarget(context, value125);
                  value127 += 2;
                  String text28 = this.getText2(value128, values4);
                  this.readElementValues(
                      methodVisitor.visitLocalVariableAnnotation(
                          context.count6,
                          context.currentTypeAnnotationTargetPath,
                          context.labels2,
                          context.labels,
                          context.currentLocalVariableAnnotationRangeIndices,
                          text28,
                          false),
                      value127,
                      true,
                      values4);
                }
                value123 += 1;
              }
            }
            Attribute attribute2 = attribute;
            while (attribute2 != null) {
              Attribute attribute3 = attribute.nextAttribute;
              attribute.nextAttribute = null;
              attribute2 = attribute3;
              methodVisitor.visitAttribute(attribute);
              attribute = attribute3;
            }
            methodVisitor.visitMaxs(value3, value4);
            return;
          }
          int value129;
          label404:
          {
            String text29 = this.getText2(value2, values4);
            int value130 = value2;
            value2 += 6;
            value129 = this.readInt(value130 + (byte) 2);
            if ("LocalVariableTable".equals(text29)) {
              if ((context.count2 & 2) == 0) {
                value30 = value2;
                int value131 = value2;
                int value132 = value131;
                value131 += 2;
                int value133 = this.readUnsignedShort(value132);
                value25 = value133;
                while (true) {
                  int value134 = value25;
                  value133 -= 1;
                  if (value134 <= 0) {
                    break;
                  }
                  int value135;
                  this.createDebugLabel(value135 = this.readUnsignedShort(value131), labels4);
                  int value136 = this.readUnsignedShort(value131 + 2);
                  int value137 = value135;
                  value131 += 10;
                  this.createDebugLabel(value137 + value136, labels4);
                  value25 = value133;
                }
              }
            } else {
              if ("LocalVariableTypeTable".equals(text29)) {
                value31 = value2;
                value25 = value2;
                break label404;
              }
              if ("LineNumberTable".equals(text29)) {
                if ((context.count2 & 2) == 0) {
                  int value138 = value2;
                  int value139 = value138;
                  value138 += 2;
                  int value140 = this.readUnsignedShort(value139);
                  value25 = value140;
                  while (true) {
                    int value141 = value25;
                    value140 -= 1;
                    if (value141 <= 0) {
                      break;
                    }
                    int value142 = this.readUnsignedShort(value138);
                    int value143 = value138;
                    value138 += 4;
                    int value144 = this.readUnsignedShort(value143 + (byte) 2);
                    this.createDebugLabel(value142, labels4);
                    labels4[value142].addLineNumber(value144);
                    value25 = value140;
                  }
                }
              } else {
                if ("RuntimeVisibleTypeAnnotations".equals(text29)) {
                  values5 = this.readTypeAnnotations(methodVisitor, context, value2, true);
                  value25 = value2;
                  break label404;
                }
                if ("RuntimeInvisibleTypeAnnotations".equals(text29)) {
                  values6 = this.readTypeAnnotations(methodVisitor, context, value2, false);
                  value25 = value2;
                  break label404;
                }
                if ("StackMapTable".equals(text29)) {
                  if ((context.count2 & 4) == 0) {
                    value27 = value2 + 2;
                    value28 = value2 + value129;
                    value25 = value2;
                    break label404;
                  }
                } else if ("StackMap".equals(text29)) {
                  if ((context.count2 & 4) == 0) {
                    value27 = value2 + 2;
                    value28 = value2 + value129;
                    value29 = 0;
                    value25 = value2;
                    break label404;
                  }
                } else {
                  Attribute attribute4 =
                      this.readAttribute(
                          context.attributePrototypes,
                          text29,
                          value2,
                          value129,
                          values4,
                          value,
                          labels4);
                  attribute4.nextAttribute = attribute;
                  attribute = attribute4;
                }
              }
            }
            value25 = value2;
          }
          value2 = value25 + value129;
          value25 = value33;
        }
      }
      Label label9 = this.getLabel(this.readUnsignedShort(value2), labels4);
      Label label10 = this.getLabel(this.readUnsignedShort(value2 + 2), labels4);
      Label label11 = this.getLabel(this.readUnsignedShort(value2 + 4), labels4);
      int value145 = this.cpInfoOffsets[this.readUnsignedShort(value2 + 6)];
      value2 += 8;
      String text210 = this.getText2(value145, values4);
      value25 = value24;
      methodVisitor.visitTryCatchBlock(label9, label10, label11, text210);
    }
  }

  public int getMaxStringLength() {
    return this.maxStringLength;
  }

  public int getItemCount() {
    return this.cpInfoOffsets.length;
  }

  public int getTypeAnnotationBytecodeOffset(int[] values, int value) {
    return values != null && value < values.length && this.readByte(values[value]) >= 67
        ? this.readUnsignedShort(values[value] + 1)
        : -1;
  }
}
