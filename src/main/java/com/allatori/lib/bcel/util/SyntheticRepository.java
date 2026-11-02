package com.allatori.lib.bcel.util;

import com.allatori.lib.bcel.classfile.ClassParser;
import com.allatori.lib.bcel.classfile.JavaClass;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

public class SyntheticRepository implements Repository {

  public static final String DEFAULT_PATH = ClassPath.getClassPath();

  public Map _loadedClasses;

  public static Map _instances = new HashMap();

  public ClassPath _path;

  @Override
  public void processJavaClass2(JavaClass javaClass) {
    this._loadedClasses.remove(javaClass.getText2());
  }

  @Override
  public JavaClass getJavaClass(String text) {
    SoftReference softReference;
    return (softReference = (SoftReference) this._loadedClasses.get(text)) == null
        ? null
        : (JavaClass) softReference.get();
  }

  @Override
  public JavaClass getJavaClass2(String text) throws ClassNotFoundException {
    if (text != null && !text.equals("")) {
      String text2 = text.replace('/', '.');
      JavaClass javaClass;
      if ((javaClass = this.getJavaClass(text2)) != null) {
        return javaClass;
      }
      try {
        return this.loadClass(this._path.getInputStream2(text2), text2);
      } catch (IOException oException) {
        throw new ClassNotFoundException(
            ("Exception while looking for class " + text2 + ": " + oException.toString()));
      }
    } else {
      throw new IllegalArgumentException(("Invalid class name " + text));
    }
  }

  public static SyntheticRepository getInstance(ClassPath classPath) {
    SyntheticRepository syntheticRepository;
    if ((syntheticRepository = (SyntheticRepository) _instances.get(classPath)) == null) {
      syntheticRepository = new SyntheticRepository(classPath);
      _instances.put(classPath, syntheticRepository);
    }
    return syntheticRepository;
  }

  @Override
  public JavaClass loadClass(Class classValue) throws ClassNotFoundException {
    String name = classValue.getName();
    JavaClass javaClass;
    if ((javaClass = this.getJavaClass(name)) != null) {
      return javaClass;
    }
    String text = name;
    int value;
    if ((value = name.lastIndexOf(46)) > 0) {
      text = text.substring(value + 1);
    }
    return this.loadClass(classValue.getResourceAsStream(text + ".class"), name);
  }

  @Override
  public void clear() {
    this._loadedClasses.clear();
  }

  @Override
  public ClassPath getClassPath() {
    return this._path;
  }

  @Override
  public void processJavaClass(JavaClass javaClass) {
    this._loadedClasses.put(javaClass.getText2(), new SoftReference<>(javaClass));
    javaClass.setRepository(this);
  }

  public SyntheticRepository(ClassPath classPath) {
    super();
    this._path = null;
    this._loadedClasses = new HashMap();
    this._path = classPath;
  }

  public static SyntheticRepository getInstance() {
    return getInstance(ClassPath.SYSTEM_CLASS_PATH);
  }

  public JavaClass loadClass(InputStream inputStream, String text) throws ClassNotFoundException {
    try {
      if (inputStream != null) {
        JavaClass javaClass = new ClassParser(inputStream, text).parse();
        this.processJavaClass(javaClass);
        return javaClass;
      }
    } catch (IOException oException) {
      throw new ClassNotFoundException(
          ("Exception while looking for class " + text + ": " + oException.toString()));
    }
    throw new ClassNotFoundException(("SyntheticRepository could not load " + text));
  }
}
