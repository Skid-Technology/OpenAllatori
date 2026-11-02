package com.allatori.lib.bcel.util;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class JavaWrapper {

  public java.lang.ClassLoader loader;

  public void runMain(String text, String[] texts) throws ClassNotFoundException {
    Class classValue = this.loader.loadClass(text);
    Method method;
    try {
      Method method2 = method = classValue.getMethod("main", texts.getClass());
      int modifiers = method2.getModifiers();
      Class returnType = method2.getReturnType();
      if (!Modifier.isPublic(modifiers)
          || !Modifier.isStatic(modifiers)
          || Modifier.isAbstract(modifiers)
          || returnType != void.class) {
        throw new NoSuchMethodException();
      }
    } catch (NoSuchMethodException noSuchMethodException) {
      System.out.println(
          "In class " + text + ": public static void main(String[] argv) is not defined");
      return;
    }
    try {
      method.invoke(null, texts);
    } catch (Exception exception) {
      exception.printStackTrace();
    }
  }

  public static void main(String[] texts) throws Exception {
    if (texts.length == 0) {
      System.out.println("Missing class name.");
    } else {
      String text = texts[0];
      String[] texts2 = new String[texts.length - 1];
      System.arraycopy(texts, 1, texts2, 0, texts2.length);
      new JavaWrapper().runMain(text, texts2);
    }
  }

  public static java.lang.ClassLoader getClassLoader() {
    String text;
    if ((text = System.getProperty("bcel.classloader")) == null || "".equals(text)) {
      text = "com.allatori.lib.bcel.util.ClassLoader";
    }
    try {
      return (java.lang.ClassLoader) Class.forName(text).newInstance();
    } catch (Exception exception) {
      throw new RuntimeException(exception.toString());
    }
  }

  public JavaWrapper(java.lang.ClassLoader classLoader) {
    super();
    this.loader = classLoader;
  }

  public JavaWrapper() {
    this(getClassLoader());
  }
}
