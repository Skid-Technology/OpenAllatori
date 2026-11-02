package com.allatori.io;

import com.allatori.lib.bcel.classfile.ClassParser;
import com.allatori.lib.bcel.generic.ClassGen;
import com.allatori.model.ClassStorage;
import com.allatori.util.Log;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class JarClassReader {

  public static ClassGen createClassGen(InputStream inputStream, String text) throws IOException {
    ClassGen classGen = new ClassGen(new ClassParser(inputStream, text).parse());
    inputStream.close();
    return classGen;
  }

  public static void processClassStorage(String text, ClassStorage classStorage)
      throws IOException {
    JarFile jarFile;
    Enumeration enumeration = (jarFile = new JarFile(text)).entries();
    while (enumeration.hasMoreElements()) {
      JarEntry jarEntry;
      if (!(jarEntry = (JarEntry) enumeration.nextElement()).isDirectory()
          && jarEntry.getName().endsWith(".class")) {
        try {
          classStorage.processText(
              createClassGen(jarFile.getInputStream(jarEntry), jarEntry.getName()),
              text,
              jarEntry.getName());
        } catch (Exception exception) {
          if (!jarEntry.getName().endsWith("module-info.class")) {
            Log.error("Cannot parse class " + jarEntry.getName());
          }
        }
      }
    }
    jarFile.close();
  }
}
