package com.allatori.config.rule;

import com.allatori.lib.bcel.classfile.AccessFlags;
import com.allatori.lib.bcel.classfile.AnnotationEntry;
import com.allatori.lib.bcel.generic.AnnotationEntryGen;
import com.allatori.lib.bcel.generic.ClassGen;
import com.allatori.lib.bcel.generic.Type;
import com.allatori.model.ClassStorage;
import com.allatori.util.Log;

public class TemplateUtils {

  public static final int COUNT = 2048;

  public static final int COUNT2 = 512;

  public static final int COUNT3 = 32;

  public static final int COUNT4 = 8192;

  public static final int COUNT5 = 128;

  public static final int COUNT6 = 4;

  public static final int COUNT7 = 256;

  public static final int COUNT8 = 1;

  public static final int COUNT9 = 2;

  public static final int COUNT10 = 8;

  public static final int COUNT11 = 1024;

  public static final int COUNT12 = 16;

  public static final int COUNT13 = 4096;

  public static final int COUNT14 = 64;

  public static boolean matches(String text) {
    if (text.equals("public")) {
      return true;
    } else if (text.equals("protected")) {
      return true;
    } else if (text.equals("package")) {
      return true;
    } else if (text.equals("private")) {
      return true;
    } else if (text.equals("protected+")) {
      return true;
    } else {
      return text.equals("package+") ? true : text.equals("private+");
    }
  }

  public static boolean matches(String[] texts, String[] texts2) {
    int value;
    int value2;
    for (int value3 = value2 = texts2.length - 1; value3 >= 0; value3 = --value2) {
      byte value4 = 0;
      int value5;
      value3 = value5 = texts.length - 1;
      while (true) {
        if (value3 < 0) {
          value = value4;
          break;
        }
        if (texts[value5].matches(texts2[value2])) {
          value = 1;
          break;
        }
        value3 = --value5;
      }
      if (value == 0) {
        return false;
      }
    }
    return true;
  }

  public static String getText(String text) {
    return getText2(text);
  }

  public static String getText2(String text) {
    return text == null ? ".*" : getText3(text.trim().replaceAll("\\$", "\\\\\\$"));
  }

  public static String getText3(String text) {
    return text.startsWith("regex:")
        ? text.substring(6)
        : text.replaceAll("\\.", "\\\\.").replaceAll("\\*", ".*");
  }

  public static boolean matches(AnnotationEntry[] annotationEntrys, String text) {
    if (text == null) {
      return true;
    }
    int value;
    for (int value2 = value = annotationEntrys.length - 1; value2 >= 0; value2 = --value) {
      if (annotationEntrys[value].getText().matches(text)) {
        return true;
      }
    }
    return false;
  }

  public static String[] getTexts(String text) {
    if (text == null) {
      return new String[0];
    }
    String[] texts;
    if ((texts = text.split("\\s*,\\s*")) == null) {
      return new String[0];
    }
    int value;
    for (int value2 = value = texts.length - 1; value2 >= 0; value2 = value) {
      texts[value] = texts[value].replaceAll("\\$", "\\\\\\$");
      String text3 = getText3(texts[value]);
      int value3 = value;
      String[] texts2 = texts;
      value--;
      texts2[value3] = text3;
    }
    return texts;
  }

  public static int calculateCount(String text) {
    String text2 = text.trim();
    short value = 0;
    if (text2.indexOf("public") >= 0) {
      value = 1;
    }
    if (text2.indexOf("protected") >= 0) {
      value |= 2;
    }
    if (text2.indexOf("package") >= 0) {
      value |= 4;
    }
    if (text2.indexOf("private") >= 0) {
      value |= 8;
    }
    if (text2.indexOf("protected+") >= 0) {
      value |= 3;
    }
    if (text2.indexOf("package+") >= 0) {
      value |= 7;
    }
    if (text2.indexOf("private+") >= 0) {
      value |= 15;
    }
    if (value == 0) {
      value = 15;
    }
    if (text2.indexOf("static") >= 0) {
      value |= 16;
    }
    if (text2.indexOf("final") >= 0) {
      value |= 32;
    }
    if (text2.indexOf("synchronized") >= 0) {
      value |= 64;
    }
    if (text2.indexOf("volatile") >= 0) {
      value |= 128;
    }
    if (text2.indexOf("transient") >= 0) {
      value |= 256;
    }
    if (text2.indexOf("native") >= 0) {
      value |= 512;
    }
    if (text2.indexOf("interface") >= 0) {
      value |= 1024;
    }
    if (text2.indexOf("abstract") >= 0) {
      value |= 2048;
    }
    if (text2.indexOf("strictfp") >= 0) {
      value |= 4096;
    }
    if (text2.indexOf("enum") >= 0) {
      value |= 8192;
    }
    return value;
  }

  public static boolean matches2(String text) {
    return text.charAt(0) == '@';
  }

