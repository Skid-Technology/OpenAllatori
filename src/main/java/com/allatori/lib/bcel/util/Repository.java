package com.allatori.lib.bcel.util;

import com.allatori.lib.bcel.classfile.JavaClass;
import java.io.Serializable;

public interface Repository extends Serializable {

  void processJavaClass(JavaClass javaClass);

  ClassPath getClassPath();

  void clear();

  JavaClass loadClass(Class classValue) throws ClassNotFoundException;

  JavaClass getJavaClass(String text);

  void processJavaClass2(JavaClass javaClass);

  JavaClass getJavaClass2(String text) throws ClassNotFoundException;
}
