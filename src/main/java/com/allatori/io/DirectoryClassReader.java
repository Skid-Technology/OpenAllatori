package com.allatori.io;

import com.allatori.lib.bcel.classfile.ClassParser;
import com.allatori.lib.bcel.generic.ClassGen;
import com.allatori.model.ClassStorage;
import com.allatori.util.Log;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class DirectoryClassReader {

  public static ClassGen createClassGen(InputStream inputStream, String text) throws IOException {
    ClassGen classGen = new ClassGen(new ClassParser(inputStream, text).parse());
    inputStream.close();
    return classGen;
  }

  public static void processText(String text, ClassStorage classStorage, String text2) {
    File[] files = new File(text).listFiles();
    int value;
    for (int value2 = value = 0; value2 < files.length; value2 = ++value) {
      File file;
      if ((file = files[value]).isDirectory()) {
        processText(file.getPath(), classStorage, text2);
      }
      if (file.getName().endsWith(".class")) {
        try {
          classStorage.processText(
              createClassGen(new BufferedInputStream(new FileInputStream(file)), file.getName()),
              text2);
        } catch (Exception exception) {
          if (!file.getName().endsWith("module-info.class")) {
            Log.error("Cannot parse class " + file.getName());
          }
        }
      }
    }
  }
}
