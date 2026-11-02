package com.allatori.io;

import com.allatori.config.Configuration;
import com.allatori.config.InputEntry;
import com.allatori.model.ClassStorage;
import com.allatori.util.Log;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Vector;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

public class ClassStorageClassLoader extends URLClassLoader {

  public ClassStorage classStorage;

  public byte[] createValues(JarFile jarFile, ZipEntry zipEntry) throws IOException {
    byte[] values = new byte[(int) zipEntry.getSize()];
    DataInputStream dataInputStream;
    (dataInputStream = new DataInputStream(jarFile.getInputStream(zipEntry)))
        .readFully(values, 0, values.length);
    dataInputStream.close();
    return values;
  }

  public ClassStorageClassLoader(URL[] uRLs) {
    super(uRLs, ClassStorageClassLoader.class.getClassLoader());
  }

  @Override
  public Class findClass(String name) throws ClassNotFoundException, ClassNotFoundException {
    try {
      try {
        if (classStorage.getClassGen(name) != null) return createType(name);
      } catch (Exception ignored) {
      }
      return super.findClass(name);
    } catch (ClassNotFoundException failure) {
      try {
        return createType(name);
      } catch (Exception ignored) {
        throw failure;
      }
    }
  }

  public Class createType(String text) throws Exception {
    try {
      String text3 = text.replace('.', '/').concat(".class");
      Vector values3 = Configuration.getInputOptions().getValues3();
      int value;
      for (int value2 = value = 0; value2 < values3.size(); value2 = ++value) {
        InputEntry inputEntry = (InputEntry) values3.get(value);
        JarFile jarFile = new JarFile(inputEntry.text2);
        try {
          ZipEntry zipEntry;
          if ((zipEntry = jarFile.getEntry(text3)) != null) {
            byte[] values = this.createValues(jarFile, zipEntry);
            try {
              return this.defineClass(text, values, 0, values.length);
            } catch (IncompatibleClassChangeError incompatibleClassChangeError) {
              Log.error("IncompatibleClassChangeError for class " + text);
              throw incompatibleClassChangeError;
            }
          }
        } finally {
          jarFile.close();
        }
      }
      throw new Exception();
    } catch (Exception exception) {
      throw exception;
    }
  }

  public void setClassStorage(ClassStorage classStorage2) {
    this.classStorage = classStorage2;
  }
}
