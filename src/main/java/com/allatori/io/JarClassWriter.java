package com.allatori.io;

import com.allatori.lib.asm.ClassWriter;
import com.allatori.util.Log;

public class JarClassWriter extends ClassWriter {

  public final JarWriter jarWriter;

  public final ClassLoader classLoader;

  @Override
  public String getCommonSuperClass(String text, String text2) {
    Class classValue;
    Class classValue2;
    try {
      classValue = Class.forName(text.replace('/', '.'), false, this.classLoader);
      classValue2 = Class.forName(text2.replace('/', '.'), false, this.classLoader);
    } catch (ClassNotFoundException classNotFoundException) {
      if (!JarWriter.getValues(this.jarWriter).contains(classNotFoundException.getMessage())) {
        JarWriter.getValues(this.jarWriter).add(classNotFoundException.getMessage());
        Log.error("CLASS NOT FOUND: " + classNotFoundException.getMessage());
        Log.error("  ADD NEEDED JAR TO THE 'classpath' ELEMENT OF THE CONFIGURATION FILE.");
      }
      return "java/lang/Object";
    } catch (NoClassDefFoundError noClassDefFoundError) {
      if (!JarWriter.getValues(this.jarWriter).contains(noClassDefFoundError.getMessage())) {
        JarWriter.getValues(this.jarWriter).add(noClassDefFoundError.getMessage());
        Log.error("CLASS NOT FOUND: " + noClassDefFoundError.getMessage());
        Log.error("  ADD NEEDED JAR TO THE 'classpath' ELEMENT OF THE CONFIGURATION FILE.");
      }
      return "java/lang/Object";
    } catch (Exception exception) {
      throw new RuntimeException(exception.toString());
    } catch (UnsupportedClassVersionError unsupportedClassVersionError) {
      if (!JarWriter.matchesJarWriter(this.jarWriter)) {
        JarWriter.matchesEnabled(this.jarWriter, true);
        Log.error(
            "SOME CLASS(ES) CANNOT BE READ DUE TO JVM VERSION INCOMPATIBILITY: "
                + unsupportedClassVersionError.toString());
        Log.error("  USE NEWER JVM VERSION TO RUN ALLATORI.");
      }
      return "java/lang/Object";
    } catch (Error error2) {
      return "java/lang/Object";
    }
    if (classValue.isAssignableFrom(classValue2)) {
      return text;
    }
    if (classValue2.isAssignableFrom(classValue)) {
      return text2;
    }
    if (!classValue.isInterface() && !classValue2.isInterface()) {
      while (!(classValue = classValue.getSuperclass()).isAssignableFrom(classValue2)) {}
      return classValue.getName().replace('.', '/');
    } else {
      return "java/lang/Object";
    }
  }

  public JarClassWriter(JarWriter jarWriter2, int value, ClassLoader classLoader2) {
    super(value);
    this.jarWriter = jarWriter2;
    this.classLoader = classLoader2;
  }
}
