package com.allatori.lib.bcel.util.comparator;

import com.allatori.lib.bcel.generic.ClassGen;
import com.allatori.lib.bcel.util.BCELComparator;

public class ClassGenComparator implements BCELComparator {

  @Override
  public int hashCode(Object result) {
    return ((ClassGen) result).getClassName().hashCode();
  }

  @Override
  public boolean equals(Object result, Object result2) {
    ClassGen classGen = (ClassGen) result;
    ClassGen classGen2 = (ClassGen) result2;
    return classGen.getClassName().equals(classGen2.getClassName());
  }
}
