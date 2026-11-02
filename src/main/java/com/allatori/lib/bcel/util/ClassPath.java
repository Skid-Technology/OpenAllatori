package com.allatori.lib.bcel.util;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.zip.ZipFile;

public class ClassPath implements Serializable {

  public ClassPath$PathEntry[] paths;

  public static final ClassPath SYSTEM_CLASS_PATH = new ClassPath();

  public String class_path;

  @Override
  public int hashCode() {
    return this.class_path.hashCode();
  }

  public ClassPath(String class_path) {
    this.class_path = class_path;
    ArrayList values = new ArrayList();
    StringTokenizer stringTokenizer =
        new StringTokenizer(class_path, System.getProperty("path.separator"));
    while (stringTokenizer.hasMoreTokens()) {
      String text2;
      if (!(text2 = stringTokenizer.nextToken()).equals("")) {
        File file = new File(text2);
        try {
          if (file.exists()) {
            if (file.isDirectory()) {
              values.add(new ClassPath$Dir(text2));
            } else {
              values.add(new ClassPath$Zip(new ZipFile(file)));
            }
          }
        } catch (IOException oException) {
          System.err.println("CLASSPATH component " + file + ": " + oException);
        }
      }
    }
    this.paths = new ClassPath$PathEntry[values.size()];
    values.toArray(this.paths);
  }

  public static final void getPathComponents(String text, List values) {
    if (text != null) {
      StringTokenizer stringTokenizer = new StringTokenizer(text, File.pathSeparator);
      while (stringTokenizer.hasMoreTokens()) {
        String text2 = stringTokenizer.nextToken();
        if (new File(text2).exists()) {
          values.add(text2);
        }
      }
    }
  }

  public byte[] getBytes(String text) throws IOException {
    return this.getBytes(text, ".class");
  }

  public InputStream getInputStream(String text, String text2) throws IOException {
    InputStream inputStream = null;
    try {
      inputStream = this.getClass().getClassLoader().getResourceAsStream("" + text + text2);
    } catch (Exception exception) {
      return inputStream != null ? inputStream : this.getClassFile(text, text2).getInputStream();
    }
    return inputStream != null ? inputStream : this.getClassFile(text, text2).getInputStream();
  }

  public byte[] getBytes(String text, String text2) throws IOException {
    DataInputStream dataInputStream = null;
    boolean matches = false;
    byte[] values;
    try {
      matches = true;
      InputStream inputStream;
      if ((inputStream = this.getInputStream(text, text2)) == null) {
        throw new IOException(("Couldn't find: " + text + text2));
      }
      dataInputStream = new DataInputStream(inputStream);
      byte[] values2 = new byte[inputStream.available()];
      dataInputStream.readFully(values2);
      values = values2;
      matches = false;
    } finally {
      if (matches) {
        if (dataInputStream != null) {
          dataInputStream.close();
        }
      }
    }
    dataInputStream.close();
    return values;
  }

  public InputStream getInputStream(String text) throws IOException {
    int value;
    for (int value2 = value = 0; value2 < this.paths.length; value2 = ++value) {
      InputStream inputStream;
      if ((inputStream = this.paths[value].getResourceAsStream(text)) != null) {
        return inputStream;
      }
    }
    return null;
  }

  public InputStream getInputStream2(String text) throws IOException {
    return this.getInputStream(text.replace('.', '/'), ".class");
  }

  public static final String getClassPath() {
    String property = System.getProperty("java.class.path");
    String property2 = System.getProperty("sun.boot.class.path");
    String property3 = System.getProperty("java.ext.dirs");
    ArrayList values = new ArrayList();
    getPathComponents(property, values);
    getPathComponents(property2, values);
    ArrayList values2 = new ArrayList();
    getPathComponents(property3, values2);
    Iterator iterator2 = values2.iterator();
    while (iterator2.hasNext()) {
      File file = new File((String) iterator2.next());
      int value;
      String[] texts;
      if ((texts = file.list(new ClassPath$1())) != null) {
        for (int value2 = value = 0; value2 < texts.length; value2 = value) {
          StringBuilder stringBuilder =
              new StringBuilder().insert(0, file.getPath()).append(File.separatorChar);
          String text = texts[value];
          value++;
          values.add(stringBuilder.append(text).toString());
        }
      }
    }
    StringBuffer stringBuffer = new StringBuffer();
    Iterator iterator3 = values.iterator();
    label23:
    while (true) {
      Iterator iterator4 = iterator3;
      while (iterator4.hasNext()) {
        stringBuffer.append((String) iterator3.next());
        if (!iterator3.hasNext()) {
          continue label23;
        }
        iterator4 = iterator3;
        stringBuffer.append(File.pathSeparatorChar);
      }
      return stringBuffer.toString().intern();
    }
  }

  public String getPath(String text) throws IOException {
    int value = text.lastIndexOf(46);
    String text2 = "";
    if (value > 0) {
      text2 = text.substring(value);
      text = text.substring(0, value);
    }
    return this.getPath(text, text2);
  }

  public ClassPath$ClassFile getClassFile(String text) throws IOException {
    return this.getClassFile(text, ".class");
  }

  public ClassPath$ClassFile getClassFile(String text, String text2) throws IOException {
    int value;
    for (int value2 = value = 0; value2 < this.paths.length; value2 = ++value) {
      ClassPath$ClassFile classPath$ClassFile;
      if ((classPath$ClassFile = this.paths[value].getClassFile(text, text2)) != null) {
        return classPath$ClassFile;
      }
    }
    throw new IOException(("Couldn't find: " + text + text2));
  }

  @Override
  public boolean equals(Object result) {
    return result instanceof ClassPath
        ? this.class_path.equals(((ClassPath) result).class_path)
        : false;
  }

  /**
   * @deprecated
   */
  public ClassPath() {
    this(getClassPath());
  }

  public URL getURL(String text) {
    int value;
    for (int value2 = value = 0; value2 < this.paths.length; value2 = ++value) {
      URL uRL;
      if ((uRL = this.paths[value].getResource(text)) != null) {
        return uRL;
      }
    }
    return null;
  }

  public String getPath(String text, String text2) throws IOException {
    return this.getClassFile(text, text2).getText2();
  }
}
