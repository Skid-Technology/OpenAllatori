package com.allatori.lib.bcel;

import com.allatori.lib.bcel.classfile.JavaClass;
import com.allatori.lib.bcel.util.ClassPath;
import com.allatori.lib.bcel.util.ClassPath$ClassFile;
import com.allatori.lib.bcel.util.SyntheticRepository;
import java.io.IOException;

public abstract class Repository {

  public static com.allatori.lib.bcel.util.Repository _repository =
      SyntheticRepository.getInstance();

  public static JavaClass[] getJavaClasss(String text) throws ClassNotFoundException {
    return getJavaClasss2(lookupClass(text));
  }

  public static void clearCache() {
    _repository.clear();
  }

  public static ClassPath$ClassFile lookupClassFile(String text) {
    try {
      ClassPath classPath;
      return (classPath = _repository.getClassPath()) == null ? null : classPath.getClassFile(text);
    } catch (IOException oException) {
      return null;
    }
  }

  public static JavaClass[] getJavaClasss2(String text) throws ClassNotFoundException {
    return getJavaClasss(lookupClass(text));
  }

  public static void removeClass(JavaClass javaClass) {
    _repository.processJavaClass2(javaClass);
  }

  public static boolean matchesJavaClass(String text, JavaClass javaClass)
      throws ClassNotFoundException {
    return matchesJavaClass2(lookupClass(text), javaClass);
  }

  public static void setRepository(com.allatori.lib.bcel.util.Repository repository) {
    _repository = repository;
  }

  public static JavaClass addClass(JavaClass javaClass) {
    JavaClass javaClass2 = _repository.getJavaClass(javaClass.getText2());
    _repository.processJavaClass(javaClass);
    return javaClass2;
  }

  public static boolean matchesJavaClass2(String text, JavaClass javaClass)
      throws ClassNotFoundException {
    return matchesJavaClass(lookupClass(text), javaClass);
  }

  public static JavaClass[] getJavaClasss(JavaClass javaClass) throws ClassNotFoundException {
    return javaClass.createJavaClasss2();
  }

  public static com.allatori.lib.bcel.util.Repository getRepository() {
    return _repository;
  }

  public static JavaClass[] getJavaClasss2(JavaClass javaClass) throws ClassNotFoundException {
    return javaClass.createJavaClasss();
  }

  public static boolean matchesText(String text, String text2) throws ClassNotFoundException {
    return matchesJavaClass(lookupClass(text), lookupClass(text2));
  }

  public static boolean matchesJavaClass(JavaClass javaClass, JavaClass javaClass2)
      throws ClassNotFoundException {
    return javaClass.matchesJavaClass2(javaClass2);
  }

  public static JavaClass lookupClass(Class classValue) throws ClassNotFoundException {
    return _repository.loadClass(classValue);
  }

  public static boolean matchesJavaClass2(JavaClass javaClass, JavaClass javaClass2)
      throws ClassNotFoundException {
    return javaClass.matchesJavaClass(javaClass2);
  }

  public static boolean matchesText2(String text, String text2) throws ClassNotFoundException {
    return matchesJavaClass2(lookupClass(text), lookupClass(text2));
  }

  public static void removeClass(String text) {
    _repository.processJavaClass2(_repository.getJavaClass(text));
  }

  public static boolean matchesText(JavaClass javaClass, String text)
      throws ClassNotFoundException {
    return matchesJavaClass(javaClass, lookupClass(text));
  }

  public static boolean matchesText2(JavaClass javaClass, String text)
      throws ClassNotFoundException {
    return matchesJavaClass2(javaClass, lookupClass(text));
  }

  public static JavaClass lookupClass(String text) throws ClassNotFoundException {
    return _repository.getJavaClass2(text);
  }
}
