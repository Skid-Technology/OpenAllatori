package com.allatori.io;

import com.allatori.lib.asm.ClassWriter;
import com.allatori.util.Log;

public class DirectoryClassWriter extends ClassWriter {

  public final ClassLoader classLoader;

  public final DirectoryWriter directoryWriter;

  public DirectoryClassWriter(
      DirectoryWriter directoryWriter2, int value, ClassLoader classLoader2) {
    super(value);
    this.directoryWriter = directoryWriter2;
    this.classLoader = classLoader2;
  }

  @Override
  public String getCommonSuperClass(String text, String text2) {
    Class classValue;
    Class classValue2;
    try {
      classValue2 = Class.forName(text.replace('/', '.'), false, this.classLoader);
      classValue = Class.forName(text2.replace('/', '.'), false, this.classLoader);
    } catch (ClassNotFoundException classNotFoundException) {
      if (!DirectoryWriter.getValues(this.directoryWriter)
          .contains(classNotFoundException.getMessage())) {
        DirectoryWriter.getValues(this.directoryWriter).add(classNotFoundException.getMessage());
        Log.error("CLASS NOT FOUND: " + classNotFoundException.getMessage());
        Log.error("  ADD NEEDED JAR TO THE 'classpath' ELEMENT OF THE CONFIGURATION FILE.");
      }
      return "java/lang/Object";
    } catch (NoClassDefFoundError noClassDefFoundError) {
      if (!DirectoryWriter.getValues(this.directoryWriter)
          .contains(noClassDefFoundError.getMessage())) {
        DirectoryWriter.getValues(this.directoryWriter).add(noClassDefFoundError.getMessage());
        Log.error("CLASS NOT FOUND: " + noClassDefFoundError.getMessage());
        Log.error("  ADD NEEDED JAR TO THE 'classpath' ELEMENT OF THE CONFIGURATION FILE.");
      }
      return "java/lang/Object";
    } catch (Exception exception) {
      throw new RuntimeException(exception.toString());
    } catch (UnsupportedClassVersionError unsupportedClassVersionError) {
      if (!DirectoryWriter.matchesDirectoryWriter(this.directoryWriter)) {
        DirectoryWriter.matchesEnabled(this.directoryWriter, true);
        Log.error(
            "SOME CLASS(ES) CANNOT BE READ DUE TO JVM VERSION INCOMPATIBILITY: "
                + unsupportedClassVersionError.toString());
        Log.error("  USE NEWER JVM VERSION TO RUN ALLATORI.");
      }
      return "java/lang/Object";
    } catch (Error error2) {
      return "java/lang/Object";
    }
    if (classValue2.isAssignableFrom(classValue)) {
      return text;
    }
    if (classValue.isAssignableFrom(classValue2)) {
      return text2;
    }
    if (!classValue2.isInterface() && !classValue.isInterface()) {
      while (!(classValue2 = classValue2.getSuperclass()).isAssignableFrom(classValue)) {}
      return classValue2.getName().replace('.', '/');
    } else {
      return "java/lang/Object";
    }
  }
}
