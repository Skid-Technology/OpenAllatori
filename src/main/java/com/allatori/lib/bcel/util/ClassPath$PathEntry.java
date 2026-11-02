package com.allatori.lib.bcel.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;

public abstract class ClassPath$PathEntry implements Serializable {

  public ClassPath$PathEntry() {}

  public ClassPath$PathEntry(ClassPath$1 classPath$1) {
    this();
  }

  public abstract InputStream getResourceAsStream(String text);

  public abstract ClassPath$ClassFile getClassFile(String text, String text2) throws IOException;

  public abstract URL getResource(String text);
}
