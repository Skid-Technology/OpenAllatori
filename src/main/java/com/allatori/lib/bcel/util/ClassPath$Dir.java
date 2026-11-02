package com.allatori.lib.bcel.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class ClassPath$Dir extends ClassPath$PathEntry {

  public String dir;

  @Override
  public URL getResource(String text) {
    File file =
        new File(("" + this.dir + File.separatorChar + text.replace('/', File.separatorChar)));
    try {
      return file.exists() ? file.toURL() : null;
    } catch (MalformedURLException malformedURLException) {
      return null;
    }
  }

  @Override
  public ClassPath$ClassFile getClassFile(String text, String text2) throws IOException {
    File file;
    return (file =
                new File(
                    (""
                        + this.dir
                        + File.separatorChar
                        + text.replace('.', File.separatorChar)
                        + text2)))
            .exists()
        ? new ClassPath$Dir$1(this, file)
        : null;
  }

  @Override
  public InputStream getResourceAsStream(String text) {
    File file =
        new File(("" + this.dir + File.separatorChar + text.replace('/', File.separatorChar)));
    try {
      return file.exists() ? new FileInputStream(file) : null;
    } catch (IOException oException) {
      return null;
    }
  }

  public static String access$100(ClassPath$Dir classPath$Dir) {
    return classPath$Dir.dir;
  }

  public ClassPath$Dir(String dir) {
    super(null);
    this.dir = dir;
  }
}
