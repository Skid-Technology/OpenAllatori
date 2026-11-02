package com.allatori.lib.bcel.util;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Locale;

public class ClassPath$1 implements FilenameFilter {

  @Override
  public boolean accept(File file, String text) {
    String text2;
    return (text2 = text.toLowerCase(Locale.ENGLISH)).endsWith(".zip") || text2.endsWith(".jar");
  }
}
