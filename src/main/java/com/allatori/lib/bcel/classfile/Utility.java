package com.allatori.lib.bcel.classfile;

import com.allatori.lib.bcel.Constants;
import com.allatori.lib.bcel.generic.AnnotationEntryGen;
import com.allatori.lib.bcel.generic.ConstantPoolGen;
import com.allatori.lib.bcel.util.ByteSequence;
import com.allatori.lib.bcel.util.TypeParserThreadLocal2;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.CharArrayReader;
import java.io.CharArrayWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public abstract class Utility {

  public static ThreadLocal consumed_chars = new TypeParserThreadLocal2();

  public static boolean wide = false;

  public static int[] counts2 = new int[48];

  public static int[] counts = new int[256];

  static {
    int value = 0;
    int value2;
    for (int value3 = value2 = 65; value3 <= 90; value3 = ++value2) {
      counts2[value] = value2;
      counts[value2] = value++;
    }
    int value4;
    for (int value5 = value4 = 103; value5 <= 122; value5 = ++value4) {
      counts2[value] = value4;
      counts[value4] = value++;
    }
    counts2[value] = 36;
    counts[36] = value++;
    counts2[value] = 95;
    counts[95] = value;
  }

  public static final int FREE_CHARS = 48;

  public static final char ESCAPE_CHAR = '$';

  public static final String printArray(Object[] results, boolean matches, boolean matches2) {
    if (results == null) {
      return null;
    }
    StringBuffer stringBuffer = new StringBuffer();
    if (matches) {
      stringBuffer.append('{');
    }
    int value;
    for (int value2 = value = 0; value2 < results.length; value2 = ++value) {
      if (results[value] != null) {
        stringBuffer
            .append(matches2 ? "\"" : "")
            .append(results[value].toString())
            .append(matches2 ? "\"" : "");
        value2 = value;
      } else {
        stringBuffer.append("null");
        value2 = value;
      }
      if (value2 < results.length - 1) {
        stringBuffer.append(", ");
      }
    }
    if (matches) {
      stringBuffer.append('}');
    }
    return stringBuffer.toString();
  }

  public static final String codeToString(
      byte[] values, ConstantPool constantPool, int value, int value2, boolean matches) {
    StringBuffer stringBuffer = new StringBuffer(values.length * 20);
    ByteSequence byteSequence = new ByteSequence(values);
    try {
      int value3;
      for (int value4 = value3 = 0; value4 < value; value4 = value3) {
        value3++;
        codeToString(byteSequence, constantPool, matches);
      }
      for (int index = 0; byteSequence.available() > 0; index++) {
        if (value2 < 0 || index < value2) {
          String text = fillup(byteSequence.getIndex() + ":", 6, true, ' ');
          stringBuffer
              .append(text)
              .append(codeToString(byteSequence, constantPool, matches))
              .append('\n');
        }
      }
    } catch (IOException oException) {
      System.out.println(stringBuffer.toString());
      oException.printStackTrace();
      throw new ClassFormatException(("Byte code error: " + oException));
    }
    return stringBuffer.toString();
  }

  public static final byte calculateValue(String text) {
    try {
      if (text.charAt(0) != '(') {
        throw new ClassFormatException(("Invalid method signature: " + text));
      } else {
        return calculateValue2(text.substring(text.lastIndexOf(41) + 1));
      }
    } catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException) {
      throw new ClassFormatException(("Invalid method signature: " + text));
    }
  }

  public static final String fillup(String text, int value, boolean matches, char value2) {
    int value3;
    char[] values = new char[(value3 = value - text.length()) < 0 ? 0 : value3];
    int value4;
    for (int value5 = value4 = 0; value5 < values.length; value5 = value4) {
      int value6 = value4;
      value4++;
      values[value6] = value2;
    }
    return matches ? ("" + text + new String(values)) : ("" + new String(values) + text);
  }

  public static final String methodSignatureToString(
      String text,
      String text2,
      String text3,
      boolean matches,
      LocalVariableTable localVariableTable)
      throws ClassFormatException {
    StringBuffer stringBuffer = new StringBuffer("(");
    int value = text3.indexOf("static") >= 0 ? 0 : 1;
    String text4;
    try {
      if (text.charAt(0) != '(') {
        throw new ClassFormatException(("Invalid method signature: " + text));
      }
      int value2 = 1;
      for (String text5 = text; text5.charAt(value2) != ')'; text5 = text) {
        String text6 = signatureToString(text.substring(value2), matches);
        stringBuffer.append(text6);
        if (localVariableTable != null) {
          LocalVariable localVariable;
          if ((localVariable = localVariableTable.getLocalVariable(value)) != null) {
            stringBuffer.append(" ").append(localVariable.getText());
          }
        } else {
          stringBuffer.append(" arg").append(value);
        }
        StringBuffer stringBuffer2;
        if (!"double".equals(text6) && !"long".equals(text6)) {
          value++;
          stringBuffer2 = stringBuffer;
        } else {
          value += 2;
          stringBuffer2 = stringBuffer;
        }
        stringBuffer2.append(", ");
        value2 += unwrap(consumed_chars);
      }
      text4 = signatureToString(text.substring(++value2), matches);
    } catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException) {
      throw new ClassFormatException(("Invalid method signature: " + text));
    }
    if (stringBuffer.length() > 1) {
      stringBuffer.setLength(stringBuffer.length() - 2);
    }
    stringBuffer.append(")");
    return ""
        + text3
        + (text3.length() > 0 ? " " : "")
        + text4
        + " "
        + text2
        + stringBuffer.toString();
  }

  public static final String getText(int value) {
    return (value & 512) != 0 ? "interface" : "class";
  }

  public static final String methodSignatureToString(String text, String text2, String text3) {
    return methodSignatureToString(text, text2, text3, true);
  }

  public static final String accessToString(int value, boolean matches) {
    StringBuffer stringBuffer = new StringBuffer();
    int value2 = 0;
    int value3 = (byte) 0;
    while (value3 < 16384) {
      int value4 = pow2(value2);
      if ((value & value4) != 0 && (!matches || value4 != 32 && value4 != 512)) {
        stringBuffer.append(Constants.TEXTS9[value2]).append(" ");
      }
      value2++;
      value3 = value4;
    }
    return stringBuffer.toString().trim();
  }

  public static final String codeToString(
      byte[] values, ConstantPool constantPool, int value, int value2) {
    return codeToString(values, constantPool, value, value2, true);
  }

  public static final String getText(String text, boolean matches) {
    return compactClassName(text, "java.lang.", matches);
  }

  public static final String codeToString(ByteSequence byteSequence, ConstantPool constantPool)
      throws IOException {
    return codeToString(byteSequence, constantPool, true);
  }

  public static final String createText(String text) {
    char[] values = text.toCharArray();
    StringBuffer stringBuffer = new StringBuffer();
    int value;
    for (int value2 = value = 0; value2 < values.length; value2 = ++value) {
      switch (values[value]) {
        case '\n':
          stringBuffer.append("\\n");
          break;
        case '\r':
          stringBuffer.append("\\r");
          break;
        case '"':
          stringBuffer.append("\\\"");
          break;
        case '\'':
          stringBuffer.append("\\'");
          break;
        case '\\':
          stringBuffer.append("\\\\");
          break;
        default:
          stringBuffer.append(values[value]);
      }
    }
    return stringBuffer.toString();
  }

  public static String createText2(String text) {
    StringBuffer stringBuffer = new StringBuffer();
    char[] values = text.toCharArray();
    byte value = 0;
    byte value2 = 0;
    int value3 = -1;
    int value4;
    label58:
    for (int value5 = value4 = 0; value5 < values.length; value5 = ++value4) {
      switch (values[value4]) {
        case '\t':
        case '\n':
        case '\f':
        case '\r':
        case ' ':
          if (value != 0) {
            value2 = 1;
          }
          break;
        case '[':
          if (value == 0) {
            throw new RuntimeException(("Illegal type: " + text));
          }
          value3 = value4;
          break label58;
        default:
          value = 1;
          if (value2 == 0) {
            stringBuffer.append(values[value4]);
          }
      }
    }
    int value6 = 0;
    if (value3 > 0) {
      value6 = countBrackets(text.substring(value3));
    }
    String text2 = stringBuffer.toString();
    stringBuffer.setLength(0);
    int value7;
    for (int value8 = value7 = 0; value8 < value6; value8 = value7) {
      value7++;
      stringBuffer.append('[');
    }
    byte value9 = 0;
    int value10;
    for (int value11 = value10 = 4; value11 <= 12 && value9 == 0; value11 = ++value10) {
      if (Constants.TEXTS[value10].equals(text2)) {
        value9 = 1;
        stringBuffer.append(Constants.TEXTS3[value10]);
      }
    }
    if (value9 == 0) {
      stringBuffer.append('L').append(text2.replace('.', '/')).append(';');
    }
    return stringBuffer.toString();
  }

  public static final boolean isSet(int value, int value2) {
    return (value & pow2(value2)) != 0;
  }

  public static Attribute[] createAttributes(ConstantPoolGen constantPoolGen, List[] valuess) {
    int[] values = new int[valuess.length];
    int value = 0;
    int[] values2 = new int[valuess.length];
    int value2 = 0;
    try {
      int value3;
      for (int value4 = value3 = 0; value4 < valuess.length; value4 = ++value3) {
        List values3;
        if ((values3 = valuess[value3]) != null) {
          Iterator iterator2 = values3.iterator();
          while (iterator2.hasNext()) {
            if (((AnnotationEntryGen) iterator2.next()).isRuntimeVisible()) {
              int value5 = values[value3] + 1;
              int value6 = value3;
              value++;
              values[value6] = value5;
            } else {
              int value7 = values2[value3] + 1;
              int value8 = value3;
              value2++;
              values2[value8] = value7;
            }
          }
        }
      }
      ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
      DataOutputStream dataOutputStream;
      (dataOutputStream = new DataOutputStream(byteArrayOutputStream)).writeByte(valuess.length);
      int value9;
      for (int value10 = value9 = 0; value10 < valuess.length; value10 = ++value9) {
        dataOutputStream.writeShort(values[value9]);
        if (values[value9] > 0) {
          Iterator iterator3 = valuess[value9].iterator();
          while (iterator3.hasNext()) {
            AnnotationEntryGen annotationEntryGen;
            if ((annotationEntryGen = (AnnotationEntryGen) iterator3.next()).isRuntimeVisible()) {
              annotationEntryGen.dump(dataOutputStream);
            }
          }
        }
      }
      dataOutputStream.close();
      ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
      DataOutputStream dataOutputStream2;
      (dataOutputStream2 = new DataOutputStream(byteArrayOutputStream2)).writeByte(valuess.length);
      int value11;
      for (int value12 = value11 = 0; value12 < valuess.length; value12 = ++value11) {
        dataOutputStream2.writeShort(values2[value11]);
        if (values2[value11] > 0) {
          Iterator iterator4 = valuess[value11].iterator();
          while (iterator4.hasNext()) {
            AnnotationEntryGen annotationEntryGen2;
            if (!(annotationEntryGen2 = (AnnotationEntryGen) iterator4.next()).isRuntimeVisible()) {
              annotationEntryGen2.dump(dataOutputStream2);
            }
          }
        }
      }
      dataOutputStream2.close();
      byte[] values4 = byteArrayOutputStream.toByteArray();
      byte[] values5 = byteArrayOutputStream2.toByteArray();
      int value13 = -1;
      int value14 = -1;
      if (value > 0) {
        value13 = constantPoolGen.addUtf8("RuntimeVisibleParameterAnnotations");
      }
      if (value2 > 0) {
        value14 = constantPoolGen.addUtf8("RuntimeInvisibleParameterAnnotations");
      }
      ArrayList values6 = new ArrayList();
      if (value > 0) {
        values6.add(
            new RuntimeVisibleParameterAnnotations(
                value13,
                values4.length,
                new DataInputStream(new ByteArrayInputStream(values4)),
                constantPoolGen.getConstantPool()));
      }
      if (value2 > 0) {
        values6.add(
            new RuntimeInvisibleParameterAnnotations(
                value14,
                values5.length,
                new DataInputStream(new ByteArrayInputStream(values5)),
                constantPoolGen.getConstantPool()));
      }
      return (Attribute[]) values6.toArray(new Attribute[values6.size()]);
    } catch (IOException oException) {
      System.err.println("IOException whilst processing parameter annotations");
      oException.printStackTrace();
      return null;
    }
  }

  public static short searchOpcode(String text) {
    String text2 = text.toLowerCase(Locale.ENGLISH);
    int value;
    for (int value2 = value = 0; value2 < Constants.TEXTS8.length; value2 = ++value) {
      if (Constants.TEXTS8[value].equals(text2)) {
        return (short) value;
      }
    }
    return -1;
  }

  public static final String signatureToString(String text, boolean matches) {
    wrap(consumed_chars, 1);
    try {
      switch (text.charAt(0)) {
        case 'B':
          return "byte";
        case 'C':
          return "char";
        case 'D':
          return "double";
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
          throw new ClassFormatException(("Invalid signature: `" + text + "'"));
        case 'F':
          return "float";
        case 'I':
          return "int";
        case 'J':
          return "long";
        case 'L':
          int value;
          if ((value = text.indexOf(59)) < 0) {
            throw new ClassFormatException(("Invalid signature: " + text));
          }
          wrap(consumed_chars, value + 1);
          return getText(text.substring(1, value), matches);
        case 'S':
          return "short";
        case 'V':
          return "void";
        case 'Z':
          return "boolean";
        case '[':
          StringBuffer stringBuffer = new StringBuffer();
          int value2 = 0;
          String text2 = text;
          while (text2.charAt(value2) == '[') {
            value2++;
            text2 = text;
            stringBuffer.append("[]");
          }
          int value3 = value2;
          String text3 = signatureToString(text.substring(value2), matches);
          int value4 = unwrap(consumed_chars) + value3;
          wrap(consumed_chars, value4);
          return "" + text3 + stringBuffer.toString();
      }
    } catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException) {
      throw new ClassFormatException(
          ("Invalid signature: " + stringIndexOutOfBoundsException + ":" + text));
    }
  }

  public static final String replace(String text, String text2, String text3) {
    String text4 = text;
    StringBuffer stringBuffer = new StringBuffer();
    try {
      if (text4.indexOf(text2) != -1) {
        int value = 0;
        String text5 = text4;
        int value2;
        while ((value2 = text5.indexOf(text2, value)) != -1) {
          text5 = text4;
          stringBuffer.append(text4.substring(value, value2));
          stringBuffer.append(text3);
          value = value2 + text2.length();
        }
        stringBuffer.append(text4.substring(value));
        text4 = stringBuffer.toString();
      }
    } catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException) {
      System.err.println(stringIndexOutOfBoundsException);
    }
    return text4;
  }

  public static final String getText2(int value) {
    return accessToString(value, false);
  }

  public static final String[] methodSignatureArgumentTypes(String text, boolean matches)
      throws ClassFormatException {
    ArrayList values = new ArrayList();
    try {
      if (text.charAt(0) != '(') {
        throw new ClassFormatException(("Invalid method signature: " + text));
      }
      for (int index = 1; text.charAt(index) != ')'; index += unwrap(consumed_chars)) {
        values.add(signatureToString(text.substring(index), matches));
      }
    } catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException) {
      throw new ClassFormatException(("Invalid method signature: " + text));
    }
    return (String[]) values.toArray(new String[values.size()]);
  }

  public static byte[] decode(String text, boolean matches) throws IOException {
    char[] values = text.toCharArray();
    CharArrayReader charArrayReader = new CharArrayReader(values);
    Utility$JavaReader utility$JavaReader = new Utility$JavaReader(charArrayReader);
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    Utility$JavaReader utility$JavaReader2 = utility$JavaReader;
    int value;
    while ((value = utility$JavaReader2.read()) >= 0) {
      utility$JavaReader2 = utility$JavaReader;
      byteArrayOutputStream.write(value);
    }
    byteArrayOutputStream.close();
    charArrayReader.close();
    utility$JavaReader.close();
    byte[] values2 = byteArrayOutputStream.toByteArray();
    if (matches) {
      GZIPInputStream gZIPInputStream = new GZIPInputStream(new ByteArrayInputStream(values2));
      byte[] values3 = new byte[values2.length * 3];
      int value2 = 0;
      GZIPInputStream gZIPInputStream2 = gZIPInputStream;
      int value3;
      while ((value3 = gZIPInputStream2.read()) >= 0) {
        byte value4 = (byte) value3;
        int value5 = value2;
        value2++;
        gZIPInputStream2 = gZIPInputStream;
        values3[value5] = value4;
      }
      values2 = new byte[value2];
      System.arraycopy(values3, 0, values2, 0, value2);
    }
    return values2;
  }

  public static final short byteToShort(byte value) {
    return value < 0 ? (short) (256 + value) : value;
  }

  public static int unwrap(ThreadLocal threadLocal) {
    return (Integer) threadLocal.get();
  }

  public static final String getText(String text) {
    return getText(text, true);
  }

  public static final byte calculateValue2(String text) {
    try {
      switch (text.charAt(0)) {
        case 'B':
          return 8;
        case 'C':
          return 5;
        case 'D':
          return 7;
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
        case 'U':
        case 'W':
        case 'X':
        case 'Y':
        default:
          throw new ClassFormatException(("Invalid method signature: " + text));
        case 'F':
          return 6;
        case 'I':
          return 10;
        case 'J':
          return 11;
        case 'L':
        case 'T':
          return 14;
        case 'S':
          return 9;
        case 'V':
          return 12;
        case 'Z':
          return 4;
        case '[':
          return 13;
      }
    } catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException) {
      throw new ClassFormatException(("Invalid method signature: " + text));
    }
  }

  public static final String format(int value, int value2, boolean matches, char value3) {
    return fillup(Integer.toString(value), value2, matches, value3);
  }

  public static final boolean equals(byte[] values, byte[] values2) {
    int value;
    if ((value = values.length) != values2.length) {
      return false;
    }
    int value2;
    for (int value3 = value2 = 0; value3 < value; value3 = ++value2) {
      if (values[value2] != values2[value2]) {
        return false;
      }
    }
    return true;
  }

  public static int countBrackets(String text) {
    char[] values = text.toCharArray();
    int value = 0;
    byte value2 = 0;
    int value3;
    for (int value4 = value3 = 0; value4 < values.length; value4 = ++value3) {
      switch (values[value3]) {
        case '[':
          if (value2 != 0) {
            throw new RuntimeException(("Illegally nested brackets:" + text));
          }
          value2 = 1;
          break;
        case ']':
          if (value2 == 0) {
            throw new RuntimeException(("Illegally nested brackets:" + text));
          }
          byte value5 = 0;
          value++;
          value2 = value5;
      }
    }
    if (value2 != 0) {
      throw new RuntimeException(("Illegally nested brackets:" + text));
    } else {
      return value;
    }
  }

  public static final int calculateCount(int value, int value2) {
    return value | pow2(value2);
  }

  public static final void printArray(PrintStream printStream, Object[] results) {
    printStream.println(printArray(results, true));
  }

  public static final String getText2(String text) {
    return signatureToString(text, true);
  }

  public static final int pow2(int value) {
    return 1 << value;
  }

  public static String encode(byte[] values, boolean matches) throws IOException {
    byte[] values2 = values;
    if (matches) {
      ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
      GZIPOutputStream gZIPOutputStream;
      (gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream))
          .write(values2, 0, values2.length);
      gZIPOutputStream.close();
      byteArrayOutputStream.close();
      values2 = byteArrayOutputStream.toByteArray();
    }
    CharArrayWriter charArrayWriter = new CharArrayWriter();
    Utility$JavaWriter utility$JavaWriter = new Utility$JavaWriter(charArrayWriter);
    int value;
    for (int value2 = value = 0; value2 < values2.length; value2 = value) {
      byte value3 = values2[value];
      value++;
      utility$JavaWriter.write(value3 & 255);
    }
    return charArrayWriter.toString();
  }

  public static final String createText(String text, boolean matches) {
    try {
      return signatureToString(text.substring(text.lastIndexOf(41) + 1), matches);
    } catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException) {
      throw new ClassFormatException(("Invalid method signature: " + text));
    }
  }

  public static Attribute[] createAttributes(ConstantPoolGen constantPoolGen, List values) {
    if (values.size() == 0) {
      return null;
    }
    try {
      int value = 0;
      int value2 = 0;
      int value3;
      for (int value4 = value3 = 0; value4 < values.size(); value4 = ++value3) {
        if (((AnnotationEntryGen) values.get(value3)).isRuntimeVisible()) {
          value++;
        } else {
          value2++;
        }
      }
      ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
      ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
      DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
      DataOutputStream dataOutputStream2 = new DataOutputStream(byteArrayOutputStream2);
      int value5 = 0;
      dataOutputStream.writeShort(value);
      dataOutputStream2.writeShort(value2);
      for (int index = 0; value5 < values.size(); value5 = ++index) {
        AnnotationEntryGen annotationEntryGen;
        if ((annotationEntryGen = (AnnotationEntryGen) values.get(index)).isRuntimeVisible()) {
          annotationEntryGen.dump(dataOutputStream);
        } else {
          annotationEntryGen.dump(dataOutputStream2);
        }
      }
      dataOutputStream.close();
      dataOutputStream2.close();
      byte[] values2 = byteArrayOutputStream.toByteArray();
      byte[] values3 = byteArrayOutputStream2.toByteArray();
      int value6 = -1;
      int value7 = -1;
      if (values2.length > 2) {
        value6 = constantPoolGen.addUtf8("RuntimeVisibleAnnotations");
      }
      if (values3.length > 2) {
        value7 = constantPoolGen.addUtf8("RuntimeInvisibleAnnotations");
      }
      ArrayList values4 = new ArrayList();
      if (values2.length > 2) {
        values4.add(
            new RuntimeVisibleAnnotations(
                value6,
                values2.length,
                new DataInputStream(new ByteArrayInputStream(values2)),
                constantPoolGen.getConstantPool()));
      }
      if (values3.length > 2) {
        values4.add(
            new RuntimeInvisibleAnnotations(
                value7,
                values3.length,
                new DataInputStream(new ByteArrayInputStream(values3)),
                constantPoolGen.getConstantPool()));
      }
      return (Attribute[]) values4.toArray(new Attribute[values4.size()]);
    } catch (IOException oException) {
      System.err.println("IOException whilst processing annotations");
      oException.printStackTrace();
      return null;
    }
  }

  public static final String methodTypeToSignature(String text, String[] texts)
      throws ClassFormatException {
    StringBuffer stringBuffer = new StringBuffer("(");
    int value;
    if (texts != null) {
      for (int value2 = value = 0; value2 < texts.length; value2 = value) {
        String text2;
        if ((text2 = createText2(texts[value])).endsWith("V")) {
          throw new ClassFormatException(("Invalid type: " + texts[value]));
        }
        value++;
        stringBuffer.append(text2);
      }
    }
    String text3 = createText2(text);
    stringBuffer.append(")").append(text3);
    return stringBuffer.toString();
  }

  public static final int calculateCount2(int value, int value2) {
    int value3 = pow2(value2);
    return (value & value3) == 0 ? value : value ^ value3;
  }

  public static final String compactClassName(String text, String text2, boolean matches) {
    int size = text2.length();
    String text3 = text.replace('/', '.');
    if (matches && text3.startsWith(text2) && text3.substring(size).indexOf(46) == -1) {
      text3 = text3.substring(size);
    }
    return text3;
  }

  public static final void printArray(PrintWriter printWriter, Object[] results) {
    printWriter.println(printArray(results, true));
  }

  public static final String[] methodSignatureArgumentTypes(String text)
      throws ClassFormatException {
    return methodSignatureArgumentTypes(text, true);
  }

  public static final String getText3(String text) {
    return createText(text, true);
  }

  public static final String toHexString(byte[] values) {
    StringBuffer stringBuffer = new StringBuffer();
    int value;
    for (int value2 = value = 0; value2 < values.length; value2 = ++value) {
      short value3;
      String text = Integer.toString(value3 = byteToShort(values[value]), 16);
      if (value3 < 16) {
        stringBuffer.append('0');
      }
      stringBuffer.append(text);
      if (value < values.length - 1) {
        stringBuffer.append(' ');
      }
    }
    return stringBuffer.toString();
  }

  public static final String codeToString(
      ByteSequence byteSequence, ConstantPool constantPool, boolean matches) throws IOException {
    short value = (short) byteSequence.readUnsignedByte();
    int value2 = 0;
    byte value3 = 0;
    StringBuffer stringBuffer = new StringBuffer(Constants.TEXTS8[value]);
    if (value == 170 || value == 171) {
      int value4;
      value3 = (byte) ((value4 = byteSequence.getIndex() % 4) == 0 ? 0 : 4 - value4);
      int value5;
      for (int value6 = value5 = 0; value6 < value3; value6 = ++value5) {
        byte value7;
        if ((value7 = byteSequence.readByte()) != 0) {
          System.err.println(
              "Warning: Padding byte != 0 in " + Constants.TEXTS8[value] + ":" + value7);
        }
      }
      value2 = byteSequence.readInt();
    }
    switch (value) {
      case 18:
        int value8 = byteSequence.readUnsignedByte();
        stringBuffer
            .append("\t\t")
            .append(constantPool.getText(value8, constantPool.getConstant(value8).getTag()))
            .append(matches ? (" (" + value8 + ")") : "");
        return stringBuffer.toString();
      case 19:
      case 20:
        int value9 = byteSequence.readUnsignedShort();
        stringBuffer
            .append("\t\t")
            .append(constantPool.getText(value9, constantPool.getConstant(value9).getTag()))
            .append(matches ? (" (" + value9 + ")") : "");
        return stringBuffer.toString();
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
        int value10;
        StringBuffer stringBuffer2;
        if (wide) {
          value10 = byteSequence.readUnsignedShort();
          wide = false;
          stringBuffer2 = stringBuffer;
        } else {
          value10 = byteSequence.readUnsignedByte();
          stringBuffer2 = stringBuffer;
        }
        stringBuffer2.append("\t\t%").append(value10);
        return stringBuffer.toString();
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
      default:
        int value11;
        if (Constants.NO_OF_OPERANDS[value] > 0) {
          for (int value12 = value11 = 0;
              value12 < Constants.TYPE_OF_OPERANDS[value].length;
              value12 = ++value11) {
            stringBuffer.append("\t\t");
            switch (Constants.TYPE_OF_OPERANDS[value][value11]) {
              case 8:
                stringBuffer.append(byteSequence.readByte());
                break;
              case 9:
                stringBuffer.append(byteSequence.readShort());
                break;
              case 10:
                stringBuffer.append(byteSequence.readInt());
                break;
              default:
                System.err.println("Unreachable default case reached!");
                System.exit(-1);
            }
          }
        }
        break;
      case 132:
        short value13;
        int value14;
        StringBuffer stringBuffer3;
        if (wide) {
          value14 = byteSequence.readUnsignedShort();
          stringBuffer3 = stringBuffer;
          value13 = byteSequence.readShort();
          wide = false;
        } else {
          value14 = byteSequence.readUnsignedByte();
          stringBuffer3 = stringBuffer;
          value13 = byteSequence.readByte();
        }
        stringBuffer3.append("\t\t%").append(value14).append("\t").append(value13);
        return stringBuffer.toString();
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
        stringBuffer.append("\t\t#").append(byteSequence.getIndex() - 1 + byteSequence.readShort());
        return stringBuffer.toString();
      case 170:
        int value15 = byteSequence.readInt();
        int value16 = byteSequence.readInt();
        int value17 = byteSequence.getIndex() - 12 - value3 - 1;
        int value18 = value2 + value17;
        stringBuffer
            .append("\tdefault = ")
            .append(value18)
            .append(", low = ")
            .append(value15)
            .append(", high = ")
            .append(value16)
            .append("(");
        int[] values = new int[value16 - value15 + 1];
        int value19 = 0;
        for (int value20 = value19; value20 < values.length; value20 = ++value19) {
          values[value19] = value17 + byteSequence.readInt();
          stringBuffer.append(values[value19]);
          if (value19 < values.length - 1) {
            stringBuffer.append(", ");
          }
        }
        stringBuffer.append(")");
        return stringBuffer.toString();
      case 171:
        int value21 = byteSequence.readInt();
        int value22 = byteSequence.getIndex() - 8 - value3 - 1;
        int[] values2 = new int[value21];
        int[] values3 = new int[value21];
        int value23 = value2 + value22;
        stringBuffer
            .append("\tdefault = ")
            .append(value23)
            .append(", npairs = ")
            .append(value21)
            .append(" (");
        int value24 = 0;
        for (int value25 = value24; value25 < value21; value25 = ++value24) {
          values2[value24] = byteSequence.readInt();
          values3[value24] = value22 + byteSequence.readInt();
          stringBuffer
              .append("(")
              .append(values2[value24])
              .append(", ")
              .append(values3[value24])
              .append(")");
          if (value24 < value21 - 1) {
            stringBuffer.append(", ");
          }
        }
        stringBuffer.append(")");
        return stringBuffer.toString();
      case 178:
      case 179:
      case 180:
      case 181:
        int value26 = byteSequence.readUnsignedShort();
        stringBuffer
            .append("\t\t")
            .append(constantPool.getText(value26, (byte) 9))
            .append(matches ? (" (" + value26 + ")") : "");
        return stringBuffer.toString();
      case 182:
      case 183:
      case 184:
        int value27 = byteSequence.readUnsignedShort();
        stringBuffer
            .append("\t")
            .append(constantPool.getText(value27, (byte) 10))
            .append(matches ? (" (" + value27 + ")") : "");
        return stringBuffer.toString();
      case 185:
        int value28 = byteSequence.readUnsignedShort();
        int value29 = byteSequence.readUnsignedByte();
        stringBuffer
            .append("\t")
            .append(constantPool.getText(value28, (byte) 11))
            .append(matches ? (" (" + value28 + ")\t") : "")
            .append(value29)
            .append("\t")
            .append(byteSequence.readUnsignedByte());
        return stringBuffer.toString();
      case 186:
        int value30 = byteSequence.readUnsignedShort();
        byteSequence.readUnsignedShort();
        ConstantInvokeDynamic constantInvokeDynamic =
            (ConstantInvokeDynamic) constantPool.getConstant(value30, (byte) 18);
        stringBuffer
            .append("\t")
            .append("<dyn>.")
            .append(
                constantPool.getText(
                    constantInvokeDynamic.getBootstrapMethodAttrIndex2(), (byte) 12));
        if (matches) {
          stringBuffer.append(
              " (" + value30 + "/" + constantInvokeDynamic.getBootstrapMethodAttrIndex2() + ")");
          return stringBuffer.toString();
        }
        break;
      case 187:
      case 192:
        stringBuffer.append("\t");
      case 193:
        int value31 = byteSequence.readUnsignedShort();
        stringBuffer
            .append("\t<")
            .append(constantPool.getText(value31, (byte) 7))
            .append(">")
            .append(matches ? (" (" + value31 + ")") : "");
        return stringBuffer.toString();
      case 188:
        stringBuffer.append("\t\t<").append(Constants.TEXTS[byteSequence.readByte()]).append(">");
        return stringBuffer.toString();
      case 189:
        int value32 = byteSequence.readUnsignedShort();
        stringBuffer
            .append("\t\t<")
            .append(getText(constantPool.createText(value32, (byte) 7), false))
            .append(">")
            .append(matches ? (" (" + value32 + ")") : "");
        return stringBuffer.toString();
      case 196:
        wide = true;
        stringBuffer.append("\t(wide)");
        return stringBuffer.toString();
      case 197:
        int value33 = byteSequence.readUnsignedShort();
        int value34 = byteSequence.readUnsignedByte();
        stringBuffer
            .append("\t<")
            .append(getText(constantPool.createText(value33, (byte) 7), false))
            .append(">\t")
            .append(value34)
            .append(matches ? (" (" + value33 + ")") : "");
        return stringBuffer.toString();
      case 200:
      case 201:
        stringBuffer.append("\t\t#").append(byteSequence.getIndex() - 1 + byteSequence.readInt());
        return stringBuffer.toString();
    }
    return stringBuffer.toString();
  }

  public static void wrap(ThreadLocal threadLocal, int value) {
    threadLocal.set(new Integer(value));
  }

  public static final String printArray(Object[] results, boolean matches) {
    return printArray(results, matches, false);
  }

  public static final String printArray(Object[] results) {
    return printArray(results, true);
  }

  public static boolean isJavaIdentifierPart(char value) {
    return value >= 'a' && value <= 'z'
        || value >= 'A' && value <= 'Z'
        || value >= '0' && value <= '9'
        || value == '_';
  }

  public static final String methodSignatureToString(
      String text, String text2, String text3, boolean matches) {
    return methodSignatureToString(text, text2, text3, matches, null);
  }
}
