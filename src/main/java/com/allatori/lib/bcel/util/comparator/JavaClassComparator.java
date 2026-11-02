package com.allatori.lib.bcel.util.comparator;

import com.allatori.lib.bcel.classfile.JavaClass;
import com.allatori.lib.bcel.util.BCELComparator;

public class JavaClassComparator implements BCELComparator {

  @Override
  public boolean equals(Object result, Object result2) {
    JavaClass javaClass = (JavaClass) result;
    JavaClass javaClass2 = (JavaClass) result2;
    return javaClass.getText2().equals(javaClass2.getText2());
  }

  @Override
  public int hashCode(Object result) {
    return ((JavaClass) result).getText2().hashCode();
  }
}