  public static boolean matches3(String text) {
    if (text.indexOf("public") >= 0) {
      return true;
    } else if (text.indexOf("protected") >= 0) {
      return true;
    } else if (text.indexOf("package") >= 0) {
      return true;
    } else if (text.indexOf("private") >= 0) {
      return true;
    } else if (text.indexOf("static") >= 0) {
      return true;
    } else if (text.indexOf("final") >= 0) {
      return true;
    } else if (text.indexOf("synchronized") >= 0) {
      return true;
    } else if (text.indexOf("volatile") >= 0) {
      return true;
    } else if (text.indexOf("transient") >= 0) {
      return true;
    } else if (text.indexOf("native") >= 0) {
      return true;
    } else if (text.indexOf("interface") >= 0) {
      return true;
    } else if (text.indexOf("abstract") >= 0) {
      return true;
    } else {
      return text.indexOf("strictfp") >= 0 ? true : text.indexOf("enum") >= 0;
    }
  }

  public static boolean matches(AnnotationEntryGen[] annotationEntryGens, String text) {
    if (text == null) {
      return true;
    }
    int value;
    for (int value2 = value = annotationEntryGens.length - 1; value2 >= 0; value2 = --value) {
      try {
        if (annotationEntryGens[value].getText2().matches(text)) {
          return true;
        }
      } catch (ClassCastException classCastException) {
        throw classCastException;
      }
    }
    return false;
  }

  public static boolean matches(ClassStorage classStorage, String text, String text2) {
    if (text == null) {
      return false;
    }
    if (text.matches(text2)) {
      return true;
    }
    ClassGen classGen;
    if ((classGen = classStorage.getClassGen(text)) != null) {
      int value;
      String[] texts;
      for (int value2 = value = (texts = classGen.getInterfaceNames()).length - 1;
          value2 >= 0;
          value2 = --value) {
        if (matches(classStorage, texts[value], text2)) {
          return true;
        }
      }
      return matches(classStorage, classGen.getSuperclassName(), text2);
    } else {
      try {
        int value3;
        Class classValue;
        Class[] classs;
        for (int value4 =
                value3 =
                    (classs =
                                (classValue =
                                        Class.forName(text, false, classStorage.getClassLoader()))
                                    .getInterfaces())
                            .length
                        - 1;
            value4 >= 0;
            value4 = --value3) {
          if (matches(classStorage, classs[value3].getName(), text2)) {
            return true;
          }
        }
        return classValue.getSuperclass() != null
            ? matches(classStorage, classValue.getSuperclass().getName(), text2)
            : false;
      } catch (ClassNotFoundException classNotFoundException) {
        return true;
      } catch (SecurityException securityException) {
        Log.error("Cannot read class: " + text);
        throw securityException;
      }
    }
  }

  public static boolean matches(Type[] types, String text) {
    StringBuffer stringBuffer = new StringBuffer("");
    int value;
    for (int value2 = value = types.length - 1; value2 >= 0; value2 = value) {
      StringBuilder stringBuilder = new StringBuilder().append(types[value].toString());
      value--;
      stringBuffer.insert((byte) 0, stringBuilder.append((String) ",").toString());
    }
    if (stringBuffer.length() > 0) {
      stringBuffer.deleteCharAt(stringBuffer.length() - 1);
    }
    return stringBuffer.toString().matches(text);
  }

  public static String getText4(String text) {
    return text.replaceAll("\\s+", "")
        .replaceAll("\\[", "\\\\[")
        .replaceAll("]", "\\\\]")
        .replaceAll("\\.", "\\\\.")
        .replaceAll("\\*\\*", "%@#")
        .replaceAll("\\*", "[^,]*")
        .replaceAll("\\%\\@\\#", ".*");
  }

  public static boolean matches(AccessFlags accessFlags, int value) {
    if (accessFlags.isEnabled6()) {
      if ((value & 1) == 0) {
        return false;
      }
    } else if (accessFlags.isEnabled13()) {
      if ((value & 2) == 0) {
        return false;
      }
    } else if (accessFlags.isEnabled9()) {
      if ((value & 8) == 0) {
        return false;
      }
    } else if ((value & 4) == 0) {
      return false;
    }
    if ((value & 16) > 0 && !accessFlags.isEnabled11()) {
      return false;
    } else if ((value & 32) > 0 && !accessFlags.isEnabled3()) {
      return false;
    } else if ((value & 64) > 0 && !accessFlags.isEnabled12()) {
      return false;
    } else if ((value & 128) > 0 && !accessFlags.isEnabled()) {
      return false;
    } else if ((value & 256) > 0 && !accessFlags.isEnabled4()) {
      return false;
    } else if ((value & 512) > 0 && !accessFlags.isEnabled14()) {
      return false;
    } else if ((value & 1024) > 0 && !accessFlags.isEnabled5()) {
      return false;
    } else if ((value & 2048) > 0 && !accessFlags.isEnabled15()) {
      return false;
    } else {
      return (value & 4096) > 0 && !accessFlags.isEnabled7()
          ? false
          : (value & 8192) <= 0 || accessFlags.isEnabled10();
    }
  }
}
