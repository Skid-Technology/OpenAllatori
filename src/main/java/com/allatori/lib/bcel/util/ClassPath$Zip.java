package com.allatori.lib.bcel.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ClassPath$Zip extends ClassPath$PathEntry {

  public ZipFile zip;

  public ClassPath$Zip(ZipFile zipFile) {
    super(null);
    this.zip = zipFile;
  }

  @Override
  public InputStream getResourceAsStream(String text) {
    ZipEntry entry = this.zip.getEntry(text);
    try {
      return entry != null ? this.zip.getInputStream(entry) : null;
    } catch (IOException oException) {
      return null;
    }
  }

  @Override
  public URL getResource(String text) {
    ZipEntry entry = this.zip.getEntry(text);
    try {
      return entry != null ? new URL(("jar:file:" + this.zip.getName() + "!/" + text)) : null;
    } catch (MalformedURLException malformedURLException) {
      return null;
    }
  }

  @Override
  public ClassPath$ClassFile getClassFile(String text, String text2) throws IOException {
    ZipEntry zipEntry;
    return (zipEntry = this.zip.getEntry("" + text.replace('.', '/') + text2)) == null
        ? null
        : new ClassPath$Zip$1(this, zipEntry);
  }

  public static ZipFile access$200(ClassPath$Zip classPath$Zip) {
    return classPath$Zip.zip;
  }
}
