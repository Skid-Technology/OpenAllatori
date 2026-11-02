package com.allatori.lib.bcel.util;

import com.allatori.lib.bcel.classfile.JavaClass;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @deprecated
 */
public class ClassVector implements Serializable {

  public List vec = new ArrayList();

  public void addElement(JavaClass javaClass) {
    this.vec.add(javaClass);
  }

  public void removeElementAt(int value) {
    this.vec.remove(value);
  }

  public JavaClass elementAt(int value) {
    return (JavaClass) this.vec.get(value);
  }

  public JavaClass[] toArray() {
    JavaClass[] javaClasss = new JavaClass[this.vec.size()];
    this.vec.toArray(javaClasss);
    return javaClasss;
  }
}
