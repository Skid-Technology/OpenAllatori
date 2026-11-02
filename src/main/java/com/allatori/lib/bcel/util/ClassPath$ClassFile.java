package com.allatori.lib.bcel.util;

import java.io.IOException;
import java.io.InputStream;

public interface ClassPath$ClassFile {

  InputStream getInputStream() throws IOException;

  String getText();

  String getText2();

  long calculateSeed();

  long calculateSeed2();
}
