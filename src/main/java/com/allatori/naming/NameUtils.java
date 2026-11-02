package com.allatori.naming;

import com.allatori.config.Configuration;
import com.allatori.lib.bcel.classfile.AccessFlags;
import com.allatori.lib.bcel.generic.ArrayType;
import com.allatori.lib.bcel.generic.ObjectType;
import com.allatori.lib.bcel.generic.ReferenceType;
import com.allatori.lib.bcel.generic.Type;
import com.allatori.util.LookupTable;

public class NameUtils {

  public static String getText(String text) {
    return getText4(text);
  }

  public static boolean matchesAccessFlags(AccessFlags accessFlags) {
    return !accessFlags.isEnabled6() && !accessFlags.isEnabled13() && !accessFlags.isEnabled9();
  }

  public static String getText2(String text) {
    return getText3(text);
  }

  public static boolean matchesAccessFlags2(AccessFlags accessFlags) {
    return matchesAccessFlags(accessFlags);
  }

  public static String getText3(String text) {
    int value;
    return (value = text.lastIndexOf(46)) == -1 ? "" : text.substring(0, value);
  }

  public static void processAccessFlags(AccessFlags accessFlags) {
    processAccessFlags2(accessFlags);
  }

  public static Type getType(Type type, LookupTable lookupTable) {
    return createType(type, lookupTable);
  }

  public static String getText4(String text) {
    return !Configuration.getRenamingOptions().isCompatibleRenamingEnabled()
            && !Configuration.getRenamingOptions().isEnabled8()
        ? text
        : text.substring(0, text.lastIndexOf(41));
  }

  public static void processAccessFlags2(AccessFlags accessFlags) {
    accessFlags.processEnabled5(false);
    accessFlags.processEnabled7(false);
    accessFlags.processEnabled13(true);
  }

  public static Type createType(Type type, LookupTable lookupTable) {
    ReferenceType referenceType = null;
    if (type instanceof ObjectType) {
      String className = ((ObjectType) type).getClassName();
      String text;
      if ((text = lookupTable.getText(className)) != null && !text.equals(className)) {
        referenceType = new ObjectType(text);
      }
    } else {
      Type type2;
      ArrayType arrayType;
      if (type instanceof ArrayType
          && (type2 = (arrayType = (ArrayType) type).getBasicType()) instanceof ObjectType) {
        String className2 = ((ObjectType) type2).getClassName();
        String text2;
        if ((text2 = lookupTable.getText(className2)) != null && !text2.equals(className2)) {
          referenceType = new ArrayType(text2, arrayType.getDimensions());
        }
      }
    }
    return referenceType;
  }
}
