package com.allatori.lib.bcel.util;

import com.allatori.lib.bcel.classfile.ClassParser;
import com.allatori.lib.bcel.classfile.JavaClass;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ClassLoaderRepository implements Repository {

  public Map loadedClasses;

  public java.lang.ClassLoader loader;

  @Override
  public void processJavaClass2(JavaClass javaClass) {
    this.loadedClasses.remove(javaClass.getText2());
  }

  @Override
  public void processJavaClass(JavaClass javaClass) {
    this.loadedClasses.put(javaClass.getText2(), javaClass);
    javaClass.setRepository(this);
  }

  public ClassLoaderRepository(java.lang.ClassLoader classLoader) {
    super();
    this.loadedClasses = new HashMap();
    this.loader = classLoader;
  }

  @Override
  public ClassPath getClassPath() {
    return null;
  }

  @Override
  public JavaClass getJavaClass(String text) {
    return this.loadedClasses.containsKey(text) ? (JavaClass) this.loadedClasses.get(text) : null;
  }

  @Override
  public JavaClass loadClass(Class classValue) throws ClassNotFoundException {
    return this.getJavaClass2(classValue.getName());
  }

  @Override
  public JavaClass getJavaClass2(String text) throws ClassNotFoundException {
    String text2 = text.replace('.', '/');
    JavaClass javaClass;
    if ((javaClass = this.getJavaClass(text)) != null) {
      return javaClass;
    }
    try {
      InputStream inputStream;
      if ((inputStream = this.loader.getResourceAsStream("" + text2 + ".class")) == null) {
        throw new ClassNotFoundException(("" + text + " not found."));
      }
      JavaClass javaClass2 = new ClassParser(inputStream, text).parse();
      this.processJavaClass(javaClass2);
      return javaClass2;
    } catch (IOException oException) {
      throw new ClassNotFoundException(oException.toString());
    }
  }

  @Override
  public void clear() {
    this.loadedClasses.clear();
  }
}
