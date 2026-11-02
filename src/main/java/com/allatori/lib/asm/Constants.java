package com.allatori.lib.asm;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Pattern;

public final class Constants {

  public static final String TEXT8 = "ConstantValue";

  public static final String TEXT26 = "Code";

  public static final String TEXT17 = "StackMapTable";

  public static final String TEXT10 = "Exceptions";

  public static final String TEXT5 = "InnerClasses";

  public static final String TEXT25 = "EnclosingMethod";

  public static final String TEXT29 = "Synthetic";

  public static final String TEXT3 = "Signature";

  public static final String TEXT15 = "SourceFile";

  public static final String TEXT14 = "SourceDebugExtension";

  public static final String TEXT13 = "LineNumberTable";

  public static final String TEXT27 = "LocalVariableTable";

  public static final String TEXT30 = "LocalVariableTypeTable";

  public static final String TEXT2 = "Deprecated";

  public static final String TEXT20 = "RuntimeVisibleAnnotations";

  public static final String TEXT7 = "RuntimeInvisibleAnnotations";

  public static final String TEXT = "RuntimeVisibleParameterAnnotations";

  public static final String TEXT23 = "RuntimeInvisibleParameterAnnotations";

  public static final String TEXT21 = "RuntimeVisibleTypeAnnotations";

  public static final String TEXT9 = "RuntimeInvisibleTypeAnnotations";

  public static final String TEXT22 = "AnnotationDefault";

  public static final String TEXT11 = "BootstrapMethods";

  public static final String TEXT4 = "MethodParameters";

  public static final String TEXT19 = "Module";

  public static final String TEXT16 = "ModulePackages";

  public static final String TEXT18 = "ModuleMainClass";

  public static final String TEXT24 = "NestHost";

  public static final String TEXT12 = "NestMembers";

  public static final String TEXT28 = "PermittedSubclasses";

  public static final String TEXT6 = "Record";

  public static final int FSTORE_3 = 70;

  public static final int ILOAD_1 = 27;

  public static final int LSTORE_3 = 66;

  public static final int COUNT = 20;

  public static final int LSTORE_2 = 65;

  public static final int ASM_GOTO_W = 220;

  public static final int ASM_IFNE = 203;

  public static final int ASM_JSR = 217;

  public static final int ILOAD_3 = 29;

  public static final int FLOAD_3 = 37;

  public static final int ALOAD_0 = 42;

  public static final int ASM_IF_ICMPGT = 212;

  public static final int FLOAD_0 = 34;

  public static final int LSTORE_0 = 63;

  public static final int DLOAD_3 = 41;

  public static final int COUNT2 = 33;

  public static final int ASM_IF_ACMPNE = 215;

  public static final int ASM_IFGE = 205;

  public static final int DSTORE_0 = 71;

  public static final int F_INSERT = 256;

  public static final int ACC_CONSTRUCTOR = 262144;

  public static final int ASTORE_2 = 77;

  public static final int ASM_IF_ICMPLT = 210;

  public static final int ASM_IF_ICMPLE = 213;

  public static final int COUNT3 = 20;

  public static final int ASM_IF_ACMPEQ = 214;

  public static final int GOTO_W = 200;

  public static final int LLOAD_0 = 30;

  public static final int ASM_IFNONNULL = 219;

  public static final int FLOAD_2 = 36;

  public static final int ILOAD_0 = 26;

  public static final int DSTORE_3 = 74;

  public static final int ASM_IF_ICMPEQ = 208;

  public static final int ASM_IFEQ = 202;

  public static final int JSR_W = 201;

  public static final int DLOAD_2 = 40;

  public static final int DLOAD_1 = 39;

  public static final int ASTORE_1 = 76;

  public static final int LLOAD_1 = 31;

  public static final int FLOAD_1 = 35;

  public static final int ASM_IFGT = 206;

  public static final int LSTORE_1 = 64;

  public static final int ASM_IF_ICMPNE = 209;

  public static final int ISTORE_3 = 62;

  public static final int DSTORE_1 = 72;

  public static final int ASTORE_3 = 78;

  public static final int ILOAD_2 = 28;

  public static final int FSTORE_0 = 67;

  public static final int ISTORE_1 = 60;

  public static final int ASM_IF_ICMPGE = 211;

  public static final int DLOAD_0 = 38;

  public static final int FSTORE_2 = 69;

  public static final int ASM_OPCODE_DELTA = 49;

  public static final int COUNT4 = 33;

  public static final int ASM_IFLT = 204;

  public static final int ASM_IFNULL = 218;

  public static final int DSTORE_2 = 73;

  public static final int FSTORE_1 = 68;

  public static final int LDC_W = 19;

  public static final int ASTORE_0 = 75;

  public static final int ISTORE_0 = 59;

  public static final int WIDE = 196;

  public static final int LLOAD_2 = 32;

  public static final int ALOAD_1 = 43;

  public static final int ALOAD_2 = 44;

  public static final int ASM_GOTO = 216;

  public static final int ASM_IFLE = 207;

  public static final int ISTORE_2 = 61;

  public static final int ALOAD_3 = 45;

  public static String createText(String text) {
    return "" + text + ".class";
  }

  public static boolean isWhitelisted(String text) {
    if (!text.startsWith("org/objectweb/asm/")) {
      return false;
    }
    String text2 = "(Annotation|Class|Field|Method|Module|RecordComponent|Signature)";
    return text.contains("Test$")
        || Pattern.matches(createText3(text2), text)
        || Pattern.matches(createText2(text2), text);
  }

  public static void checkAsmExperimental(Object result) {
    Class classValue;
    String text;
    if (!isWhitelisted(text = (classValue = result.getClass()).getName().replace('.', '/'))) {
      checkIsPreview(classValue.getClassLoader().getResourceAsStream(createText(text)));
    }
  }

  public static String createText2(String text) {
    return "org/objectweb/asm/util/Check" + text + "Adapter(\\$.*)?";
  }

  public static void checkIsPreview(InputStream inputStream) {
    if (inputStream == null) {
      throw new IllegalStateException("Bytecode not available, can't check class version");
    }
    int value;
    try {
      DataInputStream dataInputStream = new DataInputStream(inputStream);
      try {
        dataInputStream.readInt();
        value = dataInputStream.readUnsignedShort();
      } catch (Throwable throwable) {
        try {
          dataInputStream.close();
        } catch (Throwable throwable2) {
          throw throwable;
        }
        throw throwable;
      }
      dataInputStream.close();
    } catch (IOException oException) {
      throw new IllegalStateException("I/O error, can't check class version", oException);
    }
    if (value != 65535) {
      throw new IllegalStateException(
          "ASM10_EXPERIMENTAL can only be used by classes compiled with --enable-preview");
    }
  }

  public static String createText3(String text) {
    return "org/objectweb/asm/util/Trace" + text + "Visitor(\\$.*)?";
  }
}
