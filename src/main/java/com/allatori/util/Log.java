package com.allatori.util;

import com.allatori.naming.MappingXmlWriter;

public class Log {

  public static boolean silent = false;

  public static void error(String text) {
    MappingXmlWriter.stringBuilder.append("[ERROR] " + text + "\n");
    System.out.println("[ERROR] " + text);
  }

  public static void info(String text) {
    if (!silent) {
      MappingXmlWriter.stringBuilder.append("[INFO] " + text + "\n");
      System.out.println("[INFO] " + text);
    }
  }

  public static boolean isSilent() {
    return silent;
  }

  public static void warn(String text) {
    if (!silent) {
      MappingXmlWriter.stringBuilder.append("[WARNING] " + text + "\n");
      System.out.println("[WARNING] " + text);
    }
  }

  public static void info2(String text) {
    if (!silent) {
      System.out.println("[INFO] " + text);
    }
  }

  public static void setSilent(boolean matches) {
    silent = matches;
  }

  public static void debug(String text) {
    if (!silent) {
      System.out.println("[DEBUG] " + text);
    }
  }
}
