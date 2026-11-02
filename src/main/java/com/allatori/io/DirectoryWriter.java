package com.allatori.io;

import com.allatori.config.Configuration;
import com.allatori.lib.asm.ClassReader;
import com.allatori.lib.bcel.generic.ClassGen;
import com.allatori.model.ClassStorage;
import com.allatori.util.FileUtils;
import com.allatori.util.Log;
import com.allatori.util.StreamUtils;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Vector;

public class DirectoryWriter {

  public String text;

  public ClassStorage classStorage;

  public boolean enabled;

  public HashSet values = new HashSet();

  public DirectoryWriter() {
    this.enabled = false;
  }

  public void processFile(ClassGen classGen, File file)
      throws FileNotFoundException, ClassNotFoundException, IOException {
    file.getParentFile().mkdirs();
    BufferedOutputStream bufferedOutputStream =
        new BufferedOutputStream(new FileOutputStream(file));
    BufferedOutputStream bufferedOutputStream2;
    if (classGen.getMajor() > 50) {
      ResolvingClassLoader resolvingClassLoader =
          new ResolvingClassLoader(
              this.classStorage, Configuration.getMappingOptions().lookupTable);
      ClassReader classReader = new ClassReader(classGen.getJavaClass().getBytes());
      DirectoryClassWriter directoryClassWriter =
          new DirectoryClassWriter(this, 2, resolvingClassLoader);
      bufferedOutputStream2 = bufferedOutputStream;
      classReader.accept(directoryClassWriter, 4);
      bufferedOutputStream.write(directoryClassWriter.toByteArray());
    } else {
      bufferedOutputStream2 = bufferedOutputStream;
      bufferedOutputStream.write(classGen.getJavaClass().getBytes());
    }
    bufferedOutputStream2.close();
  }

  public void processText(File file, File file2, ClassStorage classStorage, String text2)
      throws FileNotFoundException, IOException, ClassNotFoundException {
    File[] files = file.listFiles();
    int value;
    for (int value2 = value = 0; value2 < files.length; value2 = ++value) {
      File file3;
      if ((file3 = files[value]).isDirectory()) {
        String text3;
        if ((text3 = "" + text2 + "." + file3.getName()).startsWith(".")) {
          text3 = text3.substring(1);
        }
        this.processText(
            file3, new File(("" + file2.getPath() + "/" + file3.getName())), classStorage, text3);
      } else if (file3.getName().endsWith(".class")) {
        String name = file3.getName();
        String text4 = name.substring(0, name.length() - 6);
        if (text2.length() > 0) {
          text4 = "" + text2 + "." + text4;
        }
        ClassGen classGen;
        if ((classGen = classStorage.getClassGen(text4)) != null
            && !Configuration.getIgnoreClassesOptions().matchesClassGen(classStorage, classGen)) {
          String className = classGen.getClassName();
          String text5 = "" + className.replace('.', '/') + ".class";
          this.processFile(classGen, new File(("" + this.text + "/" + text5)));
        } else {
          if (classGen == null) {
            Log.warn(
                "File "
                    + file3.getPath()
                    + " is copied with no modifications, as it is not in its package.");
          }
          new File(file2.getPath()).mkdirs();
          FileUtils.processFile(file3, new File(("" + file2.getPath() + "/" + file3.getName())));
        }
      } else {
        String text6 = "" + file2.getPath() + "/" + file3.getName();
        if (Configuration.getRenamingOptions()
            .getResourceRenamingOptions()
            .isUpdateResourceNamesEnabled()) {
          if ((text6 =
                  ""
                      + this.text
                      + "/"
                      + StreamUtils.createText(
                          "",
                          new StringBuilder()
                              .insert(0, text2.replace('.', '/'))
                              .append('/')
                              .append(file3.getName())
                              .toString()))
              .contains("/")) {
            new File(text6.substring(0, text6.lastIndexOf(47))).mkdirs();
          }
        } else {
          new File(file2.getPath()).mkdirs();
        }
        if (Configuration.getRenamingOptions()
            .getResourceRenamingOptions()
            .matchesText(file3.getName())) {
          FileInputStream fileInputStream = new FileInputStream(file3);
          FileOutputStream fileOutputStream = new FileOutputStream(new File(text6));
          StreamUtils.processText(
              fileInputStream,
              fileOutputStream,
              Configuration.getRenamingOptions()
                  .getResourceRenamingOptions()
                  .getText(file3.getName()));
          fileInputStream.close();
          fileOutputStream.close();
        } else {
          FileUtils.processFile(file3, new File(text6));
        }
      }
    }
    Vector inputClasses = classStorage.getInputClasses();
    int value3;
    for (int value4 = value3 = 0; value4 < inputClasses.size(); value4 = value3) {
      ClassGen classGen2;
      String className2 = (classGen2 = (ClassGen) inputClasses.get(value3)).getClassName();
      String text7 = "" + className2.replace('.', '/') + ".class";
      StringBuilder stringBuilder =
          new StringBuilder().insert(0, this.text).append("/").append(text7);
      value3++;
      this.processFile(classGen2, new File(stringBuilder.toString()));
    }
  }

  public void processClassStorage(String text2, String text, ClassStorage classStorage2)
      throws FileNotFoundException, IOException, ClassNotFoundException {
    this.classStorage = classStorage2;
    this.text = text;
    if (text2.equals(text)) {
      this.processText(new File(text2), classStorage2, "");
    } else {
      this.processText(new File(text2), new File(text), classStorage2, "");
    }
  }

  public static boolean matchesDirectoryWriter(DirectoryWriter directoryWriter) {
    return directoryWriter.enabled;
  }

  public void processText(File file, ClassStorage classStorage, String text2)
      throws FileNotFoundException, ClassNotFoundException, IOException {
    File[] files = file.listFiles();
    int value;
    for (int value2 = value = 0; value2 < files.length; value2 = ++value) {
      File file2;
      if ((file2 = files[value]).isDirectory()) {
        String text3;
        if ((text3 = "" + text2 + "." + file2.getName()).startsWith(".")) {
          text3 = text3.substring(1);
        }
        this.processText(file2, classStorage, text3);
      } else if (file2.getName().endsWith(".class")) {
        String name = file2.getName();
        String text4 = name.substring(0, name.length() - 6);
        if (text2.length() > 0) {
          text4 = "" + text2 + "." + text4;
        }
        ClassGen classGen;
        if ((classGen = classStorage.getClassGen(text4)) != null
            && !Configuration.getIgnoreClassesOptions().matchesClassGen(classStorage, classGen)) {
          file2.delete();
          String className = classGen.getClassName();
          String text5 = "" + className.replace('.', '/') + ".class";
          this.processFile(classGen, new File(("" + this.text + "/" + text5)));
        }
      }
    }
    Vector inputClasses = classStorage.getInputClasses();
    int value3;
    for (int value4 = value3 = 0; value4 < inputClasses.size(); value4 = value3) {
      ClassGen classGen2;
      String className2 = (classGen2 = (ClassGen) inputClasses.get(value3)).getClassName();
      String text6 = "" + className2.replace('.', '/') + ".class";
      StringBuilder stringBuilder =
          new StringBuilder().insert(0, this.text).append("/").append(text6);
      value3++;
      this.processFile(classGen2, new File(stringBuilder.toString()));
    }
  }

  public static HashSet getValues(DirectoryWriter directoryWriter) {
    return directoryWriter.values;
  }

  public static boolean matchesEnabled(DirectoryWriter directoryWriter, boolean matches) {
    return directoryWriter.enabled = matches;
  }
}
